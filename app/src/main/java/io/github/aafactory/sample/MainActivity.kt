package io.github.aafactory.sample

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import com.tapadoo.alerter.Alerter
import io.github.aafactory.commons.extensions.dpToPixel
import io.github.aafactory.commons.utils.DateUtils
import io.github.aafactory.sample.adapters.ShowcaseAdapter
import io.github.aafactory.sample.models.Showcase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

import mehdi.sakout.fancybuttons.samples.MainActivity
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private var mListItem: ArrayList<Showcase> = arrayListOf<Showcase>()
    private val adapter: ShowcaseAdapter by lazy { 
        ShowcaseAdapter(
                this,
                mListItem,
                AdapterView.OnItemClickListener { _, _, position, _ ->
                    val showCase = adapter.getItem(position)
                    when (showCase.name) {
                        "Fancybuttons" -> startActivity(Intent(this, MainActivity::class.java))
                    }
                }
        ) 
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.run { 
            title = getString(R.string.app_name)
        }
        recyclerView.addItemDecoration(ItemDecoration(this))
        adapter.attachTo(recyclerView)
        val moduleNames = resources.getStringArray(R.array.module_names)
        val moduleDescriptions = resources.getStringArray(R.array.module_descriptions)
        for ((index, name) in moduleNames.withIndex()) {
            mListItem.add(Showcase(name, moduleDescriptions[index]))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun bindEvent() {
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

//        btn_fancy_button.setOnClickListener { _ ->
//            startActivity(Intent(this, MainActivity::class.java))
//        }
//
//        btn_keddit_by_steps.setOnClickListener { _ ->
//            Alerter.create(this@MainActivity)
//                    .setTitle("Keddit")
//                    .setText(getString(R.string.keddit_by_steps_description))
//                    .setBackgroundColorRes(R.color.colorAccent)
//                    .show()
//        }
//
//        btn_alerter.setOnClickListener { _ ->
//            Alerter.create(this@MainActivity)
//                    .setTitle("Alerter")
//                    .setText(getString(R.string.alerter_description))
//                    .setBackgroundColorRes(R.color.colorAccent)
//                    .show()
//        }
    }

    private inner class ItemDecoration(private val context: Context) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect?.bottom = context.dpToPixel(3F)
        }
    }
}
