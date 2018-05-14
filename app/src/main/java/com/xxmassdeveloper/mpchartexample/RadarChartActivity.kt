package com.xxmassdeveloper.mpchartexample

import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet
import com.xxmassdeveloper.mpchartexample.custom.RadarMarkerView
import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase
import io.github.aafactory.sample.R
import kotlinx.android.synthetic.main.content_toolbar.*
import kotlinx.android.synthetic.main.mpandroidchart_activity_radarchart_noseekbar.*
import java.util.*

class RadarChartActivity : DemoBase() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.mpandroidchart_activity_radarchart_noseekbar)
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
        }

        textView.typeface = mTfLight
        textView.setTextColor(Color.WHITE)
        textView.setBackgroundColor(Color.rgb(60, 65, 82))

        chart1.setBackgroundColor(Color.rgb(60, 65, 82))

        chart1.description.isEnabled = false

        chart1.webLineWidth = 1f
        chart1.webColor = Color.LTGRAY
        chart1.webLineWidthInner = 1f
        chart1.webColorInner = Color.LTGRAY
        chart1.webAlpha = 100

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        val mv = RadarMarkerView(this, R.layout.mpandroidchart_radar_markerview)
        mv.chartView = chart1 // For bounds control
        chart1.marker = mv // Set the marker to the chart

        setData()

        chart1.animateXY(1400, 1400, Easing.EasingOption.EaseInCirc, Easing.EasingOption.EaseInCirc)

        val xAxis = chart1.xAxis
        xAxis.typeface = mTfLight
        xAxis.textSize = 9f
        xAxis.yOffset = 0f
        xAxis.xOffset = 0f
        xAxis.valueFormatter = object : IAxisValueFormatter {

            private val mActivities = arrayOf("Burger", "Steak", "Salad", "Pasta", "Pizza")

            override fun getFormattedValue(value: Float, axis: AxisBase): String {
                return mActivities[value.toInt() % mActivities.size]
            }
        }
        xAxis.textColor = Color.WHITE

        val yAxis = chart1.yAxis
        yAxis.typeface = mTfLight
        yAxis.setLabelCount(5, false)
        yAxis.textSize = 9f
        yAxis.axisMinimum = 0f
        yAxis.axisMaximum = 80f
        yAxis.setDrawLabels(false)

        val l = chart1.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        l.typeface = mTfLight
        l.xEntrySpace = 7f
        l.yEntrySpace = 5f
        l.textColor = Color.WHITE
    }

    fun setData() {

        val mult = 80f
        val min = 20f
        val cnt = 5

        val entries1 = ArrayList<RadarEntry>()
        val entries2 = ArrayList<RadarEntry>()

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (i in 0 until cnt) {
            val val1 = (Math.random() * mult).toFloat() + min
            entries1.add(RadarEntry(val1))

            val val2 = (Math.random() * mult).toFloat() + min
            entries2.add(RadarEntry(val2))
        }

        val set1 = RadarDataSet(entries1, "Last Week")
        set1.color = Color.rgb(103, 110, 129)
        set1.fillColor = Color.rgb(103, 110, 129)
        set1.setDrawFilled(true)
        set1.fillAlpha = 180
        set1.lineWidth = 2f
        set1.isDrawHighlightCircleEnabled = true
        set1.setDrawHighlightIndicators(false)

        val set2 = RadarDataSet(entries2, "This Week")
        set2.color = Color.rgb(121, 162, 175)
        set2.fillColor = Color.rgb(121, 162, 175)
        set2.setDrawFilled(true)
        set2.fillAlpha = 180
        set2.lineWidth = 2f
        set2.isDrawHighlightCircleEnabled = true
        set2.setDrawHighlightIndicators(false)

        val sets = ArrayList<IRadarDataSet>()
        sets.add(set1)
        sets.add(set2)

        val data = RadarData(sets)
        data.setValueTypeface(mTfLight)
        data.setValueTextSize(8f)
        data.setDrawValues(false)
        data.setValueTextColor(Color.WHITE)

        chart1.data = data
        chart1.invalidate()
    }
}
