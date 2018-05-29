package com.nhn.android.mapviewer

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Rect
import android.os.Bundle
import android.provider.Settings
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.nhn.android.maps.*
import com.nhn.android.maps.NMapActivity.OnDataProviderListener
import com.nhn.android.maps.maplib.NGeoPoint
import com.nhn.android.maps.nmapmodel.NMapError
import com.nhn.android.maps.overlay.*
import com.nhn.android.mapviewer.overlay.*
import io.github.aafactory.commons.utils.LocationUtils.Companion.getLocationWithGPSProvider
import io.github.aafactory.sample.R
import kotlinx.android.synthetic.main.nmap_activity_main.*

/**
 * Created by CHO HANJOONG on 2018-05-17.
 */

class NMapViewer : NMapActivity() {
    companion object {
        private const val LOG_TAG = "NMapViewer"
        private const val DEBUG = true
        private const val CLIENT_ID = "h3mlstTRuPudKfbo4Rge"
        private const val USE_XML_LAYOUT = true


        private val NMAP_LOCATION_DEFAULT = NGeoPoint(126.978371, 37.5666091)
        private val NMAP_ZOOMLEVEL_DEFAULT = 11
        private val NMAP_VIEW_MODE_DEFAULT = NMapView.VIEW_MODE_VECTOR
        private val NMAP_TRAFFIC_MODE_DEFAULT = false
        private val NMAP_BICYCLE_MODE_DEFAULT = false

        private val KEY_ZOOM_LEVEL = "NMapViewer.zoomLevel"
        private val KEY_CENTER_LONGITUDE = "NMapViewer.centerLongitudeE6"
        private val KEY_CENTER_LATITUDE = "NMapViewer.centerLatitudeE6"
        private val KEY_VIEW_MODE = "NMapViewer.viewMode"
        private val KEY_TRAFFIC_MODE = "NMapViewer.trafficMode"
        private val KEY_BICYCLE_MODE = "NMapViewer.bicycleMode"
    }

    private lateinit var mMapContainerView: MapContainerView
    private lateinit var mMapView: NMapView
    private lateinit var mMapLocationManager: NMapLocationManager
    private lateinit var mMapController: NMapController
    private lateinit var mOverlayManager: NMapOverlayManager
    private lateinit var mMapViewerResourceProvider: NMapViewerResourceProvider
    private lateinit var mMyLocationOverlay: NMapMyLocationOverlay
    private lateinit var mMapCompassManager: NMapCompassManager

    private var mPreferences: SharedPreferences? = null

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
        mMapView.isClickable = true
        mMapView.isEnabled = true
        mMapView.isFocusable = true
        mMapView.isFocusableInTouchMode = true
        mMapView.requestFocus()

        // register listener for map state changes
        mMapView.setOnMapStateChangeListener(onMapViewStateChangeListener)
        mMapView.setOnMapViewTouchEventListener(onMapViewTouchEventListener)
        mMapView.setOnMapViewDelegate(onMapViewTouchDelegate)

        // use map controller to zoom in/out, pan and set map center, zoom level etc.
        mMapController = mMapView.mapController

        // use built in zoom controls
        val lp = NMapView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, NMapView.LayoutParams.BOTTOM_RIGHT)
//        mMapView.setBuiltInZoomControls(true, lp)

        // create resource provider
        mMapViewerResourceProvider = NMapViewerResourceProvider(this)

        // set data provider listener
        super.setMapDataProviderListener(onDataProviderListener)

        // create overlay manager
        mOverlayManager = NMapOverlayManager(this, mMapView, mMapViewerResourceProvider)
        // register callout overlay listener to customize it.
        mOverlayManager.setOnCalloutOverlayViewListener(onCalloutOverlayListener)
        // register callout overlay view listener to customize it.
//        mOverlayManager.setOnCalloutOverlayViewListener(onCalloutOverlayViewListener)

        // location manager
        mMapLocationManager = NMapLocationManager(this)
//        mMapLocationManager.setOnLocationChangeListener(onMyLocationChangeListener)

        // compass manager
        mMapCompassManager = NMapCompassManager(this)

        // create my location overlay
        mMyLocationOverlay = mOverlayManager.createMyLocationOverlay(mMapLocationManager, mMapCompassManager)

        zoomIn.setOnClickListener { _ -> mMapController.zoomIn() }
        zoomOut.setOnClickListener { _ -> mMapController.zoomOut() }
        openDialog.setOnClickListener { _ ->
            val inflater = getSystemService(AppCompatActivity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.nmap_dialog_simple, null)
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Naver Map for Android")
            dialog.setPositiveButton("OK", null)
            dialog.setView(view)
            alertDialog = dialog.create().apply { show() }
            view.findViewById<TextView>(R.id.action_path_data).setOnClickListener(itemClickListener)
            view.findViewById<TextView>(R.id.action_poi_data).setOnClickListener(itemClickListener)
            view.findViewById<TextView>(R.id.action_scale_factor).setOnClickListener(itemClickListener)
            view.findViewById<TextView>(R.id.action_my_location).setOnClickListener(itemClickListener)
        }
    }

    private val onCalloutOverlayListener = NMapOverlayManager.OnCalloutOverlayViewListener { itemOverlay, overlayItem, itemBounds ->
//        itemBounds.set(-45, -130, -46, 1)
        
        // handle overlapped items
        if (itemOverlay is NMapPOIdataOverlay) {

            // check if it is selected by touch event
            if (!itemOverlay.isFocusedBySelectItem) {
                var countOfOverlappedItems = 1

                val poiData = itemOverlay.poIdata
                for (i in 0 until poiData.count()) {
                    val poiItem = poiData.getPOIitem(i)

                    // skip selected item
                    if (poiItem === overlayItem) {
                        continue
                    }

                    // check if overlapped or not
                    if (Rect.intersects(poiItem.boundsInScreen, overlayItem.boundsInScreen)) {
                        countOfOverlappedItems++
                    }
                }

                if (countOfOverlappedItems > 1) {
                    val text = countOfOverlappedItems.toString() + " overlapped items for " + overlayItem.title
                    Toast.makeText(this@NMapViewer, text, Toast.LENGTH_LONG).show()
                    return@OnCalloutOverlayViewListener null
                }
            }
        }

        // use custom old callout overlay
        if (overlayItem is NMapPOIitem) {

//            if (overlayItem.showRightButton()) {
//                return@OnCalloutOverlayViewListener NMapCalloutCustomOldOverlay(itemOverlay, overlayItem, itemBounds,
//                        mMapViewerResourceProvider)
//            }
        }

        // use custom callout overlay
//        NMapCalloutCustomOverlay(itemOverlay, overlayItem, itemBounds, mMapViewerResourceProvider)
//        NMapCalloutCustomOldOverlay(itemOverlay, overlayItem, itemBounds, mMapViewerResourceProvider)
        // Context context, NMapOverlay itemOverlay, NMapOverlayItem item, Rect itemBounds
        NMapCalloutCustomOverlayView(this@NMapViewer, itemOverlay, overlayItem, itemBounds)
        // set basic callout overlay
//        return new NMapCalloutBasicOverlay(itemOverlay, overlayItem, itemBounds);			
    }
    
    private var alertDialog: AlertDialog? = null
    private val itemClickListener = View.OnClickListener { view ->
        alertDialog?.cancel()
        mOverlayManager.clearOverlays()

        when (view.id) {
            R.id.action_path_data -> {
//                DialogUtil.showTips(this@NMapViewer, "Hello", "Description")
                // add path data overlay
                testPathDataOverlay()
            }
            R.id.action_poi_data -> {
                testPOIdataOverlay()
            }
            R.id.action_scale_factor -> {
                if (mMapView.mapProjection.isProjectionScaled) {
                    if (mMapView.mapProjection.isMapHD) {
                        mMapView.setScalingFactor(2.0f, false)
                    } else {
                        mMapView.setScalingFactor(1.0f, false)
                    }
                } else {
                    mMapView.setScalingFactor(2.0f, true)
                }
                mIsMapEnlared = mMapView.mapProjection.isProjectionScaled
            }
            R.id.action_my_location -> {
//                startMyLocation()
                moveCurrentLocation()
            }
        }
    }

    private fun moveCurrentLocation() {
        getLocationWithGPSProvider()?.let {
            val nGeoPoint = NGeoPoint(it.longitude, it.latitude)
//            mMapController.setMapCenter(nGeoPoint, mMapController.zoomLevel)

            // set POI data
            val poiData = NMapPOIdata(1, mMapViewerResourceProvider)
            poiData.beginPOIdata(1)
            
            val item = poiData.addPOIitem(nGeoPoint, nGeoPoint.toString(), /*NMapPOIflagType.PIN*/ContextCompat.getDrawable(this@NMapViewer, R.drawable.user_avatar), 0)
//            item.setRightAccessory(true, NMapPOIflagType.CLICKABLE_ARROW)
            poiData.endPOIdata()

            // create POI data overlay
            val poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null)

            // set event listener to the overlay
//            poiDataOverlay.onStateChangeListener = onPOIdataStateChangeListener

            // select an item
//            poiDataOverlay.selectPOIitem(0, true)
            mOverlayManager
            // show all POI data
            poiDataOverlay.showAllPOIdata(0);
        }
    }

    private var mIsMapEnlared = false
    private fun restoreInstanceState() {
        mPreferences = getPreferences(Context.MODE_PRIVATE)
        mPreferences?.let {
            val longitudeE6 = it.getInt(KEY_CENTER_LONGITUDE, NMAP_LOCATION_DEFAULT.getLongitudeE6())
            val latitudeE6 = it.getInt(KEY_CENTER_LATITUDE, NMAP_LOCATION_DEFAULT.getLatitudeE6())
            val level = it.getInt(KEY_ZOOM_LEVEL, NMAP_ZOOMLEVEL_DEFAULT)
            val viewMode = it.getInt(KEY_VIEW_MODE, NMAP_VIEW_MODE_DEFAULT)
            val trafficMode = it.getBoolean(KEY_TRAFFIC_MODE, NMAP_TRAFFIC_MODE_DEFAULT)
            val bicycleMode = it.getBoolean(KEY_BICYCLE_MODE, NMAP_BICYCLE_MODE_DEFAULT)

            mMapController.setMapViewMode(viewMode)
            mMapController.setMapViewTrafficMode(trafficMode)
            mMapController.setMapViewBicycleMode(bicycleMode)
            mMapController.setMapCenter(NGeoPoint(longitudeE6, latitudeE6), level)

            if (mIsMapEnlared) {
                mMapView.setScalingFactor(2.0f)
            } else {
                mMapView.setScalingFactor(1.0f)
            }
        }
    }

    private fun startMyLocation() {
        if (mMyLocationOverlay != null) {
            if (!mOverlayManager.hasOverlay(mMyLocationOverlay)) {
                mOverlayManager.addOverlay(mMyLocationOverlay)
            }

            if (mMapLocationManager.isMyLocationEnabled) {

                if (!mMapView.isAutoRotateEnabled) {
                    mMyLocationOverlay.setCompassHeadingVisible(true)

                    mMapCompassManager.enableCompass()

                    mMapView.setAutoRotateEnabled(true, false)

//                    mMapContainerView.requestLayout()
                } else {
                    stopMyLocation()
                }

                mMapView.postInvalidate()
            } else {
                val isMyLocationEnabled = mMapLocationManager.enableMyLocation(true)
                if (!isMyLocationEnabled) {
                    Toast.makeText(this@NMapViewer, "Please enable a My Location source in system settings",
                            Toast.LENGTH_LONG).show()

                    val goToSettings = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(goToSettings)

                    return
                }
            }
        }
    }

    private fun stopMyLocation() {
        if (mMyLocationOverlay != null) {
            mMapLocationManager.disableMyLocation()

            if (mMapView.isAutoRotateEnabled) {
                mMyLocationOverlay.isCompassHeadingVisible = false

                mMapCompassManager.disableCompass()

                mMapView.setAutoRotateEnabled(false, false)

//                mMapContainerView.requestLayout()
            }
        }
    }

    private fun testPathDataOverlay() {
        // set path data points
        val pathData = NMapPathData(9)

        pathData.initPathData()
        pathData.addPathPoint(127.108099, 37.366034, NMapPathLineStyle.TYPE_SOLID)
        pathData.addPathPoint(127.108088, 37.366043, 0)
        pathData.addPathPoint(127.108079, 37.365619, 0)
        pathData.addPathPoint(127.107458, 37.365608, 0)
        pathData.addPathPoint(127.107232, 37.365608, 0)
        pathData.addPathPoint(127.106904, 37.365624, 0)
        pathData.addPathPoint(127.105933, 37.365621, NMapPathLineStyle.TYPE_DASH)
        pathData.addPathPoint(127.105929, 37.366378, 0)
        pathData.addPathPoint(127.106279, 37.366380, 0)
        pathData.endPathData()

        val pathDataOverlay = mOverlayManager.createPathDataOverlay(pathData)
        if (pathDataOverlay != null) {

            // add path data with polygon type
            val pathData2 = NMapPathData(4)
            pathData2.initPathData()
            pathData2.addPathPoint(127.106, 37.367, NMapPathLineStyle.TYPE_SOLID)
            pathData2.addPathPoint(127.107, 37.367, 0)
            pathData2.addPathPoint(127.107, 37.368, 0)
            pathData2.addPathPoint(127.106, 37.368, 0)
            pathData2.endPathData()
            pathDataOverlay.addPathData(pathData2)
            // set path line style
            val pathLineStyle = NMapPathLineStyle(mMapView.context)
            pathLineStyle.pataDataType = NMapPathLineStyle.DATA_TYPE_POLYGON
            pathLineStyle.setLineColor(0xA04DD2, 0xff)
            pathLineStyle.setFillColor(0xFFFFFF, 0x00)
            pathData2.pathLineStyle = pathLineStyle

            // add circle data
            val circleData = NMapCircleData(1)
            circleData.initCircleData()
            circleData.addCirclePoint(127.1075, 37.3675, 50.0f)
            circleData.endCircleData()
            pathDataOverlay.addCircleData(circleData)
            // set circle style
            val circleStyle = NMapCircleStyle(mMapView.context)
            circleStyle.setLineType(NMapPathLineStyle.TYPE_DASH)
            circleStyle.setFillColor(0x000000, 0x00)
            circleData.circleStyle = circleStyle

            // show all path data
            pathDataOverlay.showAllPathData(0)
        }
    }

    private fun testPOIdataOverlay() {
        // Markers for POI item
        val markerId = NMapPOIflagType.PIN

        // set POI data
        val poiData = NMapPOIdata(2, mMapViewerResourceProvider)
        poiData.beginPOIdata(2)
        val item = poiData.addPOIitem(127.0630205, 37.5091300, "Pizza 777-111", markerId, 0)
        item.setRightAccessory(true, NMapPOIflagType.CLICKABLE_ARROW)
        poiData.addPOIitem(127.061, 37.51, "Pizza 123-456", markerId, 0)
        poiData.endPOIdata()

        // create POI data overlay
        val poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null)

        // set event listener to the overlay
        poiDataOverlay.onStateChangeListener = onPOIdataStateChangeListener

        // select an item
        poiDataOverlay.selectPOIitem(0, true)

        // show all POI data
        //poiDataOverlay.showAllPOIdata(0);
    }

    /* POI data State Change Listener*/
    private val onPOIdataStateChangeListener = object : NMapPOIdataOverlay.OnStateChangeListener {

        override fun onCalloutClick(poiDataOverlay: NMapPOIdataOverlay, item: NMapPOIitem) {
            if (DEBUG) {
                Log.i(LOG_TAG, "onCalloutClick: title=" + item.title)
            }

            // [[TEMP]] handle a click event of the callout
            Toast.makeText(this@NMapViewer, "onCalloutClick: " + item.title, Toast.LENGTH_LONG).show()
        }

        override fun onFocusChanged(poiDataOverlay: NMapPOIdataOverlay, item: NMapPOIitem?) {
            if (DEBUG) {
                if (item != null) {
                    Log.i(LOG_TAG, "onFocusChanged: " + item.toString())
                } else {
                    Log.i(LOG_TAG, "onFocusChanged: ")
                }
            }
        }
    }

    /* MapView State Change Listener*/
    private val onMapViewStateChangeListener = object : NMapView.OnMapStateChangeListener {
        override fun onMapInitHandler(mapView: NMapView, errorInfo: NMapError?) {
            if (errorInfo == null) { // success
                // restore map view state such as map center position and zoom level.
                restoreInstanceState()
            } else { // fail
                Log.e(LOG_TAG, "onFailedToInitializeWithError: " + errorInfo.toString())
                Toast.makeText(this@NMapViewer, errorInfo.toString(), Toast.LENGTH_LONG).show()
            }
        }

        override fun onAnimationStateChange(mapView: NMapView, animType: Int, animState: Int) {
            if (DEBUG) {
                Log.i(LOG_TAG, "onAnimationStateChange: animType=$animType, animState=$animState")
            }
        }

        override fun onMapCenterChange(mapView: NMapView, center: NGeoPoint) {
            if (DEBUG) {
                Log.i(LOG_TAG, "onMapCenterChange: center=" + center.toString())
            }
        }

        override fun onZoomLevelChange(mapView: NMapView, level: Int) {
            if (DEBUG) {
                Log.i(LOG_TAG, "onZoomLevelChange: level=$level")
            }
        }

        override fun onMapCenterChangeFine(mapView: NMapView) {

        }
    }

    private val onMapViewTouchDelegate = NMapView.OnMapViewDelegate {
        if (mMapLocationManager != null) {
            if (mMapLocationManager.isMyLocationEnabled()) {
                return@OnMapViewDelegate mMapLocationManager.isMyLocationFixed()
            }
        }
        false
    }

    private val onMapViewTouchEventListener = object : NMapView.OnMapViewTouchEventListener {
        override fun onLongPress(mapView: NMapView, ev: MotionEvent) {}
        override fun onLongPressCanceled(mapView: NMapView) {}
        override fun onSingleTapUp(mapView: NMapView, ev: MotionEvent) {}
        override fun onTouchDown(mapView: NMapView, ev: MotionEvent) {}
        override fun onScroll(mapView: NMapView, e1: MotionEvent, e2: MotionEvent) {}
        override fun onTouchUp(mapView: NMapView, ev: MotionEvent) {}
    }

    /* NMapDataProvider Listener */
    private val onDataProviderListener = OnDataProviderListener { placeMark, errInfo ->
        if (DEBUG) {
            Log.i(LOG_TAG, "onReverseGeocoderResponse: placeMark=" + placeMark?.toString())
        }

        if (errInfo != null) {
            Log.e(LOG_TAG, "Failed to findPlacemarkAtLocation: error=" + errInfo.toString())

            Toast.makeText(this@NMapViewer, errInfo.toString(), Toast.LENGTH_LONG).show()
            return@OnDataProviderListener
        }

//        if (mFloatingPOIitem != null && mFloatingPOIdataOverlay != null) {
//            mFloatingPOIdataOverlay.deselectFocusedPOIitem()
//
//            if (placeMark != null) {
//                mFloatingPOIitem.setTitle(placeMark.toString())
//            }
//            mFloatingPOIdataOverlay.selectPOIitemBy(mFloatingPOIitem.getId(), false)
//        }
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