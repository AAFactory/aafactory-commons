package io.github.aafactory.commons.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlarmManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import io.github.aafactory.commons.R
import io.github.aafactory.commons.utils.DateUtils

fun Activity.makeToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Activity.showAlertDialog(title: String?, message: String, positiveListener: DialogInterface.OnClickListener?, negativeListener: DialogInterface.OnClickListener?, cancelable: Boolean = true) {
    val builder = AlertDialog.Builder(this)
    builder.setCancelable(cancelable)
    builder.setNegativeButton(getString(R.string.cancel), negativeListener)
    builder.setPositiveButton(getString(R.string.ok), positiveListener)
    builder.create().apply {
        updateAlertDialog(this, message, null, title)
    }
}

fun Activity.showAlertDialog(message: String, positiveListener: DialogInterface.OnClickListener, negativeListener: DialogInterface.OnClickListener?) {
    showAlertDialog(null, message, positiveListener, negativeListener)
}

fun Activity.showAlertDialog(title: String?, message: String, positiveListener: DialogInterface.OnClickListener?, cancelable: Boolean = true) {
    val builder = AlertDialog.Builder(this)
    builder.setCancelable(cancelable)
    builder.setPositiveButton(getString(R.string.ok), positiveListener)
    builder.create().apply {
        updateAlertDialog(this, message, null, title)
    }
}

fun Activity.showAlertDialog(message: String, positiveListener: DialogInterface.OnClickListener?, cancelable: Boolean = true) {
    showAlertDialog(null, message, positiveListener, cancelable)
}

fun Activity.determineNextAlarm() {
    val nextAlarm = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val triggerTimeMillis = (getSystemService(Context.ALARM_SERVICE) as AlarmManager).nextAlarmClock?.triggerTime ?: 0
        when (triggerTimeMillis > 0) {
            true -> DateUtils.getFullPatternDateWithTime(triggerTimeMillis)
            false -> "Alarm info is not exist."
        }
    } else {
        @Suppress("DEPRECATION")
        Settings.System.getString(contentResolver, Settings.System.NEXT_ALARM_FORMATTED)
    }
    makeToast(nextAlarm)
}

fun Activity.triggerRestart(cls: Class<*>) {
    val intent = Intent(this, cls)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    this.startActivity(intent)
    finish()
    Runtime.getRuntime().exit(0)
}

fun Activity.setScreenOrientationSensor(enableSensor: Boolean) {
    requestedOrientation = when (enableSensor) {
        true -> ActivityInfo.SCREEN_ORIENTATION_SENSOR
        false -> ActivityInfo.SCREEN_ORIENTATION_NOSENSOR
    }
}

@SuppressLint("SourceLockedOrientationActivity")
fun Activity.holdCurrentOrientation() {
    when (resources.configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        Configuration.ORIENTATION_LANDSCAPE -> requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }
}

fun Activity.clearHoldOrientation() {
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
}