package com.xxmassdeveloper.mpchartexample

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BubbleData
import com.github.mikephil.charting.data.BubbleDataSet
import com.github.mikephil.charting.data.BubbleEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.IBubbleDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase
import io.github.aafactory.sample.R
import kotlinx.android.synthetic.main.content_toolbar.*
import kotlinx.android.synthetic.main.mpandroidchart_activity_bubblechart.*
import java.util.*

class BubbleChartActivity : DemoBase(), OnSeekBarChangeListener, OnChartValueSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.mpandroidchart_activity_bubblechart)
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
        }

        seekBar1.setOnSeekBarChangeListener(this)
        seekBar2.setOnSeekBarChangeListener(this)

        chart1.description.isEnabled = false
        chart1.setOnChartValueSelectedListener(this)
        chart1.setDrawGridBackground(false)
        chart1.setTouchEnabled(true)

        // enable scaling and dragging
        chart1.isDragEnabled = true
        chart1.setScaleEnabled(true)

        chart1.setMaxVisibleValueCount(200)
        chart1.setPinchZoom(true)

        seekBar1.progress = 10
        seekBar2.progress = 50

        val l = chart1.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.typeface = mTfLight

        val yl = chart1.axisLeft
        yl.typeface = mTfLight
        yl.spaceTop = 30f
        yl.spaceBottom = 30f
        yl.setDrawZeroLine(false)

        chart1.axisRight.isEnabled = false

        val xl = chart1.xAxis
        xl.position = XAxis.XAxisPosition.BOTTOM
        xl.typeface = mTfLight
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {

        val count = seekBar1.progress
        val range = seekBar2.progress

        tvXMax.text = count.toString()
        tvYMax.text = range.toString()

        val yVals1 = ArrayList<BubbleEntry>()
        val yVals2 = ArrayList<BubbleEntry>()
        val yVals3 = ArrayList<BubbleEntry>()

        for (i in 0 until count) {
            val `val` = (Math.random() * range).toFloat()
            val size = (Math.random() * range).toFloat()

            yVals1.add(BubbleEntry(i.toFloat(), `val`, size, ContextCompat.getDrawable(this, R.drawable.star)))
        }

        for (i in 0 until count) {
            val `val` = (Math.random() * range).toFloat()
            val size = (Math.random() * range).toFloat()

            yVals2.add(BubbleEntry(i.toFloat(), `val`, size, ContextCompat.getDrawable(this, R.drawable.star)))
        }

        for (i in 0 until count) {
            val `val` = (Math.random() * range).toFloat()
            val size = (Math.random() * range).toFloat()

            yVals3.add(BubbleEntry(i.toFloat(), `val`, size))
        }

        // create a dataset and give it a type
        val set1 = BubbleDataSet(yVals1, "DS 1")
        set1.setDrawIcons(false)
        set1.setColor(ColorTemplate.COLORFUL_COLORS[0], 130)
        set1.setDrawValues(true)

        val set2 = BubbleDataSet(yVals2, "DS 2")
        set2.setDrawIcons(false)
        set2.iconsOffset = MPPointF(0f, 15f)
        set2.setColor(ColorTemplate.COLORFUL_COLORS[1], 130)
        set2.setDrawValues(true)

        val set3 = BubbleDataSet(yVals3, "DS 3")
        set3.setColor(ColorTemplate.COLORFUL_COLORS[2], 130)
        set3.setDrawValues(true)

        val dataSets = ArrayList<IBubbleDataSet>()
        dataSets.add(set1) // add the datasets
        dataSets.add(set2)
        dataSets.add(set3)

        // create a data object with the datasets
        val data = BubbleData(dataSets)
        data.setDrawValues(false)
        data.setValueTypeface(mTfLight)
        data.setValueTextSize(8f)
        data.setValueTextColor(Color.WHITE)
        data.setHighlightCircleWidth(1.5f)

        chart1.data = data
        chart1.invalidate()
    }

    override fun onValueSelected(e: Entry, h: Highlight) {
        Log.i("VAL SELECTED",
                "Value: " + e.y + ", xIndex: " + e.x
                        + ", DataSet index: " + h.dataSetIndex)
    }

    override fun onNothingSelected() {
        // TODO Auto-generated method stub

    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {
        // TODO Auto-generated method stub

    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
        // TODO Auto-generated method stub
    }
}
