package io.github.aafactory.sample

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.simplemobiletools.commons.extensions.showKeyboard
import io.github.aafactory.commons.activities.BaseMarkDownViewActivity
import io.github.aafactory.commons.activities.BaseSimpleActivity
import io.github.aafactory.commons.extensions.dpToPixel
import io.github.aafactory.commons.extensions.getApplicationDataDirectory
import io.github.aafactory.commons.extensions.makeToast
import io.github.aafactory.commons.extensions.triggerRestart
import io.github.aafactory.sample.activities.DevActivity
import io.github.aafactory.sample.activities.MarkDownViewActivity
import io.github.aafactory.sample.adapters.ShowcaseAdapter
import io.github.aafactory.sample.databinding.ActivityMainBinding
import io.github.aafactory.sample.databinding.DialogSearchMainBinding
import io.github.aafactory.sample.models.Showcase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import okhttp3.*
import java.io.File
import java.io.FileReader
import java.io.IOException

class MainActivity : BaseSimpleActivity() {
    private lateinit var mActivityMainBinding: ActivityMainBinding
    private lateinit var mDialogSearchMainBinding: DialogSearchMainBinding
    private lateinit var mShowcaseItems: List<Showcase>
    private var mFilteredShowcaseItems: ArrayList<Showcase> = arrayListOf()
    private val mShowcaseJsonName = "showcases.json"
    private val mAdapter: ShowcaseAdapter by lazy {
        ShowcaseAdapter(
                this,
                mFilteredShowcaseItems
        ) { _, _, position, _ ->
            val showCase = mAdapter.getItem(position)
            startActivity(Intent(this, MarkDownViewActivity::class.java).apply {
                val openUrlInfo = if (showCase.isCheatSheet) showCase.cheatSheetUrl else "https://raw.githubusercontent.com/${showCase.owner}/${showCase.name}/master/README.md"
                putExtra(BaseMarkDownViewActivity.OPEN_URL_INFO, openUrlInfo)
                putExtra(BaseMarkDownViewActivity.OPEN_URL_DESCRIPTION, showCase.name)
                putExtra(BaseMarkDownViewActivity.FORCE_APPEND_CODE_BLOCK, showCase.forceAppendCodeBlock)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        mDialogSearchMainBinding = DialogSearchMainBinding.inflate(layoutInflater)
        setContentView(mActivityMainBinding.root)
        setSupportActionBar(toolbar)
        supportActionBar?.run { 
            title = getString(R.string.app_name)
        }
        recyclerView.addItemDecoration(ItemDecoration(this))
        mAdapter.attachTo(recyclerView)

        val showcaseJsonFile = File("${getApplicationDataDirectory()}/$mShowcaseJsonName")
        fun loadShowcaseJson(jsonFile: File) {
            val reader = JsonReader(FileReader(jsonFile))
            val type = object : TypeToken<List<Showcase>>(){}.type
            mShowcaseItems = GsonBuilder().create().fromJson<List<Showcase>?>(reader, type).sortedBy { item -> item.name }
            runOnUiThread {
                progressBar.visibility = View.GONE
                makeToast("items: ${mShowcaseItems.size}")
                refresh()
            }
        }
        if (showcaseJsonFile.exists()) {
            loadShowcaseJson(showcaseJsonFile)
        } else {
            val client = OkHttpClient()
            val request: Request = Request.Builder()
                    .url("https://raw.githubusercontent.com/AAFactory/aafactory-commons/master/data/showcases.json")
                    .build()
            // enqueue is async
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}
                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val body = response.body()?.string() ?: ""
                        val showcaseJsonFile = File("${getApplicationDataDirectory()}/$mShowcaseJsonName")
                        showcaseJsonFile.writeText(body, Charsets.UTF_8)
                    }
                    response.close()
                    loadShowcaseJson(showcaseJsonFile)
                }
            })
        }
    }

    private fun refresh(repoName: String = "", description: String = "") {
        mFilteredShowcaseItems.clear()
        mShowcaseItems.filter { item ->
            when (repoName.isEmpty()) {
                true -> true
                false -> { item.name.contains(repoName, true) }
            }
        }.filter { item ->
            when (description.isEmpty()) {
                true -> true
                false -> { item.description.contains(description, true) }
            }
        }.run { mFilteredShowcaseItems.addAll(this) }
        mAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_diary_main, menu)
        return true
    }

    private var mDialogSearchMain: Dialog? = null
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.devConsole -> {
                startActivity(Intent(this, DevActivity::class.java))
            }
            R.id.deleteShowcaseFile -> {
                val showcaseJsonFile = File("${getApplicationDataDirectory()}/$mShowcaseJsonName")
                if (showcaseJsonFile.exists()) showcaseJsonFile.delete()
                triggerRestart(MainActivity::class.java)
            }
            R.id.search -> {
                mDialogSearchMain?.show() ?: run {
                    val builder = AlertDialog.Builder(this).apply {
                        setNegativeButton(getString(android.R.string.cancel), null)
                        setPositiveButton(getString(android.R.string.ok)) { _, _ ->
                            refresh(mDialogSearchMainBinding.repositoryNameQuery.text.toString(), mDialogSearchMainBinding.repositoryDescriptionQuery.text.toString())
                            mDialogSearchMain?.dismiss()
                        }
                    }
                    mDialogSearchMain = builder.create().apply {
                        setTitle("Repository Filter")
//                    val dialogSearchMain = layoutInflater.inflate(R.layout.dialog_search_main, null)
//                    setView(dialogSearchMain)
                        setView(mDialogSearchMainBinding.root)
                        mDialogSearchMainBinding.repositoryNameQuery.run {
                            requestFocus()
                            showKeyboard(this)
                        }
                        show()
                    }
                }

//                Handler(Looper.getMainLooper()).postDelayed({
//                    mDialogSearchMainBinding.repositoryNameQuery.run {
//                        runOnUiThread {
//                            requestFocus()
//                            showKeyboard(this)
//                        }
//                    }
//                }, 1000)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private inner class ItemDecoration(private val context: Context) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.bottom = context.dpToPixel(3F)
        }
//        override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
//            super.getItemOffsets(outRect, view, parent, state)
//            outRect?.bottom = context.dpToPixel(3F)
//        }
    }
}
