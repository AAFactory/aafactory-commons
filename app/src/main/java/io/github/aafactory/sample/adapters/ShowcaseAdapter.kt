package io.github.aafactory.sample.adapters

import android.app.Activity
import android.os.AsyncTask
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import android.widget.AdapterView
import io.github.aafactory.sample.R
import io.github.aafactory.sample.api.GitHubService
import io.github.aafactory.sample.helpers.GIT_HUB_API_BASE_URL
import io.github.aafactory.sample.models.Repository
import io.github.aafactory.sample.models.Showcase
import kotlinx.android.synthetic.main.item_showcase.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*



/**
 * Created by CHO HANJOONG on 2018-04-19.
 */

class ShowcaseAdapter(
        private val activity: Activity,
        private val listItem: ArrayList<Showcase>,
        private val onItemClickListener: AdapterView.OnItemClickListener
) : RecyclerView.Adapter<ShowcaseViewHolder>(){

    private fun createViewHolder(layoutType: Int, parent: ViewGroup?): ShowcaseViewHolder {
        val view = activity.layoutInflater.inflate(layoutType, parent, false)
        return ShowcaseViewHolder(view as ViewGroup)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowcaseViewHolder = createViewHolder(R.layout.item_showcase, parent)

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: ShowcaseViewHolder, position: Int) {
        holder.bindData(listItem[position])
        holder.itemView.setOnClickListener { item ->
            onItemClickListener.onItemClick(null, item, holder.adapterPosition, holder.itemId)
        }
        holder.colorType.setOnClickListener { _ ->
            Log.i("", listItem[position].repositoryName())
            val item = listItem[position]
            RepoInfoTask(holder, item).execute()
        }
    }

    fun getItem(position: Int): Showcase = listItem[position]

    fun attachTo(view: RecyclerView) {
        view.adapter = this
    }

    internal inner class RepoInfoTask(
            val holder: ShowcaseViewHolder,
            val item: Showcase) : AsyncTask<String, Void, Repository>() {

        private var exception: Exception? = null

        override fun doInBackground(vararg urls: String): Repository? {
            val retrofit = Retrofit.Builder()
                    .baseUrl(GIT_HUB_API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            val gitHubService = retrofit.create(GitHubService::class.java)
            val call = gitHubService.repository(item.owner, item.name)
            val repository: Repository? = call.execute().body()
            repository?.let {
                item.description = it.description
                item.stargazersCount = it.stargazers_count
                item.forksCount = it.forks_count
            }
            return repository
        }

        override fun onPostExecute(repository: Repository?) {
            repository?.let {
                holder.starsAll.text = "${it.stargazers_count}"
                holder.description.text = it.description
                holder.forks.text = "${it.forks_count}"
            }
        }
    }
}