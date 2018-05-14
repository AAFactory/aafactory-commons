package com.xxmassdeveloper.mpchartexample

import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.ChartTouchListener
import com.github.mikephil.charting.listener.OnChartGestureListener
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.Utils
import com.xxmassdeveloper.mpchartexample.custom.MyMarkerView
import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase
import io.github.aafactory.sample.R
import kotlinx.android.synthetic.main.content_toolbar.*
import kotlinx.android.synthetic.main.mpandroidchart_activity_linechart.*
import java.util.*

class LineChartActivity1 : DemoBase(), OnSeekBarChangeListener, OnChartGestureListener, OnChartValueSelectedListener {
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

        chart1.onChartGestureListener = this
        chart1.setOnChartValueSelectedListener(this)
        chart1.setDrawGridBackground(false)

        // no description text
        chart1.description.isEnabled = false

        // enable touch gestures
        chart1.setTouchEnabled(true)

        // enable scaling and dragging
        chart1.isDragEnabled = true
        chart1.setScaleEnabled(true)
        // chart1.setScaleXEnabled(true);
        // chart1.setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart1.setPinchZoom(true)

        // set an alternative background color
        // chart1.setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        val mv = MyMarkerView(this, R.layout.mpandroidchart_custom_marker_view)
        mv.chartView = chart1 // For bounds control
        chart1.marker = mv // Set the marker to the chart

        // x-axis limit line
        val llXAxis = LimitLine(10f, "Index 10")
        llXAxis.lineWidth = 4f
        llXAxis.enableDashedLine(10f, 10f, 0f)
        llXAxis.labelPosition = LimitLabelPosition.RIGHT_BOTTOM
        llXAxis.textSize = 10f

        val xAxis = chart1.xAxis
        xAxis.enableGridDashedLine(10f, 10f, 0f)
        //xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());
        //xAxis.addLimitLine(llXAxis); // add x-axis limit line

        val tf = Typeface.createFromAsset(assets, "OpenSans-Regular.ttf")

        val ll1 = LimitLine(150f, "Upper Limit")
        ll1.lineWidth = 4f
        ll1.enableDashedLine(10f, 10f, 0f)
        ll1.labelPosition = LimitLabelPosition.RIGHT_TOP
        ll1.textSize = 10f
        ll1.typeface = tf

        val ll2 = LimitLine(-30f, "Lower Limit")
        ll2.lineWidth = 4f
        ll2.enableDashedLine(10f, 10f, 0f)
        ll2.labelPosition = LimitLabelPosition.RIGHT_BOTTOM
        ll2.textSize = 10f
        ll2.typeface = tf

        val leftAxis = chart1.axisLeft
        leftAxis.removeAllLimitLines() // reset all limit lines to avoid overlapping lines
        leftAxis.addLimitLine(ll1)
        leftAxis.addLimitLine(ll2)
        leftAxis.axisMaximum = 200f
        leftAxis.axisMinimum = -50f
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f)
        leftAxis.setDrawZeroLine(false)

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true)

        chart1.axisRight.isEnabled = false

        //chart1.getViewPortHandler().setMaximumScaleY(2f);
        //chart1.getViewPortHandler().setMaximumScaleX(2f);

        // add data
        setData(45, 100f)

        //        chart1.setVisibleXRange(20);
        //        chart1.setVisibleYRange(20f, AxisDependency.LEFT);
        //        chart1.centerViewTo(20, 50, AxisDependency.LEFT);

        chart1.animateX(2500)
        //chart1.invalidate();

        // get the legend (only possible after setting data)
        val l = chart1.legend

        // modify the legend ...
        l.form = LegendForm.LINE

        // // dont forget to refresh the drawing
        // chart1.invalidate();
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        tvXMax.text = "" + (seekBar1.progress + 1)
        tvYMax.text = "" + seekBar2.progress

        setData(seekBar1.progress + 1, seekBar2.progress.toFloat())

        // redraw
        chart1.invalidate()
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {
        // TODO Auto-generated method stub
    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
        // TODO Auto-generated method stub
    }

    private fun setData(count: Int, range: Float) {

        val values = ArrayList<Entry>()

        for (i in 0 until count) {

            val `val` = (Math.random() * range).toFloat() + 3
            values.add(Entry(i.toFloat(), `val`, resources.getDrawable(R.drawable.star)))
        }

        val set1: LineDataSet

        if (chart1.data != null && chart1.data.dataSetCount > 0) {
            set1 = chart1.data.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            chart1.data.notifyDataChanged()
            chart1.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(values, "DataSet 1")

            set1.setDrawIcons(false)

            // set the line to be drawn like this "- - - - - -"
            set1.enableDashedLine(10f, 5f, 0f)
            set1.enableDashedHighlightLine(10f, 5f, 0f)
            set1.color = Color.BLACK
            set1.setCircleColor(Color.BLACK)
            set1.lineWidth = 1f
            set1.circleRadius = 3f
            set1.setDrawCircleHole(false)
            set1.valueTextSize = 9f
            set1.setDrawFilled(true)
            set1.formLineWidth = 1f
            set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            set1.formSize = 15f

            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                val drawable = ContextCompat.getDrawable(this, R.drawable.fade_red)
                set1.fillDrawable = drawable
            } else {
                set1.fillColor = Color.BLACK
            }

            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set1) // add the datasets

            // create a data object with the datasets
            val data = LineData(dataSets)

            // set data
            chart1.data = data
        }
    }

    override fun onChartGestureStart(me: MotionEvent, lastPerformedGesture: ChartTouchListener.ChartGesture) {
        Log.i("Gesture", "START, x: " + me.x + ", y: " + me.y)
    }

    override fun onChartGestureEnd(me: MotionEvent, lastPerformedGesture: ChartTouchListener.ChartGesture) {
        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture)

        // un-highlight values after the gesture is finished and no single-tap
        if (lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            chart1.highlightValues(null) // or highlightTouch(null) for callback to onNothingSelected(...)
    }

    override fun onChartLongPressed(me: MotionEvent) {
        Log.i("LongPress", "Chart longpressed.")
    }

    override fun onChartDoubleTapped(me: MotionEvent) {
        Log.i("DoubleTap", "Chart double-tapped.")
    }

    override fun onChartSingleTapped(me: MotionEvent) {
        Log.i("SingleTap", "Chart single-tapped.")
    }

    override fun onChartFling(me1: MotionEvent, me2: MotionEvent, velocityX: Float, velocityY: Float) {
        Log.i("Fling", "Chart flinged. VeloX: $velocityX, VeloY: $velocityY")
    }

    override fun onChartScale(me: MotionEvent, scaleX: Float, scaleY: Float) {
        Log.i("Scale / Zoom", "ScaleX: $scaleX, ScaleY: $scaleY")
    }

    override fun onChartTranslate(me: MotionEvent, dX: Float, dY: Float) {
        Log.i("Translate / Move", "dX: $dX, dY: $dY")
    }

    override fun onValueSelected(e: Entry, h: Highlight) {
        Log.i("Entry selected", e.toString())
        Log.i("LOWHIGH", "low: " + chart1.lowestVisibleX + ", high: " + chart1.highestVisibleX)
        Log.i("MIN MAX", "xmin: " + chart1.xChartMin + ", xmax: " + chart1.xChartMax + ", ymin: " + chart1.yChartMin + ", ymax: " + chart1.yChartMax)
    }

    override fun onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.")
    }
}
