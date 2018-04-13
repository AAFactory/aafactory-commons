package com.github.aafactory.commons.utils

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
    }
}
