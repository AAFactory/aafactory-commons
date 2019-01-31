package io.github.aafactory.commons.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {

    companion object {
        const val SIMPLE_DATE_FORMAT_FULL = SimpleDateFormat.FULL
        const val SIMPLE_DATE_FORMAT_DATE_FIELD = SimpleDateFormat.DATE_FIELD

        fun getDateStringFromTimeMillis(timeMillis: Long, dateFormat: Int = SIMPLE_DATE_FORMAT_FULL, locale: Locale = Locale.getDefault()): String {
            val date = Date(timeMillis)
            val dateFormat = SimpleDateFormat.getDateInstance(dateFormat, locale)
            return dateFormat.format(date)
        }
        
        
        /// ------------------------------------------------------------------
        /// Awesome Application Factory legacy functions 
        /// ------------------------------------------------------------------
        val TIME_PATTERN = "HH:mm"
        val TIME_PATTERN_WITH_SECONDS = "HH:mm ss"
        val DATE_PATTERN_DASH = "yyyy-MM-dd"
        val DATE_TIME_PATTERN_WITHOUT_DELIMITER = "yyyyMMddHHmmss"
        val YEAR_PATTERN = "yyyy"
        val MONTH_PATTERN = "MM"
        val DAY_PATTERN = "dd"
        
        fun getFullPatternDate(timeMillis: Long): String {
            val date = Date(timeMillis)
            val dateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.FULL, Locale.getDefault())
            return dateFormat.format(date)
        }
        
        fun getFullPatternDateWithTime(timeMillis: Long): String {
            val date = Date(timeMillis)
            val dateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.FULL, Locale.getDefault())
            val hourFormat = SimpleDateFormat(TIME_PATTERN)
            return String.format("%s %s", dateFormat.format(date), hourFormat.format(date))
        }
        
        fun getFullPatternDateWithTimeAndSeconds(timeMillis: Long, locale: Locale = Locale.getDefault()): String {
            val date = Date(timeMillis)
            val dateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.FULL, locale)
            val hourFormat = SimpleDateFormat(TIME_PATTERN_WITH_SECONDS)
            return String.format("%s %s", dateFormat.format(date), hourFormat.format(date))
        }

        fun getCurrentDateTime(pattern: String): String {
            val date = Date()
            val dateFormat = SimpleDateFormat(pattern)
            return dateFormat.format(date)
        }
       
        fun timeMillisToDateTime(timeMillis: Long, pattern: String): String {
            val date = Date(timeMillis)
            val dateFormat = SimpleDateFormat(pattern)
            return dateFormat.format(date)
        }
    }
}
