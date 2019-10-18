package io.github.aafactory.commons.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.graphics.ColorUtils
import com.simplemobiletools.commons.dialogs.ColorPickerDialog
import com.simplemobiletools.commons.extensions.getThemeId
import com.simplemobiletools.commons.extensions.setBackgroundWithStroke
import com.simplemobiletools.commons.models.MyTheme
import io.github.aafactory.commons.R
import io.github.aafactory.commons.extensions.baseConfig
import io.github.aafactory.commons.extensions.updateTextColors
import io.github.aafactory.commons.dialogs.LineColorPickerDialog
import io.github.aafactory.commons.extensions.updateAppViews
import kotlinx.android.synthetic.main.activity_customization.*
import java.util.*

/**
 * Created by Hanjoong Cho on 2017-12-18.
 * This code based 'Simple-Commons' package
 * You can see original 'Simple-Commons' from below link.
 * https://github.com/SimpleMobileTools/Simple-Commons
 */


open class BaseCustomizationActivity : BaseSimpleActivity() {
    private val THEME_LIGHT = 0
    private val THEME_DARK = 1
    private val THEME_SOLARIZED = 2
    private val THEME_DARK_RED = 3
    private val THEME_CUSTOM = 4
    private val THEME_SHARED = 5

    private var curTextColor = 0
    private var curBackgroundColor = 0
    private var curScreenBackgroundColor = 0
    private var curPrimaryColor = 0
    private var curSelectedThemeId = 0
    private var hasUnsavedChanges = false
    private var isLineColorPickerVisible = false
    private var predefinedThemes = LinkedHashMap<Int, MyTheme>()
    private var curPrimaryLineColorPicker: LineColorPickerDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customization)

        predefinedThemes.apply {
            put(THEME_CUSTOM, MyTheme(R.string.custom, 0, 0, 0))
        }

        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true);
            setHomeAsUpIndicator(R.drawable.ic_cross)
        }

        updateTextColors(main_holder)
        updateAppViews(main_holder)
        initColorVariables()
        setupColorsPickers()

        customization_text_color_holder.setOnClickListener { pickTextColor() }
        customization_background_color_holder.setOnClickListener { pickBackgroundColor() }
        customization_screen_background_color_holder.setOnClickListener { pickBackgroundColor2() }
        customization_primary_color_holder.setOnClickListener { pickPrimaryColor() }
        setupThemePicker()
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
        if (hasUnsavedChanges) {
//            promptSaveDiscard()
        } else {
            super.onBackPressed()
        }
    }

    private fun setupThemePicker() {
        curSelectedThemeId = getCurrentThemeId()
    }

    private fun updateColorTheme(themeId: Int, useStored: Boolean = false) {
        curSelectedThemeId = themeId

        resources.apply {
            if (curSelectedThemeId == THEME_CUSTOM) {
                if (useStored) {
                    curTextColor = baseConfig.customTextColor
                    curBackgroundColor = baseConfig.customBackgroundColor
                    curPrimaryColor = baseConfig.customPrimaryColor
                    setTheme(getThemeId(curPrimaryColor))
                    setupColorsPickers()
                } else {
                    baseConfig.customPrimaryColor = curPrimaryColor
                    baseConfig.customBackgroundColor = curBackgroundColor
                    baseConfig.customTextColor = curTextColor
                }
            }
        }

        hasUnsavedChanges = true
        invalidateOptionsMenu()
        updateTextColors(main_holder, curTextColor)

        updateBackgroundColor()
        updateActionbarColor(curPrimaryColor)
    }

    private fun getCurrentThemeId(): Int {
        if (baseConfig.isUsingSharedTheme)
            return THEME_SHARED

        var themeId = THEME_CUSTOM
        resources.apply {
            for ((key, value) in predefinedThemes.filter { it.key != THEME_CUSTOM && it.key != THEME_SHARED }) {
                if (curTextColor == getColor(value.textColorId) && curBackgroundColor == getColor(value.backgroundColorId) && curPrimaryColor == getColor(value.primaryColorId)) {
                    themeId = key
                }
            }
        }
        return themeId
    }

    private fun saveChanges(finishAfterSave: Boolean) {
        baseConfig.apply {
            textColor = curTextColor
            backgroundColor = curBackgroundColor
            screenBackgroundColor = curScreenBackgroundColor
            primaryColor = curPrimaryColor
            isThemeChanged = true
        }

        baseConfig.isUsingSharedTheme = curSelectedThemeId == THEME_SHARED
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
        if (baseConfig.screenBackgroundColor == -1) {
            customization_screen_background_color.setBackgroundWithStroke(ColorUtils.setAlphaComponent(baseConfig.primaryColor, getBackgroundAlpha()), curBackgroundColor)
        } else {
            customization_screen_background_color.setBackgroundWithStroke(curScreenBackgroundColor, curBackgroundColor)
        }
    }

    private fun hasColorChanged(old: Int, new: Int) = Math.abs(old - new) > 1

    private fun colorChanged() {
        hasUnsavedChanges = true
        setupColorsPickers()
        invalidateOptionsMenu()
    }

    private fun setCurrentTextColor(color: Int) {
        curTextColor = color
//        updateTextColors(customization_holder, color)
    }

    private fun setCurrentBackgroundColor(color: Int) {
        curBackgroundColor = color
        updateAppViews(main_holder, curBackgroundColor)

//        updateBackgroundColor(color)
    }

    private fun setCurrentBackgroundColor2(color: Int) {
        curScreenBackgroundColor = color
//        updateAppViews(main_holder, curScreenBackgroundColor)
        updateBackgroundColor(color)
    }

    private fun setCurrentPrimaryColor(color: Int) {
        curPrimaryColor = color
        updateActionbarColor(color)
    }

    private fun pickTextColor() {
        ColorPickerDialog(this, curTextColor) {
            if (hasColorChanged(curTextColor, it)) {
                setCurrentTextColor(it)
                colorChanged()
                updateColorTheme(getUpdatedTheme())
            }
        }
    }

    private fun pickBackgroundColor() {
        ColorPickerDialog(this, curBackgroundColor) {
            if (hasColorChanged(curBackgroundColor, it)) {
                setCurrentBackgroundColor(it)
                colorChanged()
                updateColorTheme(getUpdatedTheme())
            }
        }
    }

    private fun pickBackgroundColor2() {
        ColorPickerDialog(this, curScreenBackgroundColor) {
            if (hasColorChanged(curScreenBackgroundColor, it)) {
                setCurrentBackgroundColor2(it)
                colorChanged()
//                updateColorTheme(getUpdatedTheme())
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
                    updateColorTheme(getUpdatedTheme())
                    setTheme(getThemeId(color))
                    updateBackgroundColor(color)
                }
            } else {
                updateActionbarColor(curPrimaryColor)
                setTheme(getThemeId(curPrimaryColor))
                updateBackgroundColor(curPrimaryColor)
            }
        }
    }

    private fun getUpdatedTheme() = if (curSelectedThemeId == THEME_SHARED) THEME_SHARED else THEME_CUSTOM
}