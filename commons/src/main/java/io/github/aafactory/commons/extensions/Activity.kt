package io.github.aafactory.commons.extensions

import android.app.Activity
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import io.github.aafactory.commons.utils.DateUtils

fun Activity.makeToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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
