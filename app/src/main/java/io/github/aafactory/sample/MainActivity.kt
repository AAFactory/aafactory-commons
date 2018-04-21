package io.github.aafactory.sample

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AdapterView
import io.github.aafactory.commons.extensions.dpToPixel
import io.github.aafactory.sample.adapters.ShowcaseAdapter
import io.github.aafactory.sample.models.Showcase
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_toolbar.*
import mehdi.sakout.fancybuttons.samples.MainActivity
import java.util.*

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
                        "Alerter" -> startActivity(Intent(this, com.tapadoo.alerter.sample.MainActivity::class.java))
                        "CoordinatorExamples" -> startActivity(Intent(this, saulmm.coordinatorexamples.MainActivity::class.java))
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

    private inner class ItemDecoration(private val context: Context) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect?.bottom = context.dpToPixel(3F)
        }
    }
}
