package io.github.aafactory.sample

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.simplemobiletools.commons.extensions.showKeyboard
import io.github.aafactory.commons.activities.BaseMarkDownViewActivity
import io.github.aafactory.commons.activities.BaseSimpleActivity
import io.github.aafactory.commons.extensions.dpToPixel
import io.github.aafactory.sample.activities.DevActivity
import io.github.aafactory.sample.activities.MarkDownViewActivity
import io.github.aafactory.sample.adapters.ShowcaseAdapter
import io.github.aafactory.sample.databinding.ActivityMainBinding
import io.github.aafactory.sample.databinding.DialogSearchMainBinding
import io.github.aafactory.sample.models.Showcase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : BaseSimpleActivity() {
    private lateinit var mActivityMainBinding: ActivityMainBinding
    private lateinit var mDialogSearchMainBinding: DialogSearchMainBinding

    private val showcaseItems = mutableListOf(
            Showcase("", "Regular Expression", true, "Basic Chapter-01", "https://raw.githubusercontent.com/hanjoongcho/CheatSheet/master/regular-expression/chapter01.md"),
            Showcase("AppIntro", "AppIntro", false, ""),
            Showcase("Werb", "PickPhotoSample", false, ""),
            Showcase("ParkSangGwon", "TedBottomPicker", false, ""),
            Showcase("donglua", "PhotoPicker", false, ""),
            Showcase("gsuitedevs", "android-samples", false, ""),
            Showcase("kioko", "motion-layout-playground", false, ""),
            Showcase("zoonooz", "simple-view-behavior", false, ""),
            Showcase("googlesamples", "android-architecture", false, ""),
            Showcase("afollestad", "material-dialogs", false, ""),
            Showcase("googlesamples", "android-FingerprintDialog", false, ""),
            Showcase("rubensousa", "ViewPagerCards", false, ""),
            Showcase("AAFactory", "aafactory-commons", false, ""),
            Showcase("devunwired", "recyclerview-playground", false, ""),
            Showcase("openlayers", "openlayers", false, ""),
            Showcase("juanchosaravia", "KedditBySteps", false, ""),
            Showcase("Tapadoo", "Alerter", false, ""),
            Showcase("saulmm", "CoordinatorExamples", false, ""),
            Showcase("medyo", "Fancybuttons", false, ""),
            Showcase("pedant", "sweet-alert-dialog", false, ""),
            Showcase("timusus", "RecyclerView-FastScroll", false, ""),
            Showcase("woxingxiao", "BubbleSeekBar", false, ""),
            Showcase("PhilJay", "MPAndroidChart", false, ""),
            Showcase("navermaps", "maps.android", false, ""),
            Showcase("bumptech", "glide", false, ""),
            Showcase("googlesamples", "android-ConstraintLayoutExamples", false, ""),
    )

    private var mListItem: ArrayList<Showcase> = arrayListOf()
    private val mAdapter: ShowcaseAdapter by lazy {
        ShowcaseAdapter(
                this,
                mListItem
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
        refresh()
        runOnUiThread {
            progressBar.visibility = View.GONE
        }
    }

    private fun refresh(repoName: String = "", description: String = "") {
        mListItem.clear()
        showcaseItems.filter { item ->
            when (repoName.isEmpty()) {
                true -> true
                false -> { item.name.contains(repoName, true) }
            }
        }.filter { item ->
            when (description.isEmpty()) {
                true -> true
                false -> { item.description.contains(description, true) }
            }
        }.run { mListItem.addAll(this) }
        mAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    private var mDialogSearchMain: Dialog? = null
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.devConsole -> {
                startActivity(Intent(this, DevActivity::class.java))
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
                        show()
                    }
                }

                Handler(Looper.getMainLooper()).postDelayed({
                    mDialogSearchMainBinding.repositoryNameQuery.run {
                        runOnUiThread {
                            requestFocus()
                            showKeyboard(this)
                        }
                    }
                }, 1000)
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
