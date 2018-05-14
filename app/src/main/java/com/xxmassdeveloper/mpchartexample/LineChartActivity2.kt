package com.xxmassdeveloper.mpchartexample

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase
import io.github.aafactory.sample.R
import kotlinx.android.synthetic.main.content_toolbar.*
import kotlinx.android.synthetic.main.mpandroidchart_activity_linechart.*
import java.util.*

class LineChartActivity2 : DemoBase(), OnSeekBarChangeListener, OnChartValueSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.mpandroidchart_activity_linechart)
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
        }

        seekBar1.progress = 45
        seekBar2.progress = 100

        seekBar1.setOnSeekBarChangeListener(this)
        seekBar2.setOnSeekBarChangeListener(this)

//        mChart = findViewById<View>(R.id.chart1)
        chart1.setOnChartValueSelectedListener(this)

        // no description text
        chart1.description.isEnabled = false

        // enable touch gestures
        chart1.setTouchEnabled(true)

        chart1.dragDecelerationFrictionCoef = 0.9f

        // enable scaling and dragging
        chart1.isDragEnabled = true
        chart1.setScaleEnabled(true)
        chart1.setDrawGridBackground(false)
        chart1.isHighlightPerDragEnabled = true

        // if disabled, scaling can be done on x- and y-axis separately
        chart1.setPinchZoom(true)

        // set an alternative background color
        chart1.setBackgroundColor(Color.LTGRAY)

        // add data
        setData(20, 30f)

        chart1.animateX(2500)

        // get the legend (only possible after setting data)
        val l = chart1.legend

        // modify the legend ...
        l.form = LegendForm.LINE
        l.typeface = mTfLight
        l.textSize = 11f
        l.textColor = Color.WHITE
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        //        l.setYOffset(11f);

        val xAxis = chart1.xAxis
        xAxis.typeface = mTfLight
        xAxis.textSize = 11f
        xAxis.textColor = Color.WHITE
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        val leftAxis = chart1.axisLeft
        leftAxis.typeface = mTfLight
        leftAxis.textColor = ColorTemplate.getHoloBlue()
        leftAxis.axisMaximum = 200f
        leftAxis.axisMinimum = 0f
        leftAxis.setDrawGridLines(true)
        leftAxis.isGranularityEnabled = true

        val rightAxis = chart1.axisRight
        rightAxis.typeface = mTfLight
        rightAxis.textColor = Color.RED
        rightAxis.axisMaximum = 900f
        rightAxis.axisMinimum = -200f
        rightAxis.setDrawGridLines(false)
        rightAxis.setDrawZeroLine(false)
        rightAxis.isGranularityEnabled = false
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        tvXMax.text =  String.format("%d", seekBar1.progress + 1)
        tvYMax.text = "${seekBar2.progress}"

        setData(seekBar1.progress + 1, seekBar2.progress.toFloat())

        // redraw
        chart1.invalidate()
    }

    private fun setData(count: Int, range: Float) {

        val yVals1 = ArrayList<Entry>()

        for (i in 0 until count) {
            val mult = range / 2f
            val `val` = (Math.random() * mult).toFloat() + 50
            yVals1.add(Entry(i.toFloat(), `val`))
        }

        val yVals2 = ArrayList<Entry>()

        for (i in 0 until count - 1) {
            val `val` = (Math.random() * range).toFloat() + 450
            yVals2.add(Entry(i.toFloat(), `val`))
            //            if(i == 10) {
            //                yVals2.add(new Entry(i, val + 50));
            //            }
        }

        val yVals3 = ArrayList<Entry>()

        for (i in 0 until count) {
            val `val` = (Math.random() * range).toFloat() + 500
            yVals3.add(Entry(i.toFloat(), `val`))
        }

        val set1: LineDataSet
        val set2: LineDataSet
        val set3: LineDataSet

        if (chart1.data != null && chart1.data.dataSetCount > 0) {
            set1 = chart1.data.getDataSetByIndex(0) as LineDataSet
            set2 = chart1.data.getDataSetByIndex(1) as LineDataSet
            set3 = chart1.data.getDataSetByIndex(2) as LineDataSet
            set1.values = yVals1
            set2.values = yVals2
            set3.values = yVals3
            chart1.data.notifyDataChanged()
            chart1.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(yVals1, "DataSet 1")

            set1.axisDependency = AxisDependency.LEFT
            set1.color = ColorTemplate.getHoloBlue()
            set1.setCircleColor(Color.WHITE)
            set1.lineWidth = 2f
            set1.circleRadius = 3f
            set1.fillAlpha = 65
            set1.fillColor = ColorTemplate.getHoloBlue()
            set1.highLightColor = Color.rgb(244, 117, 117)
            set1.setDrawCircleHole(false)
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);

            // create a dataset and give it a type
            set2 = LineDataSet(yVals2, "DataSet 2")
            set2.axisDependency = AxisDependency.RIGHT
            set2.color = Color.RED
            set2.setCircleColor(Color.WHITE)
            set2.lineWidth = 2f
            set2.circleRadius = 3f
            set2.fillAlpha = 65
            set2.fillColor = Color.RED
            set2.setDrawCircleHole(false)
            set2.highLightColor = Color.rgb(244, 117, 117)
            //set2.setFillFormatter(new MyFillFormatter(900f));

            set3 = LineDataSet(yVals3, "DataSet 3")
            set3.axisDependency = AxisDependency.RIGHT
            set3.color = Color.YELLOW
            set3.setCircleColor(Color.WHITE)
            set3.lineWidth = 2f
            set3.circleRadius = 3f
            set3.fillAlpha = 65
            set3.fillColor = ColorTemplate.colorWithAlpha(Color.YELLOW, 200)
            set3.setDrawCircleHole(false)
            set3.highLightColor = Color.rgb(244, 117, 117)

            // create a data object with the datasets
            val data = LineData(set1, set2, set3)
            data.setValueTextColor(Color.WHITE)
            data.setValueTextSize(9f)

            // set data
            chart1.data = data
        }
    }

    override fun onValueSelected(e: Entry, h: Highlight) {
        Log.i("Entry selected", e.toString())

        chart1.centerViewToAnimated(e.x, e.y, chart1.data.getDataSetByIndex(h.dataSetIndex)
                .axisDependency, 500)
        //mChart.zoomAndCenterAnimated(2.5f, 2.5f, e.getX(), e.getY(), mChart.getData().getDataSetByIndex(dataSetIndex)
        // .getAxisDependency(), 1000);
        //mChart.zoomAndCenterAnimated(1.8f, 1.8f, e.getX(), e.getY(), mChart.getData().getDataSetByIndex(dataSetIndex)
        // .getAxisDependency(), 1000);
    }

    override fun onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.")
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {
        // TODO Auto-generated method stub
    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
        // TODO Auto-generated method stub
    }
}
