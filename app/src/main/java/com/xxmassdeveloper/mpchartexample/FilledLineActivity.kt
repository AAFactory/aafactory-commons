package com.xxmassdeveloper.mpchartexample

import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase
import io.github.aafactory.sample.R
import kotlinx.android.synthetic.main.content_toolbar.*
import kotlinx.android.synthetic.main.mpandroidchart_activity_linechart_noseekbar.*
import java.util.*

class FilledLineActivity : DemoBase() {
    private val mFillColor = Color.argb(150, 51, 181, 229)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.mpandroidchart_activity_linechart_noseekbar)
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
        }

        chart1.setBackgroundColor(Color.WHITE)
        chart1.setGridBackgroundColor(mFillColor)
        chart1.setDrawGridBackground(true)
        chart1.setDrawBorders(true)

        // no description text
        chart1.description.isEnabled = false

        // if disabled, scaling can be done on x- and y-axis separately
        chart1.setPinchZoom(false)

        val l = chart1.legend
        l.isEnabled = false

        val xAxis = chart1.xAxis
        xAxis.isEnabled = false

        val leftAxis = chart1.axisLeft
        leftAxis.axisMaximum = 900f
        leftAxis.axisMinimum = -250f
        leftAxis.setDrawAxisLine(false)
        leftAxis.setDrawZeroLine(false)
        leftAxis.setDrawGridLines(false)

        chart1.axisRight.isEnabled = false

        // add data
        setData(100, 60f)

        chart1.invalidate()
    }

    private fun setData(count: Int, range: Float) {

        val yVals1 = ArrayList<Entry>()

        for (i in 0 until count) {
            val `val` = (Math.random() * range).toFloat() + 50// + (float)
            // ((mult *
            // 0.1) / 10);
            yVals1.add(Entry(i.toFloat(), `val`))
        }

        val yVals2 = ArrayList<Entry>()

        for (i in 0 until count) {
            val `val` = (Math.random() * range).toFloat() + 450// + (float)
            // ((mult *
            // 0.1) / 10);
            yVals2.add(Entry(i.toFloat(), `val`))
        }

        val set1: LineDataSet
        val set2: LineDataSet

        if (chart1.data != null && chart1.data.dataSetCount > 0) {
            set1 = chart1.data.getDataSetByIndex(0) as LineDataSet
            set2 = chart1.data.getDataSetByIndex(1) as LineDataSet
            set1.values = yVals1
            set2.values = yVals2
            chart1.data.notifyDataChanged()
            chart1.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(yVals1, "DataSet 1")

            set1.axisDependency = YAxis.AxisDependency.LEFT
            set1.color = Color.rgb(255, 241, 46)
            set1.setDrawCircles(false)
            set1.lineWidth = 2f
            set1.circleRadius = 3f
            set1.fillAlpha = 255
            set1.setDrawFilled(true)
            set1.fillColor = Color.WHITE
            set1.highLightColor = Color.rgb(244, 117, 117)
            set1.setDrawCircleHole(false)
            set1.fillFormatter = IFillFormatter { dataSet, dataProvider -> chart1.axisLeft.axisMinimum }

            // create a dataset and give it a type
            set2 = LineDataSet(yVals2, "DataSet 2")
            set2.axisDependency = YAxis.AxisDependency.LEFT
            set2.color = Color.rgb(255, 241, 46)
            set2.setDrawCircles(false)
            set2.lineWidth = 2f
            set2.circleRadius = 3f
            set2.fillAlpha = 255
            set2.setDrawFilled(true)
            set2.fillColor = Color.WHITE
            set2.setDrawCircleHole(false)
            set2.highLightColor = Color.rgb(244, 117, 117)
            set2.fillFormatter = IFillFormatter { dataSet, dataProvider -> chart1.axisLeft.axisMaximum }

            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set1) // add the datasets
            dataSets.add(set2)

            // create a data object with the datasets
            val data = LineData(dataSets)
            data.setDrawValues(false)

            // set data
            chart1.data = data
        }
    }
}
