package com.xxmassdeveloper.mpchartexample.custom

import android.content.Context
import android.graphics.Typeface
import android.widget.TextView

import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF

import java.text.DecimalFormat

import io.github.aafactory.sample.R

/**
 * Custom implementation of the MarkerView.
 *
 * @author Philipp Jahoda
 */
class RadarMarkerView(context: Context, layoutResource: Int) : MarkerView(context, layoutResource) {

    private val tvContent: TextView = findViewById<TextView>(R.id.tvContent)
    private val format = DecimalFormat("##0")

    init {
        tvContent.typeface = Typeface.createFromAsset(context.assets, "OpenSans-Light.ttf")    
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        e?.let {
            tvContent.text = format.format(e.y.toDouble()) + " %"
        }
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF((-(width / 2)).toFloat(), (-height - 10).toFloat())
    }
}
