package com.xxmassdeveloper.mpchartexample

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ViewPortHandler
import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase
import io.github.aafactory.sample.R
import kotlinx.android.synthetic.main.content_toolbar.*
import kotlinx.android.synthetic.main.mpandroidchart_activity_age_distribution.*
import java.text.DecimalFormat
import java.util.*

class StackedBarActivityNegative : DemoBase(), OnChartValueSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.mpandroidchart_activity_age_distribution)
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
        }
        //        setTitle("Age Distribution Austria");

        chart1.setOnChartValueSelectedListener(this)
        chart1.setDrawGridBackground(false)
        chart1.description.isEnabled = false

        // scaling can now only be done on x- and y-axis separately
        chart1.setPinchZoom(false)

        chart1.setDrawBarShadow(false)
        chart1.setDrawValueAboveBar(true)
        chart1.isHighlightFullBarEnabled = false

        chart1.axisLeft.isEnabled = false
        chart1.axisRight.axisMaximum = 25f
        chart1.axisRight.axisMinimum = -25f
        chart1.axisRight.setDrawGridLines(false)
        chart1.axisRight.setDrawZeroLine(true)
        chart1.axisRight.setLabelCount(7, false)
        chart1.axisRight.valueFormatter = CustomFormatter()
        chart1.axisRight.textSize = 9f

        val xAxis = chart1.xAxis
        xAxis.position = XAxisPosition.BOTH_SIDED
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)
        xAxis.textSize = 9f
        xAxis.axisMinimum = 0f
        xAxis.axisMaximum = 110f
        xAxis.setCenterAxisLabels(true)
        xAxis.labelCount = 12
        xAxis.granularity = 10f
        xAxis.valueFormatter = object : IAxisValueFormatter {

            private val format = DecimalFormat("###")

            override fun getFormattedValue(value: Float, axis: AxisBase): String {
                return format.format(value.toDouble()) + "-" + format.format((value + 10).toDouble())
            }
        }

        val l = chart1.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        l.formSize = 8f
        l.formToTextSpace = 4f
        l.xEntrySpace = 6f

        // IMPORTANT: When using negative values in stacked bars, always make sure the negative values are in the array first
        val yValues = ArrayList<BarEntry>()
        yValues.add(BarEntry(5f, floatArrayOf(-10f, 10f)))
        yValues.add(BarEntry(15f, floatArrayOf(-12f, 13f)))
        yValues.add(BarEntry(25f, floatArrayOf(-15f, 15f)))
        yValues.add(BarEntry(35f, floatArrayOf(-17f, 17f)))
        yValues.add(BarEntry(45f, floatArrayOf(-19f, 20f)))
        yValues.add(BarEntry(45f, floatArrayOf(-19f, 20f), resources.getDrawable(R.drawable.star)))
        yValues.add(BarEntry(55f, floatArrayOf(-19f, 19f)))
        yValues.add(BarEntry(65f, floatArrayOf(-16f, 16f)))
        yValues.add(BarEntry(75f, floatArrayOf(-13f, 14f)))
        yValues.add(BarEntry(85f, floatArrayOf(-10f, 11f)))
        yValues.add(BarEntry(95f, floatArrayOf(-5f, 6f)))
        yValues.add(BarEntry(105f, floatArrayOf(-1f, 2f)))

        val set = BarDataSet(yValues, "Age Distribution")
        set.setDrawIcons(false)
        set.valueFormatter = CustomFormatter()
        set.valueTextSize = 7f
        set.axisDependency = YAxis.AxisDependency.RIGHT
        set.setColors(*intArrayOf(Color.rgb(67, 67, 72), Color.rgb(124, 181, 236)))
        set.stackLabels = arrayOf("Men", "Women")

        val xLabels = arrayOf("0-10", "10-20", "20-30", "30-40", "40-50", "50-60", "60-70", "70-80", "80-90", "90-100", "100+")

        val data = BarData(set)
        data.barWidth = 8.5f
        chart1.data = data
        chart1.invalidate()
    }

    override fun onValueSelected(e: Entry, h: Highlight) {
        val entry = e as BarEntry
        Log.i("VAL SELECTED",
                "Value: " + Math.abs(entry.yVals[h.stackIndex]))
    }

    override fun onNothingSelected() {
        // TODO Auto-generated method stub
        Log.i("NOTING SELECTED", "")
    }

    private inner class CustomFormatter : IValueFormatter, IAxisValueFormatter {
        private val mFormat: DecimalFormat

        init {
            mFormat = DecimalFormat("###")
        }

        // data
        override fun getFormattedValue(value: Float, entry: Entry, dataSetIndex: Int, viewPortHandler: ViewPortHandler): String {
            return mFormat.format(Math.abs(value).toDouble()) + "m"
        }

        // YAxis
        override fun getFormattedValue(value: Float, axis: AxisBase): String {
            return mFormat.format(Math.abs(value).toDouble()) + "m"
        }
    }
}
