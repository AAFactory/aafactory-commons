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
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.MPPointF
import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase
import io.github.aafactory.sample.R
import kotlinx.android.synthetic.main.content_toolbar.*
import kotlinx.android.synthetic.main.mpandroidchart_activity_horizontalbarchart.*
import java.util.*

class HorizontalBarChartActivity : DemoBase(), OnSeekBarChangeListener, OnChartValueSelectedListener {
    private var mOnValueSelectedRectF = RectF()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.mpandroidchart_activity_horizontalbarchart)
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
        }
        
        chart1.setOnChartValueSelectedListener(this)
        // chart1.setHighlightEnabled(false);

        chart1.setDrawBarShadow(false)
        chart1.setDrawValueAboveBar(true)
        chart1.description.isEnabled = false

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart1.setMaxVisibleValueCount(60)

        // scaling can now only be done on x- and y-axis separately
        chart1.setPinchZoom(false)

        // draw shadows for each bar that show the maximum value
        // chart1.setDrawBarShadow(true);

        chart1.setDrawGridBackground(false)

        val xl = chart1.xAxis
        xl.position = XAxisPosition.BOTTOM
        xl.typeface = mTfLight
        xl.setDrawAxisLine(true)
        xl.setDrawGridLines(false)
        xl.granularity = 10f

        val yl = chart1.axisLeft
        yl.typeface = mTfLight
        yl.setDrawAxisLine(true)
        yl.setDrawGridLines(true)
        yl.axisMinimum = 0f // this replaces setStartAtZero(true)
        //        yl.setInverted(true);

        val yr = chart1.axisRight
        yr.typeface = mTfLight
        yr.setDrawAxisLine(true)
        yr.setDrawGridLines(false)
        yr.axisMinimum = 0f // this replaces setStartAtZero(true)
        //        yr.setInverted(true);

        setData(12, 50f)
        chart1.setFitBars(true)
        chart1.animateY(2500)

        // setting data
        seekBar2.progress = 50
        seekBar1.progress = 12

        seekBar2.setOnSeekBarChangeListener(this)
        seekBar1.setOnSeekBarChangeListener(this)

        val l = chart1.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        l.formSize = 8f
        l.xEntrySpace = 4f
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        tvXMax.text = String.format("%d", seekBar1.progress + 1)
        tvYMax.text = "${seekBar2.progress}" 
        
        setData(seekBar1.progress + 1, seekBar2.progress.toFloat())
        chart1.setFitBars(true)
        chart1.invalidate()
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {
        // TODO Auto-generated method stub

    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
        // TODO Auto-generated method stub

    }

    private fun setData(count: Int, range: Float) {
        val barWidth = 9f
        val spaceForBar = 10f
        val yVals1 = ArrayList<BarEntry>()

        for (i in 0 until count) {
            val `val` = (Math.random() * range).toFloat()
            yVals1.add(BarEntry(i * spaceForBar, `val`, ContextCompat.getDrawable(this, R.drawable.star)))
        }

        val set1: BarDataSet

        if (chart1.data != null && chart1.data.dataSetCount > 0) {
            set1 = chart1.data.getDataSetByIndex(0) as BarDataSet
            set1.values = yVals1
            chart1.data.notifyDataChanged()
            chart1.notifyDataSetChanged()
        } else {
            set1 = BarDataSet(yVals1, "DataSet 1")

            set1.setDrawIcons(false)

            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set1)

            val data = BarData(dataSets)
            data.setValueTextSize(10f)
            data.setValueTypeface(mTfLight)
            data.barWidth = barWidth
            chart1.data = data
        }
    }

    @SuppressLint("NewApi")
    override fun onValueSelected(e: Entry?, h: Highlight) {
        if (e == null)
            return

        val bounds = mOnValueSelectedRectF
        chart1.getBarBounds(e as BarEntry?, bounds)

        val position = chart1.getPosition(e, chart1.data.getDataSetByIndex(h.dataSetIndex)
                .axisDependency)

        Log.i("bounds", bounds.toString())
        Log.i("position", position.toString())

        MPPointF.recycleInstance(position)
    }

    override fun onNothingSelected() {}
}
