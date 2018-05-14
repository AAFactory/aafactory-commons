package com.xxmassdeveloper.mpchartexample

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.EntryXComparator
import com.xxmassdeveloper.mpchartexample.custom.MyMarkerView
import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase
import io.github.aafactory.sample.R
import kotlinx.android.synthetic.main.content_toolbar.*
import kotlinx.android.synthetic.main.mpandroidchart_activity_linechart.*
import java.util.*

class InvertedLineChartActivity : DemoBase(), OnSeekBarChangeListener, OnChartValueSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.mpandroidchart_activity_linechart)
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
        }

        seekBar1.progress = 45
        seekBar2.progress = 100

        seekBar1.setOnSeekBarChangeListener(this)
        seekBar2.setOnSeekBarChangeListener(this)

        chart1.setOnChartValueSelectedListener(this)
        chart1.setDrawGridBackground(false)

        // no description text
        chart1.description.isEnabled = false

        // enable touch gestures
        chart1.setTouchEnabled(true)

        // enable scaling and dragging
        chart1.isDragEnabled = true
        chart1.setScaleEnabled(true)

        // if disabled, scaling can be done on x- and y-axis separately
        chart1.setPinchZoom(true)

        // set an alternative background color
        // mChart.setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        val mv = MyMarkerView(this, R.layout.mpandroidchart_custom_marker_view)
        mv.chartView = chart1 // For bounds control
        chart1.marker = mv // Set the marker to the chart

        val xl = chart1.xAxis
        xl.setAvoidFirstLastClipping(true)
        xl.axisMinimum = 0f

        val leftAxis = chart1.axisLeft
        leftAxis.isInverted = true
        leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)

        val rightAxis = chart1.axisRight
        rightAxis.isEnabled = false

        // add data
        setData(25, 50f)

        // // restrain the maximum scale-out factor
        // mChart.setScaleMinima(3f, 3f);
        //
        // // center the view to a specific position inside the chart
        // mChart.centerViewPort(10, 50);

        // get the legend (only possible after setting data)
        val l = chart1.legend

        // modify the legend ...
        l.form = LegendForm.LINE

        // dont forget to refresh the drawing
        chart1.invalidate()
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        tvXMax.text = String.format("%d", seekBar1.progress + 1)
        tvYMax.text = "${seekBar2.progress}" 
        setData(seekBar1.progress + 1, seekBar2.progress.toFloat())

        // redraw
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

    private fun setData(count: Int, range: Float) {
        val entries = ArrayList<Entry>()

        for (i in 0 until count) {
            val xVal = (Math.random() * range).toFloat()
            val yVal = (Math.random() * range).toFloat()
            entries.add(Entry(xVal, yVal))
        }

        // sort by x-value
        Collections.sort(entries, EntryXComparator())

        // create a dataset and give it a type
        val set1 = LineDataSet(entries, "DataSet 1")

        set1.lineWidth = 1.5f
        set1.circleRadius = 4f

        // create a data object with the datasets
        val data = LineData(set1)

        // set data
        chart1.data = data
    }
}
