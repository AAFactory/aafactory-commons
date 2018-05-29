package io.github.aafactory.commons.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager

/**
 * Created by CHO HANJOONG on 2018-05-29.
 */

class LocationUtils {
    companion object {
        private fun Context.getGPSProvider(): LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        private fun Context.getNetworkProvider(): LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        @SuppressLint("MissingPermission")
        fun Context.getLocationWithGPSProvider(): Location? {
            return getGPSProvider().getLastKnownLocation(LocationManager.GPS_PROVIDER) ?: getNetworkProvider().getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        }
    }
}