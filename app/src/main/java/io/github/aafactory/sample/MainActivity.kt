package io.github.aafactory.sample

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AdapterView
import cn.pedant.SweetAlert.sample.SweetAlertDialogActivity
import io.github.aafactory.commons.extensions.dpToPixel
import io.github.aafactory.sample.adapters.ShowcaseAdapter
import io.github.aafactory.sample.api.GitHubService
import io.github.aafactory.sample.helpers.GIT_HUB_API_BASE_URL
import io.github.aafactory.sample.models.Repository
import io.github.aafactory.sample.models.Showcase
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_toolbar.*
import mehdi.sakout.fancybuttons.samples.MainActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
                        "KedditBySteps" -> startActivity(Intent(this, com.droidcba.kedditbysteps.MainActivity::class.java))
                        "sweet-alert-dialog" -> startActivity(Intent(this, SweetAlertDialogActivity::class.java))
                        "RecyclerView-FastScroll" -> startActivity(Intent(this, com.simplecityapps.recyclerview_fastscroll.sample.MainActivity::class.java))
                        "BubbleSeekBar" -> startActivity(Intent(this, com.xw.samlpe.bubbleseekbar.sample.MainActivity::class.java))
                        "MPAndroidChart" -> startActivity(Intent(this, com.xxmassdeveloper.mpchartexample.notimportant.MainActivity::class.java))
                        "glide" -> startActivity(Intent(this, com.bumptech.glide.samples.imgur.MainActivity::class.java))
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
        val owners = resources.getStringArray(R.array.owners)
        val repositories = resources.getStringArray(R.array.repositories)

        Thread(Runnable {
            val retrofit = Retrofit.Builder()
                    .baseUrl(GIT_HUB_API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            val gitHubService = retrofit.create(GitHubService::class.java)
            for ((index, owner) in owners.withIndex()) {
                val call = gitHubService.repository(owner, repositories[index])
                val repository: Repository? = call.execute().body()
                mListItem.add(Showcase(
                        owner,
                        repositories[index],
                        repository?.description ?: "",
                        repository?.stargazers_count ?: 0,
                        repository?.forks_count ?: 0)
                )
            }
            runOnUiThread { 
                adapter.notifyDataSetChanged()
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
