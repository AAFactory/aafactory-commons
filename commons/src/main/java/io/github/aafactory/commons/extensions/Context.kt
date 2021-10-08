package io.github.aafactory.commons.extensions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Looper
import android.util.TypedValue
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import com.simplemobiletools.commons.extensions.baseConfig
import com.simplemobiletools.commons.helpers.*
import io.github.aafactory.commons.databinding.DialogMessageBinding
import io.github.aafactory.commons.helpers.BaseConfig
import io.github.aafactory.commons.helpers.PERMISSION_ACCESS_COARSE_LOCATION
import io.github.aafactory.commons.helpers.PERMISSION_ACCESS_FINE_LOCATION
import io.github.aafactory.commons.helpers.SETTING_SCREEN_BACKGROUND_COLOR_DEFAULT
import io.github.aafactory.commons.utils.CommonUtils

/**
 * Created by CHO HANJOONG on 2017-12-30.
 * This code based 'Simple-Commons' package
 * You can see original 'Simple-Commons' from below link.
 * https://github.com/SimpleMobileTools/Simple-Commons
 */

fun Context.isOnMainThread() = Looper.myLooper() == Looper.getMainLooper()
fun Context.getSharedPrefs() = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)

fun Context.isJellyBean1Plus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
fun Context.isAndroidFour() = Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT_WATCH
fun Context.isKitkatPlus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
fun Context.isLollipopPlus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
fun Context.isMarshmallowPlus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
fun Context.isNougatPlus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
fun Context.isOreoPlus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

fun Context.updateAlertDialog(alertDialog: AlertDialog, message: String? = null, customView: View? = null, customTitle: String? = null) {
    alertDialog.run {
        when (customView == null) {
            true -> {
                DialogMessageBinding.inflate(layoutInflater).apply {
                    root.apply {
                        simpleMessage.text = message
                    }
                    setView(root)
                }
            }
            false -> setView(customView)
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        customTitle?.let {
            val titleView = TextView(this@updateAlertDialog).apply {
                text = customTitle
                val padding = CommonUtils.dpToPixel(this@updateAlertDialog, 15F)
                setPadding(padding * 2, padding, padding * 2, padding)
                setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18F)
            }
            setCustomTitle(titleView)
        }
        show()
        getButton(AlertDialog.BUTTON_POSITIVE).run {}
        getButton(AlertDialog.BUTTON_NEGATIVE).run {}
    }
}

fun Context.dpToPixel(dp: Float, policy: Int = 0): Int {
    val px: Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
    return when (policy) {
        0 -> Math.floor(px.toDouble()).toInt()
        1 -> Math.ceil(px.toDouble()).toInt()
        else -> 0
    }
}

fun Context.dpToPixel(dp: Float): Int = dpToPixel(dp, 0)

val Context.baseConfig: BaseConfig get() = BaseConfig.newInstance(this)

fun Context.hasPermission(permId: Int) = ContextCompat.checkSelfPermission(this, getPermissionString(permId)) == PackageManager.PERMISSION_GRANTED

fun Context.getPermissionString(id: Int) = when (id) {
    PERMISSION_READ_STORAGE -> Manifest.permission.READ_EXTERNAL_STORAGE
    PERMISSION_WRITE_STORAGE -> Manifest.permission.WRITE_EXTERNAL_STORAGE
    PERMISSION_CAMERA -> Manifest.permission.CAMERA
    PERMISSION_RECORD_AUDIO -> Manifest.permission.RECORD_AUDIO
    PERMISSION_READ_CONTACTS -> Manifest.permission.READ_CONTACTS
    PERMISSION_WRITE_CONTACTS -> Manifest.permission.WRITE_CONTACTS
    PERMISSION_READ_CALENDAR -> Manifest.permission.READ_CALENDAR
    PERMISSION_WRITE_CALENDAR -> Manifest.permission.WRITE_CALENDAR
    PERMISSION_CALL_PHONE -> Manifest.permission.CALL_PHONE
    PERMISSION_ACCESS_FINE_LOCATION ->  Manifest.permission.ACCESS_FINE_LOCATION
    PERMISSION_ACCESS_COARSE_LOCATION -> Manifest.permission.ACCESS_COARSE_LOCATION
    else -> ""
}

fun Context.getCurScreenBackgroundColor(backgroundAlpha: Int): Int = when (baseConfig.screenBackgroundColor == SETTING_SCREEN_BACKGROUND_COLOR_DEFAULT) {
    true -> ColorUtils.setAlphaComponent(baseConfig.primaryColor, backgroundAlpha)
    false -> baseConfig.screenBackgroundColor
}

fun Context.isConnectedOrConnecting(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
    return activeNetwork?.isConnectedOrConnecting == true
}

fun Context.isNightMode() = when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
    Configuration.UI_MODE_NIGHT_YES -> false
    Configuration.UI_MODE_NIGHT_NO -> false
    else -> false
}