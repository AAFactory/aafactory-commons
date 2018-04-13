package com.litesuits.common.utils

import org.junit.Assert
import org.junit.Test

class ByteUtilTest {

    @Test
    fun byteToBit_case1() {
        val testString = "Hello AAF"
        val bitString = ByteUtil.byteToBit(testString.toByteArray(Charsets.UTF_8))
        Assert.assertEquals("010010000110010101101100011011000110111100100000010000010100000101000110", bitString)
    }
}