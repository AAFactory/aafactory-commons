package io.github.aafactory.commons.helpers

import android.content.Context
import android.preference.PreferenceManager

/**
 * Created by CHO HANJOONG on 2018-04-15.
 */

open class BaseConfig(context: Context) : com.simplemobiletools.commons.helpers.BaseConfig(context) {
    protected val legacyPrefs = PreferenceManager.getDefaultSharedPreferences(context)!!

    companion object {
        fun newInstance(context: Context) = BaseConfig(context)
    }

    /// ------------------------------------------------------------------
    /// Awesome Application Factory legacy properties 
    /// ------------------------------------------------------------------
    var aafPatternLockProtectionOn: Boolean
        get() = prefs.getBoolean(AAF_PATTERN_LOCK_PROTECTION_ON, false)
        set(appPasswordProtectionOn) = prefs.edit().putBoolean(AAF_PATTERN_LOCK_PROTECTION_ON, appPasswordProtectionOn).apply()

    var aafPatternLockPauseMillis: Long
        get() = prefs.getLong(AAF_PATTERN_LOCK_PAUSE_MILLIS, 0L)
        set(aafPatternLockPauseMillis) = prefs.edit().putLong(AAF_PATTERN_LOCK_PAUSE_MILLIS, aafPatternLockPauseMillis).apply()

    var aafPatternLockSaved: String
        get() = legacyPrefs.getString(AAF_PATTERN_LOCK_SAVED, AAF_PATTERN_LOCK_DEFAULT)
        set(aafPatternLockSaved) = legacyPrefs.edit().putString(AAF_PATTERN_LOCK_SAVED, aafPatternLockSaved).apply()

    var aafPinLockPauseMillis: Long
        get() = prefs.getLong(AAF_PIN_LOCK_PAUSE_MILLIS, 0L)
        set(aafPinLockPauseMillis) = prefs.edit().putLong(AAF_PIN_LOCK_PAUSE_MILLIS, aafPinLockPauseMillis).apply()

    var isThemeChanged: Boolean
        get() = prefs.getBoolean(AAF_THEME_CHANGE, false)
        set(isThemeChanged) = prefs.edit().putBoolean(AAF_THEME_CHANGE, isThemeChanged).apply()
}