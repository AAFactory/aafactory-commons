package com.xxmassdeveloper.mpchartexample.custom

import android.content.Context
import android.widget.TextView

import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF

import java.text.DecimalFormat

import io.github.aafactory.sample.R

/**
 * Custom implementation of the MarkerView.
 *
 * @author Philipp Jahoda
 */
class XYMarkerView(context: Context, private val xAxisValueFormatter: IAxisValueFormatter) : MarkerView(context, R.layout.mpandroidchart_custom_marker_view) {

    private val tvContent: TextView = findViewById<TextView>(R.id.tvContent)
    private val format: DecimalFormat = DecimalFormat("###.0")

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        e?.let {
            tvContent.text = "x: ${xAxisValueFormatter.getFormattedValue(e.x, null)}, y: ${format.format(e.y.toDouble())}" 
        }
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
    }
}
