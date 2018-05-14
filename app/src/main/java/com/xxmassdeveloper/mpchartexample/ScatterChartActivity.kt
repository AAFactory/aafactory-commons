package com.xxmassdeveloper.mpchartexample

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.github.mikephil.charting.charts.ScatterChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.ScatterData
import com.github.mikephil.charting.data.ScatterDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.xxmassdeveloper.mpchartexample.custom.CustomScatterShapeRenderer
import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase
import io.github.aafactory.sample.R
import kotlinx.android.synthetic.main.content_toolbar.*
import kotlinx.android.synthetic.main.mpandroidchart_activity_scatterchart.*
import java.util.*

class ScatterChartActivity : DemoBase(), OnSeekBarChangeListener, OnChartValueSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.mpandroidchart_activity_scatterchart)
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
        chart1.maxHighlightDistance = 50f

        // enable scaling and dragging
        chart1.isDragEnabled = true
        chart1.setScaleEnabled(true)

        chart1.setMaxVisibleValueCount(200)
        chart1.setPinchZoom(true)

        seekBar1.progress = 45
        seekBar2.progress = 100

        val l = chart1.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.typeface = mTfLight
        l.xOffset = 5f

        val yl = chart1.axisLeft
        yl.typeface = mTfLight
        yl.axisMinimum = 0f // this replaces setStartAtZero(true)

        chart1.axisRight.isEnabled = false

        val xl = chart1.xAxis
        xl.typeface = mTfLight
        xl.setDrawGridLines(false)
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {

        tvXMax.text = "" + (seekBar1.progress + 1)
        tvYMax.text = "" + seekBar2.progress

        val yVals1 = ArrayList<Entry>()
        val yVals2 = ArrayList<Entry>()
        val yVals3 = ArrayList<Entry>()

        for (i in 0 until seekBar1.progress) {
            val `val` = (Math.random() * seekBar2.progress).toFloat() + 3
            yVals1.add(Entry(i.toFloat(), `val`))
        }

        for (i in 0 until seekBar1.progress) {
            val `val` = (Math.random() * seekBar2.progress).toFloat() + 3
            yVals2.add(Entry(i + 0.33f, `val`))
        }

        for (i in 0 until seekBar1.progress) {
            val `val` = (Math.random() * seekBar2.progress).toFloat() + 3
            yVals3.add(Entry(i + 0.66f, `val`))
        }

        // create a dataset and give it a type
        val set1 = ScatterDataSet(yVals1, "DS 1")
        set1.setScatterShape(ScatterChart.ScatterShape.SQUARE)
        set1.color = ColorTemplate.COLORFUL_COLORS[0]
        val set2 = ScatterDataSet(yVals2, "DS 2")
        set2.setScatterShape(ScatterChart.ScatterShape.CIRCLE)
        set2.scatterShapeHoleColor = ColorTemplate.COLORFUL_COLORS[3]
        set2.scatterShapeHoleRadius = 3f
        set2.color = ColorTemplate.COLORFUL_COLORS[1]
        val set3 = ScatterDataSet(yVals3, "DS 3")
        set3.shapeRenderer = CustomScatterShapeRenderer()
        set3.color = ColorTemplate.COLORFUL_COLORS[2]

        set1.scatterShapeSize = 8f
        set2.scatterShapeSize = 8f
        set3.scatterShapeSize = 8f

        val dataSets = ArrayList<IScatterDataSet>()
        dataSets.add(set1) // add the datasets
        dataSets.add(set2)
        dataSets.add(set3)

        // create a data object with the datasets
        val data = ScatterData(dataSets)
        data.setValueTypeface(mTfLight)

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
