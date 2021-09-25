package io.github.aafactory.sample

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import io.github.aafactory.commons.activities.BaseSimpleActivity
import io.github.aafactory.commons.extensions.dpToPixel
import io.github.aafactory.sample.adapters.ShowcaseAdapter
import io.github.aafactory.sample.models.Repository
import io.github.aafactory.sample.models.Showcase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : BaseSimpleActivity() {
    val listItems = mutableListOf<Map<String, String>>(
            mapOf("owner" to "Werb",
                    "name" to "PickPhotoSample")
            , mapOf("owner" to "ParkSangGwon",
                    "name" to "TedBottomPicker")
            , mapOf("owner" to "donglua",
                    "name" to "PhotoPicker")
            , mapOf("owner" to "gsuitedevs",
                    "name" to "android-samples",
                    "displayName" to "Google Drive Android API Demos")
            , mapOf("owner" to "kioko",
                    "name" to "motion-layout-playground")
            , mapOf("owner" to "zoonooz",
                    "name" to "simple-view-behavior")
            , mapOf("owner" to "googlesamples",
                    "name" to "android-architecture",
                    "displayName" to "todo-mvvm-live-kotlin"
            )
            , mapOf("owner" to "googlesamples",
                "name" to "android-architecture",
                "displayName" to "todo-mvp-kotlin"
            )
            , mapOf("owner" to "afollestad",
                    "name" to "material-dialogs"
            )
            , mapOf("owner" to "googlesamples",
                    "name" to "android-FingerprintDialog"
            )
            , mapOf("owner" to "rubensousa",
                    "name" to "ViewPagerCards"
            )
            , mapOf("owner" to "AAFactory",
                    "name" to "aafactory-commons"
            )
            , mapOf("owner" to "devunwired",
                    "name" to "recyclerview-playground"
            )
            , mapOf("owner" to "openlayers",
                    "name" to "openlayers"
            )
            , mapOf("owner" to "juanchosaravia",
                    "name" to "KedditBySteps"
            )
            , mapOf("owner" to "Tapadoo",
                    "name" to "Alerter"
            )
            , mapOf("owner" to "saulmm",
                    "name" to "CoordinatorExamples"
            )
            , mapOf("owner" to "medyo",
                    "name" to "Fancybuttons"
            )
            , mapOf("owner" to "pedant",
                    "name" to "sweet-alert-dialog"
            )
            , mapOf("owner" to "timusus",
                    "name" to "RecyclerView-FastScroll"
            )
            , mapOf("owner" to "woxingxiao",
                    "name" to "BubbleSeekBar"
            )
            , mapOf("owner" to "PhilJay",
                    "name" to "MPAndroidChart"
            )
            , mapOf("owner" to "navermaps",
                    "name" to "maps.android"
            )
            , mapOf("owner" to "bumptech",
                    "name" to "glide",
                    "displayName" to "glide-imgur"
            )
            , mapOf("owner" to "bumptech",
                    "name" to "glide",
                    "displayName" to "glide-flickr"
            )
            , mapOf("owner" to "googlesamples",
            "name" to "android-ConstraintLayoutExamples"
    )
    )
    private var mListItem: ArrayList<Showcase> = arrayListOf<Showcase>()
    private val adapter: ShowcaseAdapter by lazy { 
        ShowcaseAdapter(
                this,
                mListItem,
                AdapterView.OnItemClickListener { _, _, position, _ ->
                    val showCase = adapter.getItem(position)
                    when (showCase.repositoryName()) {
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
        listItems.forEach {
            val owner = it["owner"] ?: ""
            val name = it["name"] ?: ""
            val displayName = it["displayName"] ?: name
            val repository: Repository? = Repository("", "", 0, 0)
            mListItem.add(Showcase(
                    owner,
                    name,
                    displayName,
                    repository?.description ?: "",
                    repository?.stargazers_count ?: 0,
                    repository?.forks_count ?: 0)
            )
            adapter.notifyDataSetChanged()
        }
        runOnUiThread {
            progressBar.visibility = View.GONE
        }
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
