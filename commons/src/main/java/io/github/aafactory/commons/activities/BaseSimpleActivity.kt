package io.github.aafactory.commons.activities

import android.app.ActivityManager
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import io.github.aafactory.commons.extensions.*

/**
 * Created by CHO HANJOONG on 2017-11-25.
 * This code based 'Simple-Commons' package
 * You can see original 'Simple-Commons' from below link.
 * https://github.com/SimpleMobileTools/Simple-Commons
 */

open class BaseSimpleActivity : AppCompatActivity() {
    var actionOnPermission: ((granted: Boolean) -> Unit)? = null
    var isAskingPermissions = false
    var useDynamicTheme = true
    private val GENERIC_PERM_HANDLER = 100

    open fun isEnableStatusBarDarkenColor(): Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        if (useDynamicTheme) {
            setTheme(getThemeId())
//            setTheme(R.style.AppTheme_AAF)
        }

        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        if (useDynamicTheme) {
            setTheme(getThemeId())
            updateBackgroundColor(baseConfig.screenBackgroundColor)
        }
        updateActionbarColor()
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this@BaseSimpleActivity.onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        isAskingPermissions = false
        if (requestCode == GENERIC_PERM_HANDLER && grantResults.isNotEmpty()) {
            actionOnPermission?.invoke(grantResults[0] == 0)
        }
    }
    
    fun startCustomizationActivity() = startActivity(Intent(this, BaseCustomizationActivity::class.java))

    fun updateActionbarColor(color: Int = baseConfig.primaryColor) {
        supportActionBar?.setBackgroundDrawable(ColorDrawable(color))
//        supportActionBar?.title = Html.fromHtml("<font color='${color.getContrastColor().toHex()}'>${supportActionBar?.title}</font>")
        updateStatusbarColor(color)

        if (isLollipopPlus()) {
            setTaskDescription(ActivityManager.TaskDescription(null, null, color))
        }
    }

    fun updateStatusbarColor(color: Int) {
        if (isLollipopPlus()) {
            window.statusBarColor = if (isEnableStatusBarDarkenColor()) color.darkenColor() else color
        }
    }

    open fun updateBackgroundColor(color: Int = baseConfig.screenBackgroundColor) {
        val mainView: ViewGroup? = getMainViewGroup()
        mainView?.run {
//            setBackgroundColor(ColorUtils.setAlphaComponent(color, 255))
            setBackgroundColor(color)
        }
    }
    
    open fun getMainViewGroup(): ViewGroup? = null
    
//    open fun getBackgroundAlpha(): Int = 255
    
    fun handlePermission(permissionId: Int, callback: (granted: Boolean) -> Unit) {
        actionOnPermission = null
        if (hasPermission(permissionId)) {
            callback(true)
        } else {
            isAskingPermissions = true
            actionOnPermission = callback
            ActivityCompat.requestPermissions(this, arrayOf(getPermissionString(permissionId)), GENERIC_PERM_HANDLER)
        }
    }
}