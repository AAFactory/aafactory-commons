package io.github.aafactory.resolver

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import io.github.aafactory.commons.resolver.MMSResolver
import io.github.aafactory.sample.R
import java.util.*

/**
 * Created by hanjoong on 2016-template_02-12.
 */
class MMSArrayAdapter(
        private val appContext: Context,
        private val layoutResourceId: Int,
        private val data: ArrayList<MMSResolver.MMSDto>
) : ArrayAdapter<MMSResolver.MMSDto>(appContext, layoutResourceId, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var row = convertView
        val holder: ViewHolder?

        if (row == null) {
            val inflater = (context as Activity).layoutInflater
            row = inflater.inflate(layoutResourceId, parent, false)

            holder = ViewHolder()
            holder.textView1 = row.findViewById(R.id.text1) as TextView
            holder.textView2 = row.findViewById(R.id.text2) as TextView
            holder.textView3 = row.findViewById(R.id.text3) as TextView
            row.tag = holder

        } else {
            holder = row.tag as ViewHolder
        }

        val smsDto = data[position]

        holder.textView1?.text = smsDto.address
        holder.textView2?.text = smsDto.getTimestampString()
        holder.textView3?.text = smsDto.body

        return row
    }

    internal class ViewHolder {
        var textView1: TextView? = null
        var textView2: TextView? = null
        var textView3: TextView? = null
    }
}
