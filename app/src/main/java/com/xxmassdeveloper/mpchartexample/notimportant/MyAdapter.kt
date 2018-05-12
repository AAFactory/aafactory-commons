package com.xxmassdeveloper.mpchartexample.notimportant

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

import io.github.aafactory.sample.R

/**
 * Created by Philipp Jahoda on 07/12/15.
 */
class MyAdapter(context: Context, objects: List<ContentItem>) : ArrayAdapter<ContentItem>(context, 0, objects) {

    private val mTypeFaceLight: Typeface = Typeface.createFromAsset(context.assets, "OpenSans-Light.ttf")
    private val mTypeFaceRegular: Typeface = Typeface.createFromAsset(context.assets, "OpenSans-Regular.ttf")

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var convertView = convertView

        val c = getItem(position)

        var holder: ViewHolder?

        if (convertView == null) {

            holder = ViewHolder()

            convertView = LayoutInflater.from(context).inflate(R.layout.mpandroidchart_list_item, null)
            holder.tvName = convertView.findViewById<View>(R.id.tvName) as TextView
            holder.tvDesc = convertView.findViewById<View>(R.id.tvDesc) as TextView
            holder.tvNew = convertView.findViewById<View>(R.id.tvNew) as TextView

            convertView.tag = holder

        } else {
            holder = convertView.tag as ViewHolder
        }

        holder.tvNew?.typeface = mTypeFaceRegular
        holder.tvName?.typeface = mTypeFaceLight
        holder.tvDesc?.typeface = mTypeFaceLight

        holder.tvName?.text = c.name
        holder.tvDesc?.text = c.desc

        if (c.isNew)
            holder.tvNew?.visibility = View.VISIBLE
        else
            holder.tvNew?.visibility = View.GONE

        return convertView
    }

    private inner class ViewHolder {
        internal var tvName: TextView? = null
        internal var tvDesc: TextView? = null
        internal var tvNew: TextView? = null
    }
}
