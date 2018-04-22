package io.github.aafactory.commons.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.media.ExifInterface
import android.support.v4.content.ContextCompat
import io.github.aafactory.commons.extensions.dpToPixel
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import io.github.aafactory.commons.R

/**
 * Created by CHO HANJOONG on 2018-04-22.
 */

object BitmapUtils {
    
    /// ------------------------------------------------------------------
    /// Awesome Application Factory legacy functions 
    /// ------------------------------------------------------------------
    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val h = options.outHeight
        val w = options.outWidth
        var inSampleSize = 0
        if (h > reqHeight || w > reqWidth) {
            val ratioW = w.toFloat() / reqWidth
            val ratioH = h.toFloat() / reqHeight
            inSampleSize = Math.min(ratioH, ratioW).toInt()
        }
        inSampleSize = Math.max(1, inSampleSize)
        return inSampleSize
    }

    fun saveBitmap(srcPath: String, destPath: String, fixedWidthHeight: Int, orientation: Int = 1): Boolean {
        var result = true
        var outputStream: OutputStream? = null
        try {
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }

            BitmapFactory.decodeFile(srcPath, options)
            val inSampleSize = calculateInSampleSize(options, fixedWidthHeight, fixedWidthHeight)
            options.inJustDecodeBounds = false
            options.inSampleSize = inSampleSize
            val bitmap = BitmapFactory.decodeFile(srcPath, options)
            val matrix = Matrix()
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
                ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
                ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
            }
            val rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

            val height = rotateBitmap.height
            val width = rotateBitmap.width
            val downSampleHeight = height / width.toFloat() * fixedWidthHeight
            val downSampleWidth = width / height.toFloat() * fixedWidthHeight
            val thumbNail = when (width > height) {
                true -> Bitmap.createScaledBitmap(rotateBitmap, fixedWidthHeight, downSampleHeight.toInt(), false)
                false -> Bitmap.createScaledBitmap(rotateBitmap, downSampleWidth.toInt(), fixedWidthHeight, false)
            }
            outputStream = FileOutputStream(destPath)
            thumbNail.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            result = false
        } finally {
            IOUtils.closeQuietly(outputStream)
        }
        return result
    }


    fun decodeFile(activity: Activity, imagePath: String?, options: BitmapFactory.Options? = null): Bitmap = when (imagePath != null && File(imagePath).exists()) {
        true -> {
            options?.let { BitmapFactory.decodeFile(imagePath, options) } ?: BitmapFactory.decodeFile(imagePath)
        }
        false -> {
            BitmapFactory.decodeResource(activity.resources, android.R.drawable.ic_menu_gallery)
        }
    }

    fun addBorder(context: Context, bmp: Bitmap, scaleFactor: Double): Bitmap {
        val borderSize = context.dpToPixel(1.5F)
        val targetFlameWidth: Int = (bmp.width * scaleFactor).toInt() + (borderSize * 2)
        val targetFlameHeight: Int = (bmp.height * scaleFactor).toInt() + (borderSize * 2)
        val targetPhotoWidth: Int = (bmp.width * scaleFactor).toInt()
        val targetPhotoHeight: Int = (bmp.height * scaleFactor).toInt()
        val bmpWithBorder = Bitmap.createBitmap(targetFlameWidth, targetFlameHeight, bmp.config)
        val samplingPhoto = Bitmap.createScaledBitmap(bmp, targetPhotoWidth, targetPhotoHeight, false)
        val canvas = Canvas(bmpWithBorder)
        canvas.drawColor(ContextCompat.getColor(context, R.color.colorPrimary))
        canvas.drawBitmap(samplingPhoto, borderSize.toFloat(), borderSize.toFloat(), null)
        return bmpWithBorder
    }

    fun addFilmFrame(activity: Activity, bmp: Bitmap, scaleFactor: Double, resourceId: Int): Bitmap {
        val frameWidth = if (resourceId == R.drawable.frame_03) 137 else 140
        val frameHeight = if (resourceId == R.drawable.frame_03) 117 else 99
        val frameInnerWidth = if (resourceId == R.drawable.frame_03) 122F else 105F
        val frameInnerHeight = if (resourceId == R.drawable.frame_03) 77F else 63F

        // 01. calculate ratio
        val ratioX: Float = frameInnerWidth / (bmp.width * scaleFactor).toInt()
        val ratioY: Float = frameInnerHeight / (bmp.height * scaleFactor).toInt()

        // 02. setting the target photo size 
        val targetPhotoWidth: Int = (bmp.width * scaleFactor).toInt()
        val targetPhotoHeight: Int = (bmp.height * scaleFactor).toInt()

        // 03. calculate frame width and height
        val targetFlameWidth: Int = (frameWidth / ratioX).toInt()
        val targetFlameHeight: Int = (frameHeight / ratioY).toInt()

        val temp = BitmapFactory.decodeResource(activity.resources, resourceId)
        val bmpWithFrame = Bitmap.createBitmap(targetFlameWidth, targetFlameHeight, temp.config)
        val canvas = Canvas(bmpWithFrame)
        val frame = Bitmap.createScaledBitmap(temp, targetFlameWidth, targetFlameHeight, false)
        val samplingPhoto = Bitmap.createScaledBitmap(bmp, targetPhotoWidth, targetPhotoHeight, false)
        canvas.drawBitmap(frame, 0f, 0f, null)
        canvas.drawBitmap(samplingPhoto, (targetFlameWidth - targetPhotoWidth) / 2F, (targetFlameHeight - targetPhotoHeight) / 2F, null)
        return bmpWithFrame
    }

    fun decodeFileMaxWidthHeight(path: String, maxWidthHeight: Int): Bitmap {
        var inputStream: InputStream? = FileUtils.openInputStream(File(path))
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }

        BitmapFactory.decodeStream(inputStream, null, options)
        IOUtils.closeQuietly(inputStream)
        val inSampleSize = calculateInSampleSize(options, maxWidthHeight, maxWidthHeight)
        inputStream = FileUtils.openInputStream(File(path))
        options.inJustDecodeBounds = false
        options.inSampleSize = inSampleSize
        val tempBitmap = BitmapFactory.decodeStream(inputStream, null, options)
        val sampling = when (tempBitmap.width < tempBitmap.height) {
            true -> {
                val ratio: Float = maxWidthHeight * 1.0F / tempBitmap.height
                Bitmap.createScaledBitmap(tempBitmap, (tempBitmap.width * ratio).toInt(), (tempBitmap.height * ratio).toInt(), false)
            }
            false -> {
                val ratio: Float = maxWidthHeight * 1.0F / tempBitmap.width
                Bitmap.createScaledBitmap(tempBitmap, (tempBitmap.width * ratio).toInt(), (tempBitmap.height * ratio).toInt(), false)
            }
        }
        return sampling
    }

    fun decodeFileCropCenter(path: String, fixedWidthHeight: Int): Bitmap {
        var inputStream: InputStream = FileUtils.openInputStream(File(path))
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }

        BitmapFactory.decodeStream(inputStream, null, options)
        IOUtils.closeQuietly(inputStream)
        val inSampleSize = calculateInSampleSize(options, fixedWidthHeight, fixedWidthHeight)
        inputStream = FileUtils.openInputStream(File(path))
        options.inJustDecodeBounds = false
        options.inSampleSize = inSampleSize
        val tempBitmap = BitmapFactory.decodeStream(inputStream, null, options)
        val sampling = when (tempBitmap.width > tempBitmap.height) {
            true -> {
                val ratio: Float = fixedWidthHeight * 1.0F / tempBitmap.height
                Bitmap.createScaledBitmap(tempBitmap, (tempBitmap.width * ratio).toInt(), (tempBitmap.height * ratio).toInt(), false)
            }
            false -> {
                val ratio: Float = fixedWidthHeight * 1.0F / tempBitmap.width
                Bitmap.createScaledBitmap(tempBitmap, (tempBitmap.width * ratio).toInt(), (tempBitmap.height * ratio).toInt(), false)
            }
        }
        return cropCenter(sampling)
    }

    fun cropCenter(srcBmp: Bitmap): Bitmap {
        return when (srcBmp.width >= srcBmp.height) {
            true -> {
                Bitmap.createBitmap(
                        srcBmp,
                        srcBmp.width / 2 - srcBmp.height / 2,
                        0,
                        srcBmp.height,
                        srcBmp.height
                )
            }
            false -> {
                Bitmap.createBitmap(
                        srcBmp,
                        0,
                        srcBmp.height / 2 - srcBmp.width / 2,
                        srcBmp.width,
                        srcBmp.width
                )
            }
        }
    }
}