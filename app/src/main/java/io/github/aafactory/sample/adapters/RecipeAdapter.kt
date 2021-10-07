package io.github.aafactory.sample.adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import io.github.aafactory.sample.R
import io.github.aafactory.sample.models.Recipe
import kotlinx.android.synthetic.main.item_recipe.*


/**
 * Created by CHO HANJOONG on 2018-04-19.
 */

class RecipeAdapter(
        private val activity: Activity,
        private val listItem: ArrayList<Recipe>,
        private val onItemClickListener: AdapterView.OnItemClickListener
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>(){

    private fun createViewHolder(layoutType: Int, parent: ViewGroup?): RecipeViewHolder {
        val view = activity.layoutInflater.inflate(layoutType, parent, false)
        return RecipeViewHolder(view as ViewGroup)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder = createViewHolder(R.layout.item_recipe, parent)

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bindData(listItem[position])
        holder.itemView.setOnClickListener { item ->
            onItemClickListener.onItemClick(null, item, holder.adapterPosition, holder.itemId)
        }
    }

    fun getItem(position: Int): Recipe = listItem[position]

    fun attachTo(view: RecyclerView) {
        view.adapter = this
    }

    class RecipeViewHolder(containerView: View) : BaseViewHolder<Recipe>(containerView) {
        override fun bindData(data: Recipe) {
            title.text = data.title
            description.text = data.description
//            description.text = data.description
//            starsAll.text = "${data.stargazersCount}"
//            forks.text = "${data.forksCount}"
//            owner.text = "Built by ${data.owner}"
        }
    }
}