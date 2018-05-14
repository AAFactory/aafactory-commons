package com.xxmassdeveloper.mpchartexample

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase
import io.github.aafactory.sample.R
import kotlinx.android.synthetic.main.content_toolbar.*
import kotlinx.android.synthetic.main.mpandroidchart_activity_piechart.*
import java.util.*

class PieChartActivity : DemoBase(), OnSeekBarChangeListener, OnChartValueSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.mpandroidchart_activity_piechart)
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
        }

        seekBar1.progress = 4
        seekBar2.progress = 10

        chart1.setUsePercentValues(true)
        chart1.description.isEnabled = false
        chart1.setExtraOffsets(5f, 10f, 5f, 5f)

        chart1.dragDecelerationFrictionCoef = 0.95f

        chart1.setCenterTextTypeface(mTfLight)
        chart1.centerText = generateCenterSpannableText()

        chart1.isDrawHoleEnabled = true
        chart1.setHoleColor(Color.WHITE)

        chart1.setTransparentCircleColor(Color.WHITE)
        chart1.setTransparentCircleAlpha(110)

        chart1.holeRadius = 58f
        chart1.transparentCircleRadius = 61f

        chart1.setDrawCenterText(true)

        chart1.rotationAngle = 0f
        // enable rotation of the chart by touch
        chart1.isRotationEnabled = true
        chart1.isHighlightPerTapEnabled = true

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        chart1.setOnChartValueSelectedListener(this)

        setData(4, 100f)

        chart1.animateY(1400, Easing.EasingOption.EaseInCubic)
        // mChart.spin(2000, 0, 360);

        seekBar1.setOnSeekBarChangeListener(this)
        seekBar2.setOnSeekBarChangeListener(this)

        val l = chart1.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.xEntrySpace = 7f
        l.yEntrySpace = 0f
        l.yOffset = 0f

        // entry label styling
        chart1.setEntryLabelColor(Color.WHITE)
        chart1.setEntryLabelTypeface(mTfRegular)
        chart1.setEntryLabelTextSize(12f)
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {

        tvXMax.text = "" + seekBar1.progress
        tvYMax.text = "" + seekBar2.progress

        setData(seekBar1.progress, seekBar2.progress.toFloat())
    }

    private fun setData(count: Int, range: Float) {

        val entries = ArrayList<PieEntry>()

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (i in 0 until count) {
            entries.add(PieEntry((Math.random() * range + range / 5).toFloat(),
                    mParties[i % mParties.size], ContextCompat.getDrawable(this, R.drawable.star)))
        }

        val dataSet = PieDataSet(entries, "Election Results")

        dataSet.setDrawIcons(false)

        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        // add a lot of colors

        val colors = ArrayList<Int>()

        for (c in ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c)

        for (c in ColorTemplate.JOYFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.COLORFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.LIBERTY_COLORS)
            colors.add(c)

        for (c in ColorTemplate.PASTEL_COLORS)
            colors.add(c)

        colors.add(ColorTemplate.getHoloBlue())

        dataSet.colors = colors
        //dataSet.setSelectionShift(0f);

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        data.setValueTypeface(mTfLight)
        chart1.data = data

        // undo all highlights
        chart1.highlightValues(null)

        chart1.invalidate()
    }

    private fun generateCenterSpannableText(): SpannableString {
        val s = SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda")
        s.setSpan(RelativeSizeSpan(1.7f), 0, 14, 0)
        s.setSpan(StyleSpan(Typeface.NORMAL), 14, s.length - 15, 0)
        s.setSpan(ForegroundColorSpan(Color.GRAY), 14, s.length - 15, 0)
        s.setSpan(RelativeSizeSpan(.8f), 14, s.length - 15, 0)
        s.setSpan(StyleSpan(Typeface.ITALIC), s.length - 14, s.length, 0)
        s.setSpan(ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length - 14, s.length, 0)
        return s
    }

    override fun onValueSelected(e: Entry?, h: Highlight) {
        if (e == null)
            return
        Log.i("VAL SELECTED",
                "Value: " + e.y + ", index: " + h.x
                        + ", DataSet index: " + h.dataSetIndex)
    }

    override fun onNothingSelected() {
        Log.i("PieChart", "nothing selected")
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {
        // TODO Auto-generated method stub
    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
        // TODO Auto-generated method stub
    }
}
