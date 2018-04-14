package com.github.aafactory.commons.utils

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class DateUtilsTest {
    private val millis = 1523595935571
    
    @Test
    fun getDateStringFromTimeMillisCaseA() {
        val dateString = DateUtils.getDateStringFromTimeMillis(millis, DateUtils.SIMPLE_DATE_FORMAT_FULL, Locale.ENGLISH)
        assertEquals("Friday, April 13, 2018", dateString)
    }

    @Test
    fun getDateStringFromTimeMillisCaseB() {
        val dateString = DateUtils.getDateStringFromTimeMillis(millis, DateUtils.SIMPLE_DATE_FORMAT_DATE_FIELD, Locale.ENGLISH)
        assertEquals("4/13/18", dateString)
    }

    /// ------------------------------------------------------------------
    /// Awesome Application Factory legacy function tests 
    /// ------------------------------------------------------------------
    @Test
    fun getFullPatternDate() {
        assertEquals("2018년 4월 13일 금요일", DateUtils.getFullPatternDate(millis))
    }
    
    @Test
    fun getFullPatternDateWithTime() {
        assertEquals("2018년 4월 13일 금요일 14:05", DateUtils.getFullPatternDateWithTime(millis))
    }
    
    @Test
    fun getFullPatternDateWithTimeAndSeconds() {
        assertEquals("Friday, April 13, 2018 14:05 35", DateUtils.getFullPatternDateWithTimeAndSeconds(millis, Locale.ENGLISH))
    }

    @Test
    fun timeMillisToDateTimeCaseA() {
        assertEquals("14:05", DateUtils.timeMillisToDateTime(millis, DateUtils.TIME_PATTERN))
    }
    
    @Test
    fun timeMillisToDateTimeCaseB() {
        assertEquals("14:05 35", DateUtils.timeMillisToDateTime(millis, DateUtils.TIME_PATTERN_WITH_SECONDS))
    }
    
    @Test
    fun timeMillisToDateTimeCaseC() {
        assertEquals("2018-04-13", DateUtils.timeMillisToDateTime(millis, DateUtils.DATE_PATTERN_DASH))
    }
    
    @Test
    fun timeMillisToDateTimeCaseD() {
        assertEquals("20180413140535", DateUtils.timeMillisToDateTime(millis, DateUtils.DATE_TIME_PATTERN_WITHOUT_DELIMITER))
    }
    
    @Test
    fun timeMillisToDateTimeCaseE() {
        assertEquals("2018", DateUtils.timeMillisToDateTime(millis, DateUtils.YEAR_PATTERN))
    }
    
    @Test
    fun timeMillisToDateTimeCaseF() {
        assertEquals("04", DateUtils.timeMillisToDateTime(millis, DateUtils.MONTH_PATTERN))
    }
    
    @Test
    fun timeMillisToDateTimeCaseG() {
        assertEquals("13", DateUtils.timeMillisToDateTime(millis, DateUtils.DAY_PATTERN))
    }
}