package com.github.aafactory.commons.utils

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class DateUtilsTest {

    @Test
    fun getDateStringFromTimeMillis_case1() {
        val millis = 1523595935571
        val dateString = DateUtils.getDateStringFromTimeMillis(millis, DateUtils.SIMPLE_DATE_FORMAT_FULL, Locale.ENGLISH)
        assertEquals("Friday, April 13, 2018", dateString)
    }

    @Test
    fun getDateStringFromTimeMillis_case2() {
        val millis = 1523595935571
        val dateString = DateUtils.getDateStringFromTimeMillis(millis, DateUtils.SIMPLE_DATE_FORMAT_DATE_FIELD, Locale.ENGLISH)
        assertEquals("4/13/18", dateString)
    }
}