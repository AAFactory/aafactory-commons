package io.github.aafactory.sample.adapters

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import android.widget.AdapterView
import io.github.aafactory.sample.R
import io.github.aafactory.sample.models.Showcase
import kotlinx.android.synthetic.main.item_showcase.*
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
            Log.i("", listItem[position].repositoryName());
        }
    }

    fun getItem(position: Int): Showcase = listItem[position]

    fun attachTo(view: RecyclerView) {
        view.adapter = this
    }
}