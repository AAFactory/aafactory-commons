package com.nhn.android.mapviewer

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.nhn.android.maps.NMapActivity
import com.nhn.android.maps.NMapCompassManager
import com.nhn.android.maps.NMapLocationManager
import com.nhn.android.maps.NMapView
import com.nhn.android.mapviewer.overlay.NMapOverlayManager
import io.github.aafactory.sample.R
import kotlinx.android.synthetic.main.nmap_activity_main.*

/**
 * Created by CHO HANJOONG on 2018-05-17.
 */

class NMapViewer : NMapActivity() {
    companion object {
        private val LOG_TAG = "NMapViewer"
        private val CLIENT_ID = "h3mlstTRuPudKfbo4Rge"
        private val USE_XML_LAYOUT = false
    }

    private lateinit var mMapContainerView: MapContainerView
    private lateinit var mMapView: NMapView
    
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (USE_XML_LAYOUT) {
            setContentView(R.layout.nmap_activity_main)

            mMapView = mapView
        } else {
            // create map view
            mMapView = NMapView(this)

            // create parent view to rotate map view
            mMapContainerView = MapContainerView(this)
            mMapContainerView.addView(mMapView)

            // set the activity content to the parent view
            setContentView(mMapContainerView)
        }

        // set a registered Client Id for Open MapViewer Library
        mMapView.setClientId(CLIENT_ID)

        // initialize map view
        mMapView.setClickable(true)
        mMapView.setEnabled(true)
        mMapView.setFocusable(true)
        mMapView.setFocusableInTouchMode(true)
        mMapView.requestFocus()

        // register listener for map state changes
//        mMapView.setOnMapStateChangeListener(onMapViewStateChangeListener)
//        mMapView.setOnMapViewTouchEventListener(onMapViewTouchEventListener)
//        mMapView.setOnMapViewDelegate(onMapViewTouchDelegate)

        // use map controller to zoom in/out, pan and set map center, zoom level etc.
//        mMapController = mMapView.getMapController()

        // use built in zoom controls
        val lp = NMapView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, NMapView.LayoutParams.BOTTOM_RIGHT)
        mMapView.setBuiltInZoomControls(true, lp)

        // create resource provider
//        mMapViewerResourceProvider = NMapViewerResourceProvider(this)

        // set data provider listener
//        super.setMapDataProviderListener(onDataProviderListener)

        // create overlay manager
//        mOverlayManager = NMapOverlayManager(this, mMapView, mMapViewerResourceProvider)
        // register callout overlay listener to customize it.
//        mOverlayManager.setOnCalloutOverlayListener(onCalloutOverlayListener)
        // register callout overlay view listener to customize it.
//        mOverlayManager.setOnCalloutOverlayViewListener(onCalloutOverlayViewListener)

        // location manager
//        mMapLocationManager = NMapLocationManager(this)
//        mMapLocationManager.setOnLocationChangeListener(onMyLocationChangeListener)

        // compass manager
//        mMapCompassManager = NMapCompassManager(this)

        // create my location overlay
//        mMyLocationOverlay = mOverlayManager.createMyLocationOverlay(mMapLocationManager, mMapCompassManager)

        mMapView.setScalingFactor(2.0F, true)
    }

    private inner class MapContainerView(context: Context) : ViewGroup(context) {
        override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
            val width = width
            val height = height
            val count = childCount
            for (i in 0 until count) {
                val view = getChildAt(i)
                val childWidth = view.measuredWidth
                val childHeight = view.measuredHeight
                val childLeft = (width - childWidth) / 2
                val childTop = (height - childHeight) / 2
                view.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight)
            }

//            if (changed) {
//                mOverlayManager.onSizeChanged(width, height)
//            }
        }

        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            val w = View.getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
            val h = View.getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
            var sizeSpecWidth = widthMeasureSpec
            var sizeSpecHeight = heightMeasureSpec

            val count = childCount
            for (i in 0 until count) {
                val view = getChildAt(i)

                if (view is NMapView) {
                    if (mMapView.isAutoRotateEnabled) {
                        val diag = (Math.sqrt((w * w + h * h).toDouble()).toInt() + 1) / 2 * 2
                        sizeSpecWidth = View.MeasureSpec.makeMeasureSpec(diag, View.MeasureSpec.EXACTLY)
                        sizeSpecHeight = sizeSpecWidth
                    }
                }

                view.measure(sizeSpecWidth, sizeSpecHeight)
            }
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }
}