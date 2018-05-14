package com.xxmassdeveloper.mpchartexample

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase
import io.github.aafactory.sample.R
import kotlinx.android.synthetic.main.content_toolbar.*
import kotlinx.android.synthetic.main.mpandroidchart_activity_candlechart.*
import java.util.*

class CandleStickChartActivity : DemoBase(), OnSeekBarChangeListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.mpandroidchart_activity_candlechart)
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
        }

        seekBar1.setOnSeekBarChangeListener(this)
        seekBar2.setOnSeekBarChangeListener(this)
        chart1.setBackgroundColor(Color.WHITE)
        chart1.description.isEnabled = false

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart1.setMaxVisibleValueCount(60)

        // scaling can now only be done on x- and y-axis separately
        chart1.setPinchZoom(false)

        chart1.setDrawGridBackground(false)

        val xAxis = chart1.xAxis
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)

        val leftAxis = chart1.axisLeft
        //        leftAxis.setEnabled(false);
        leftAxis.setLabelCount(7, false)
        leftAxis.setDrawGridLines(false)
        leftAxis.setDrawAxisLine(false)
        
        val rightAxis = chart1.axisRight
        rightAxis.isEnabled = false
        //        rightAxis.setStartAtZero(false);

        // setting data
        seekBar1.progress = 40
        seekBar2.progress = 100

        chart1.legend.isEnabled = false
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {

        val prog = seekBar1.progress + 1

        tvXMax.text = prog.toString()
        tvYMax.text = seekBar2.progress.toString()

        chart1.resetTracking()

        val yVals1 = ArrayList<CandleEntry>()

        for (i in 0 until prog) {
            val mult = (seekBar2.progress + 1).toFloat()
            val `val` = (Math.random() * 40).toFloat() + mult

            val high = (Math.random() * 9).toFloat() + 8f
            val low = (Math.random() * 9).toFloat() + 8f

            val open = (Math.random() * 6).toFloat() + 1f
            val close = (Math.random() * 6).toFloat() + 1f

            val even = i % 2 == 0

            yVals1.add(CandleEntry(
                    i.toFloat(), `val` + high,
                    `val` - low,
                    if (even) `val` + open else `val` - open,
                    if (even) `val` - close else `val` + close,
                    resources.getDrawable(R.drawable.star)
            ))
        }

        val set1 = CandleDataSet(yVals1, "Data Set")

        set1.setDrawIcons(false)
        set1.axisDependency = AxisDependency.LEFT
        //        set1.setColor(Color.rgb(80, 80, 80));
        set1.shadowColor = Color.DKGRAY
        set1.shadowWidth = 0.7f
        set1.decreasingColor = Color.RED
        set1.decreasingPaintStyle = Paint.Style.FILL
        set1.increasingColor = Color.rgb(122, 242, 84)
        set1.increasingPaintStyle = Paint.Style.STROKE
        set1.neutralColor = Color.BLUE
        //set1.setHighlightLineWidth(1f);

        val data = CandleData(set1)

        chart1.data = data
        chart1.invalidate()
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {
        // TODO Auto-generated method stub
    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
        // TODO Auto-generated method stub
    }
}
