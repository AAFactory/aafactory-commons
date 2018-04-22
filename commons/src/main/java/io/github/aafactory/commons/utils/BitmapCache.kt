package io.github.aafactory.commons.utils

import android.graphics.Bitmap
import android.util.LruCache

/**
 * Created by CHO HANJOONG on 2018-04-22.
 */

object BitmapCache {

    /// ------------------------------------------------------------------
    /// Awesome Application Factory legacy functions 
    /// ------------------------------------------------------------------
    private var mMemoryCache: LruCache<String, Bitmap>? = null

    init {
        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()

        // Use 1/8th of the available memory for this memory cache.
        val cacheSize = maxMemory / 8

        mMemoryCache = object : LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String, bitmap: Bitmap): Int = bitmap.byteCount / 1024
        }
    }

    fun getBitmapFromMemCache(key: String): Bitmap? = mMemoryCache?.get(key)

    fun addBitmapToMemoryCache(key: String, bitmap: Bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache?.put(key, bitmap)
        }
    }
}