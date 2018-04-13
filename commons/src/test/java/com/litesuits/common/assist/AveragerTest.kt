package com.litesuits.common.assist

import org.junit.Assert
import org.junit.Test

/**
 * Created by CHO HANJOONG on 2018-04-14.
 */

class AveragerTest {
    
    @Test
    fun getAverageTest() {
        val averager = Averager()
        averager.add(100)
        averager.add(70)
        averager.add(50)
        averager.add(20)
        Assert.assertTrue(averager.average.toInt() == 60)
    }
}