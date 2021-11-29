package io.github.aafactory.sample.activities

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.aafactory.commons.R
import io.github.aafactory.commons.activities.BaseSimpleActivity
import io.github.aafactory.commons.extensions.makeToast
import io.github.aafactory.sample.adapters.ContributorAdapter
import io.github.aafactory.sample.databinding.ActivityContributorBinding
import io.github.aafactory.sample.helpers.ItemDecoration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class ContributorActivity : BaseSimpleActivity() {
    private lateinit var mBinding: ActivityContributorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityContributorBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setSupportActionBar(mBinding.toolbar)
        supportActionBar?.run {
            title = "Contributor"
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_cross)
        }

        CoroutineScope(Dispatchers.IO).launch {
            kotlin.runCatching {
                val retrofit: Retrofit = Retrofit.Builder()
                        .baseUrl(API_URL_CUSTOM)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                val github = retrofit.create(CustomData::class.java)
                val call = github.findContributors()
                val contributors: List<ContributorAdapter.Contributor>? = call.execute().body()
                withContext(Dispatchers.Main) {
                    mBinding.run {
                        progressbarLoading.visibility = View.GONE
                        makeToast("size: ${contributors!!.size}")
                        recyclerContributors.run {
                            adapter = ContributorAdapter(this@ContributorActivity, contributors!!)
                            layoutManager = LinearLayoutManager(this@ContributorActivity, LinearLayoutManager.VERTICAL, false)
//                            layoutManager = GridLayoutManager(this@ContributorActivity, 1)
//                            addItemDecoration(ItemDecoration(this@ContributorActivity))
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val API_URL_CUSTOM = "https://raw.githubusercontent.com"
    }

    interface CustomData {
        @GET("/hanjoongcho/aaf-easydiary/master/data/contributors.json")
        fun findContributors(): retrofit2.Call<List<ContributorAdapter.Contributor>>
    }
}