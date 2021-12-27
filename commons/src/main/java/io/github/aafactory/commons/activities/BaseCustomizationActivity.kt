package io.github.aafactory.commons.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.simplemobiletools.commons.dialogs.ColorPickerDialog
import com.simplemobiletools.commons.extensions.setBackgroundWithStroke
import io.github.aafactory.commons.R
import io.github.aafactory.commons.dialogs.LineColorPickerDialog
import io.github.aafactory.commons.extensions.baseConfig
import io.github.aafactory.commons.extensions.darkenColor
import io.github.aafactory.commons.extensions.getThemeId
import kotlinx.android.synthetic.main.activity_customization.*

/**
 * Created by Hanjoong Cho on 2017-12-18.
 * This code based 'Simple-Commons' package
 * You can see original 'Simple-Commons' from below link.
 * https://github.com/SimpleMobileTools/Simple-Commons
 */


open class BaseCustomizationActivity : BaseSimpleActivity() {
    private var curTextColor = 0
    private var curBackgroundColor = 0
    private var curScreenBackgroundColor = 0
    private var curPrimaryColor = 0
    private var hasUnsavedChanges = false
    private var isLineColorPickerVisible = false
    private var curPrimaryLineColorPicker: LineColorPickerDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customization)

        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true);
            setHomeAsUpIndicator(R.drawable.ic_cross)
        }

//        updateTextColors(main_holder)
//        updateAppViews(main_holder)
        initColorVariables()
        setupColorsPickers()

        customization_text_color_holder.setOnClickListener { pickTextColor() }
        customization_background_color_holder.setOnClickListener { pickBackgroundColor() }
        customization_screen_background_color_holder.setOnClickListener { pickScreenBackgroundColor() }
        customization_primary_color_holder.setOnClickListener { pickPrimaryColor() }
    }

    override fun onResume() {
        super.onResume()
        updateBackgroundColor(curScreenBackgroundColor)
        updateActionbarColor(curPrimaryColor)
        setTheme(getThemeId(curPrimaryColor))

        curPrimaryLineColorPicker?.getSpecificColor()?.apply {
            updateActionbarColor(this)
            setTheme(getThemeId(this))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_customization, menu)
        menu.findItem(R.id.save).isVisible = hasUnsavedChanges
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> saveChanges(true)
            android.R.id.home -> super.onBackPressed()
//            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onBackPressed() {
        if (!hasUnsavedChanges) super.onBackPressed()
    }

    private fun saveChanges(finishAfterSave: Boolean) {
        baseConfig.apply {
            textColor = curTextColor
            backgroundColor = curBackgroundColor
            screenBackgroundColor = curScreenBackgroundColor
            primaryColor = curPrimaryColor
            isThemeChanged = true
        }

        hasUnsavedChanges = false
        if (finishAfterSave) {
            finish()
        } else {
            invalidateOptionsMenu()
        }
    }

    private fun initColorVariables() {
        curTextColor = baseConfig.textColor
        curBackgroundColor = baseConfig.backgroundColor
        curScreenBackgroundColor = baseConfig.screenBackgroundColor
        curPrimaryColor = baseConfig.primaryColor
    }

    private fun setupColorsPickers() {
        customization_text_color.setBackgroundWithStroke(curTextColor, curBackgroundColor)
        customization_primary_color.setBackgroundWithStroke(curPrimaryColor, curBackgroundColor)
        customization_background_color.setBackgroundWithStroke(curBackgroundColor, curBackgroundColor)
        customization_screen_background_color.setBackgroundWithStroke(curScreenBackgroundColor, curBackgroundColor)
    }

    private fun hasColorChanged(old: Int, new: Int) = Math.abs(old - new) > 1

    private fun colorChanged() {
        hasUnsavedChanges = true
        setupColorsPickers()
        invalidateOptionsMenu()
    }

    private fun setCurrentTextColor(color: Int) {
        curTextColor = color
//        updateTextColors(main_holder, curTextColor)
    }

    private fun setCurrentBackgroundColor(color: Int) {
        curBackgroundColor = color
//        updateAppViews(main_holder, curBackgroundColor)
    }

    private fun setCurrentScreenBackgroundColor(color: Int) {
        curScreenBackgroundColor = color
        updateBackgroundColor(color)
    }

    private fun setCurrentPrimaryColor(color: Int) {
        curPrimaryColor = color
        setCurrentScreenBackgroundColor(color.darkenColor())
        updateActionbarColor(color)
    }

    private fun pickTextColor() {
        ColorPickerDialog(this, curTextColor) {
            if (hasColorChanged(curTextColor, it)) {
                setCurrentTextColor(it)
                colorChanged()
            }
        }
    }

    private fun pickBackgroundColor() {
        ColorPickerDialog(this, curBackgroundColor) {
            if (hasColorChanged(curBackgroundColor, it)) {
                setCurrentBackgroundColor(it)
                colorChanged()
            }
        }
    }

    private fun pickScreenBackgroundColor() {
        ColorPickerDialog(this, curScreenBackgroundColor) {
            if (hasColorChanged(curScreenBackgroundColor, it)) {
                setCurrentScreenBackgroundColor(it)
                colorChanged()
            }
        }
    }

    private fun pickPrimaryColor() {
        isLineColorPickerVisible = true
        curPrimaryLineColorPicker = LineColorPickerDialog(this, curPrimaryColor) { wasPositivePressed, color ->
            curPrimaryLineColorPicker = null
            isLineColorPickerVisible = false
            if (wasPositivePressed) {
                if (hasColorChanged(curPrimaryColor, color)) {
                    setCurrentPrimaryColor(color)
                    colorChanged()
                    setTheme(getThemeId(color))
                }
            } else {
                updateActionbarColor(curPrimaryColor)
                setTheme(getThemeId(curPrimaryColor))
                updateBackgroundColor(curPrimaryColor)
            }
        }
    }
}