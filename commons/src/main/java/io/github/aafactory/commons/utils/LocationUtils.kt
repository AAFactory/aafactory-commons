package io.github.aafactory.commons.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.location.Location
import android.support.design.widget.Snackbar
import android.view.View
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.Task
import io.github.aafactory.commons.extensions.showSnackBar

/**
 * Created by CHO HANJOONG on 2018-05-29.
 */

class LocationUtils {
    companion object {
        @SuppressLint("MissingPermission")
        fun getLastLocation(activity: Activity, fusedLocationProviderClient: FusedLocationProviderClient, callBack: (task: Task<Location>) -> Unit) {
            fusedLocationProviderClient.lastLocation
                    .addOnCompleteListener(activity) { task ->
                        if (task.isSuccessful && task.result != null) {
                            callBack(task)
                        } else {
//                            DialogUtil.showTips(activity, "", task.exception.toString())
//                        showSnackbar(R.string.no_location_detected)
                            activity.findViewById<View>(android.R.id.content).showSnackBar(task.exception.toString(), Snackbar.LENGTH_LONG)
                        }
                    }
        }
    }
}