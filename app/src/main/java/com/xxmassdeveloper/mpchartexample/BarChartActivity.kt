package com.xxmassdeveloper.mpchartexample

import android.annotation.SuppressLint
import android.graphics.RectF
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.model.GradientColor
import com.github.mikephil.charting.utils.MPPointF
import com.xxmassdeveloper.mpchartexample.custom.DayAxisValueFormatter
import com.xxmassdeveloper.mpchartexample.custom.MyAxisValueFormatter
import com.xxmassdeveloper.mpchartexample.custom.XYMarkerView
import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase
import io.github.aafactory.sample.R
import kotlinx.android.synthetic.main.content_toolbar.*
import kotlinx.android.synthetic.main.mpandroidchart_activity_barchart.*
import java.util.*

class BarChartActivity : DemoBase(), OnSeekBarChangeListener, OnChartValueSelectedListener {
    private var mOnValueSelectedRectF = RectF()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.mpandroidchart_activity_barchart)
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
        }

        chart1.setOnChartValueSelectedListener(this)
        chart1.setDrawBarShadow(false)
        chart1.setDrawValueAboveBar(true)
        chart1.description.isEnabled = false

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart1.setMaxVisibleValueCount(60)

        // scaling can now only be done on x- and y-axis separately
        chart1.setPinchZoom(false)

        chart1.setDrawGridBackground(false)
        // mChart.setDrawYLabels(false);

        val xAxisFormatter = DayAxisValueFormatter(chart1)

        val xAxis = chart1.xAxis
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.typeface = mTfLight
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f // only intervals of 1 day
        xAxis.labelCount = 7
        xAxis.valueFormatter = xAxisFormatter

        val custom = MyAxisValueFormatter()

        val leftAxis = chart1.axisLeft
        leftAxis.typeface = mTfLight
        leftAxis.setLabelCount(8, false)
        leftAxis.valueFormatter = custom
        leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.spaceTop = 15f
        leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)

        val rightAxis = chart1.axisRight
        rightAxis.setDrawGridLines(false)
        rightAxis.typeface = mTfLight
        rightAxis.setLabelCount(8, false)
        rightAxis.valueFormatter = custom
        rightAxis.spaceTop = 15f
        rightAxis.axisMinimum = 0f // this replaces setStartAtZero(true)

        val l = chart1.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        l.form = LegendForm.SQUARE
        l.formSize = 9f
        l.textSize = 11f
        l.xEntrySpace = 4f
        // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });

        val mv = XYMarkerView(this, xAxisFormatter)
        mv.chartView = chart1 // For bounds control
        chart1.marker = mv // Set the marker to the chart

        setData(12, 50f)

        // setting data
        seekBar2.progress = 50
        seekBar1.progress = 12

        seekBar2.setOnSeekBarChangeListener(this)
        seekBar1.setOnSeekBarChangeListener(this)

        // mChart.setDrawLegend(false);
    }


    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        tvXMax.text = String.format("%d", seekBar1.progress + 2)
        tvYMax.text = "${seekBar2.progress}" 

        setData(seekBar1.progress + 1, seekBar2.progress.toFloat())
        chart1.invalidate()
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {
        // TODO Auto-generated method stub
    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
        // TODO Auto-generated method stub
    }

    private fun setData(count: Int, range: Float) {

        val start = 1f

        val yVals1 = ArrayList<BarEntry>()

        var i = start.toInt()
        while (i < start + count.toFloat() + 1f) {
            val mult = range + 1
            val `val` = (Math.random() * mult).toFloat()

            if (Math.random() * 100 < 25) {
                yVals1.add(BarEntry(i.toFloat(), `val`, ContextCompat.getDrawable(this, R.drawable.star)))
            } else {
                yVals1.add(BarEntry(i.toFloat(), `val`))
            }
            i++
        }

        val set1: BarDataSet

        if (chart1.data != null && chart1.data.dataSetCount > 0) {
            set1 = chart1.data.getDataSetByIndex(0) as BarDataSet
            set1.values = yVals1
            chart1.data.notifyDataChanged()
            chart1.notifyDataSetChanged()
        } else {
            set1 = BarDataSet(yVals1, "The year 2017")

            set1.setDrawIcons(false)

            //            set1.setColors(ColorTemplate.MATERIAL_COLORS);

            /*int startColor = ContextCompat.getColor(this, android.R.color.holo_blue_dark);
            int endColor = ContextCompat.getColor(this, android.R.color.holo_blue_bright);
            set1.setGradientColor(startColor, endColor);*/

            val startColor1 = ContextCompat.getColor(this, android.R.color.holo_orange_light)
            val startColor2 = ContextCompat.getColor(this, android.R.color.holo_blue_light)
            val startColor3 = ContextCompat.getColor(this, android.R.color.holo_orange_light)
            val startColor4 = ContextCompat.getColor(this, android.R.color.holo_green_light)
            val startColor5 = ContextCompat.getColor(this, android.R.color.holo_red_light)
            val endColor1 = ContextCompat.getColor(this, android.R.color.holo_blue_dark)
            val endColor2 = ContextCompat.getColor(this, android.R.color.holo_purple)
            val endColor3 = ContextCompat.getColor(this, android.R.color.holo_green_dark)
            val endColor4 = ContextCompat.getColor(this, android.R.color.holo_red_dark)
            val endColor5 = ContextCompat.getColor(this, android.R.color.holo_orange_dark)

            val gradientColors = ArrayList<GradientColor>()
            gradientColors.add(GradientColor(startColor1, endColor1))
            gradientColors.add(GradientColor(startColor2, endColor2))
            gradientColors.add(GradientColor(startColor3, endColor3))
            gradientColors.add(GradientColor(startColor4, endColor4))
            gradientColors.add(GradientColor(startColor5, endColor5))

            //            set1.setGradientColors(gradientColors);

            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set1)

            val data = BarData(dataSets)
            data.setValueTextSize(10f)
            data.setValueTypeface(mTfLight)
            data.barWidth = 0.9f

            chart1.data = data
        }
    }

    @SuppressLint("NewApi")
    override fun onValueSelected(e: Entry?, h: Highlight) {

        if (e == null)
            return

        val bounds = mOnValueSelectedRectF
        chart1.getBarBounds(e as BarEntry?, bounds)
        val position = chart1.getPosition(e, AxisDependency.LEFT)

        Log.i("bounds", bounds.toString())
        Log.i("position", position.toString())
        Log.i("x-index","low: " + chart1.lowestVisibleX + ", high: " + chart1.highestVisibleX)

        MPPointF.recycleInstance(position)
    }

    override fun onNothingSelected() {}
}
