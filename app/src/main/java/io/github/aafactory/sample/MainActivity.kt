package io.github.aafactory.sample

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AdapterView
import cn.pedant.SweetAlert.sample.SweetAlertDialogActivity
import com.litesuits.common.utils.DialogUtil
import com.nhn.android.mapviewer.NMapViewer
import io.github.aafactory.commons.activities.BaseSimpleActivity
import io.github.aafactory.commons.extensions.dpToPixel
import io.github.aafactory.commons.helpers.PERMISSION_ACCESS_COARSE_LOCATION
import io.github.aafactory.commons.helpers.PERMISSION_ACCESS_FINE_LOCATION
import io.github.aafactory.resolver.MMSActivity
import io.github.aafactory.sample.adapters.ShowcaseAdapter
import io.github.aafactory.sample.api.GitHubService
import io.github.aafactory.sample.helpers.GIT_HUB_API_BASE_URL
import io.github.aafactory.sample.models.Repository
import io.github.aafactory.sample.models.Showcase
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.activity_main.*
import mehdi.sakout.fancybuttons.samples.MainActivity
import org.openlayers.OpenlayersActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : BaseSimpleActivity() {
    val listItems = mutableListOf<Map<String, String>>(
            mapOf("owner" to "rubensousa",
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
                        "Alerter" -> startActivity(Intent(this, com.tapadoo.alerter.sample.MainActivity::class.java))
                        "CoordinatorExamples" -> startActivity(Intent(this, saulmm.coordinatorexamples.MainActivity::class.java))
                        "KedditBySteps" -> startActivity(Intent(this, com.droidcba.kedditbysteps.MainActivity::class.java))
                        "sweet-alert-dialog" -> startActivity(Intent(this, SweetAlertDialogActivity::class.java))
                        "RecyclerView-FastScroll" -> startActivity(Intent(this, com.simplecityapps.recyclerview_fastscroll.sample.MainActivity::class.java))
                        "BubbleSeekBar" -> startActivity(Intent(this, com.xw.samlpe.bubbleseekbar.sample.MainActivity::class.java))
                        "MPAndroidChart" -> startActivity(Intent(this, com.xxmassdeveloper.mpchartexample.notimportant.MainActivity::class.java))
                        "glide-imgur" -> startActivity(Intent(this, com.bumptech.glide.samples.imgur.MainActivity::class.java))
                        "glide-flickr" -> startActivity(Intent(this, com.bumptech.glide.samples.flickr.FlickrSearchActivity::class.java))
                        "maps.android" -> {
                            handlePermission(PERMISSION_ACCESS_COARSE_LOCATION) {
                                when (it) {
                                    true -> {
                                        handlePermission(PERMISSION_ACCESS_FINE_LOCATION) {
                                            when (it) {
                                                true -> startActivity(Intent(this, NMapViewer::class.java))
                                                false -> { DialogUtil.showTips(this@MainActivity, "WARN", "Permission Denied")}
                                            }
                                        }
                                    }
                                    false -> { DialogUtil.showTips(this@MainActivity, "WARN", "Permission Denied")}
                                }
                            }
                        }
                        "android-ConstraintLayoutExamples" -> startActivity(Intent(this, com.example.android.constraintlayoutexamples.MainActivity::class.java))
                        "openlayers" -> startActivity(Intent(this, OpenlayersActivity::class.java))
                        "aafactory-commons" -> startActivity(Intent(this, MMSActivity::class.java))
                        "recyclerview-playground" -> startActivity(Intent(this, com.example.android.recyclerplayground.MainActivity::class.java))
                        "ViewPagerCards" -> startActivity(Intent(this, com.github.rubensousa.viewpagercards.MainActivity::class.java))
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
//        val owners = resources.getStringArray(R.array.owners)
//        val repositories = resources.getStringArray(R.array.repositories)

        Thread(Runnable {
            val retrofit = Retrofit.Builder()
                    .baseUrl(GIT_HUB_API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            val gitHubService = retrofit.create(GitHubService::class.java)
//            for ((index, owner) in owners.withIndex()) {
//                val call = gitHubService.repository(owner, repositories[index])
//                val repository: Repository? = call.execute().body()
//                mListItem.add(Showcase(
//                        owner,
//                        repositories[index],
//                        repository?.description ?: "",
//                        repository?.stargazers_count ?: 0,
//                        repository?.forks_count ?: 0)
//                )
//            }
            listItems.forEach {
                val owner = it["owner"] ?: ""
                val name = it["name"] ?: ""
                val displayName = it["displayName"] ?: name
                val call = gitHubService.repository(owner, name)
//                val repository: Repository? = call.execute().body()
                val repository: Repository? = Repository("", "", 0, 0)   
                mListItem.add(Showcase(
                        owner,
                        name,
                        displayName,
                        repository?.description ?: "",
                        repository?.stargazers_count ?: 0,
                        repository?.forks_count ?: 0)
                )
                runOnUiThread {
                    adapter.notifyDataSetChanged()
                }
            }
            runOnUiThread {
                progressBar.visibility = View.GONE
            }
        }).start()
    }

    private inner class ItemDecoration(private val context: Context) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect?.bottom = context.dpToPixel(3F)
        }
    }
}
