package io.github.aafactory.sample.adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import io.github.aafactory.commons.extensions.makeToast
import io.github.aafactory.sample.R
import io.github.aafactory.sample.api.GitHubService
import io.github.aafactory.sample.helpers.AAFactoryDbHelper
import io.github.aafactory.sample.helpers.GIT_HUB_API_BASE_URL
import io.github.aafactory.sample.models.Repository
import io.github.aafactory.sample.models.Showcase
import kotlinx.android.synthetic.main.item_showcase.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by CHO HANJOONG on 2018-04-19.
 */

class ShowcaseAdapter(
        private val activity: Activity,
        private val listItem: ArrayList<Showcase>,
        private val onItemClickListener: AdapterView.OnItemClickListener
) : RecyclerView.Adapter<ShowcaseAdapter.ShowcaseViewHolder>(){

    override fun getItemViewType(position: Int): Int = if (listItem[position].isCheatSheet) 1 else 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowcaseViewHolder {
        val layoutType = if (viewType == 0) R.layout.item_showcase else R.layout.item_cheatsheet
        return createViewHolder(layoutType, parent)
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: ShowcaseViewHolder, position: Int) {
        holder.bindData(listItem[position])
        holder.itemView.setOnClickListener { item ->
            onItemClickListener.onItemClick(null, item, holder.adapterPosition, holder.itemId)
        }
        holder.colorType?.setOnClickListener { _ ->
            val item = listItem[position]
            CoroutineScope(Dispatchers.IO).launch {
                val retrofit = Retrofit.Builder()
                        .baseUrl(GIT_HUB_API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                val gitHubService = retrofit.create(GitHubService::class.java)
                val call = gitHubService.repository(item.owner, item.name)
                runCatching {
                    val repository: Repository? = call.execute().body()
                    repository?.let {
                        item.description = it.description ?: ""
                        item.stargazersCount = it.stargazers_count
                        item.forksCount = it.forks_count
                        withContext(Dispatchers.Main) {
                            holder.starsAll.text = "${it.stargazers_count}"
                            holder.description.text = it.description
                            holder.forks.text = "${it.forks_count}"
                        }
                        withContext(Dispatchers.Main) {
                            val showcase = Showcase(item.owner, item.name, false, item.description, "", false, item.stargazersCount, item.forksCount)
                            AAFactoryDbHelper.upsertShowcase(showcase)
                            activity.makeToast(AAFactoryDbHelper.countShowcase().toString())
                        }
                    }
                }
            }
        }
    }

    private fun createViewHolder(layoutType: Int, parent: ViewGroup?): ShowcaseViewHolder {
        val view = activity.layoutInflater.inflate(layoutType, parent, false)
        return ShowcaseViewHolder(view as ViewGroup)
    }

    fun getItem(position: Int): Showcase = listItem[position]

    fun attachTo(view: RecyclerView) {
        view.adapter = this
    }

    class ShowcaseViewHolder(containerView: View) : BaseViewHolder<Showcase>(containerView) {
        override fun bindData(data: Showcase) {
            AAFactoryDbHelper.findShowcase(data.owner, data.name)?.let {
                data.description = it.description
                data.stargazersCount = it.stargazersCount
                data.forksCount = it.forksCount
            }

            title.text = data.name
            description.text = data.description
            owner?.text = "Built by ${data.owner}"
            starsAll?.text = if (data.stargazersCount > 0) data.stargazersCount.toString() else "N/A"
            forks?.text = if (data.forksCount > 0) data.forksCount.toString() else "N/A"
        }
    }
}