package io.github.aafactory.commons.utils

import android.app.Activity
import android.app.AlarmManager
import android.content.ContentResolver
import android.content.Context
import android.graphics.Point
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.TypedValue
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import id.zelory.compressor.Compressor
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import java.io.File
import java.io.FileOutputStream

/**
 * Created by CHO HANJOONG on 2018-05-20.
 */

class CommonUtils {
    companion object {
        fun getDefaultDisplay(activity: Activity): Point {
            val display = activity.windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            return size
        }
        
        fun dpToPixelFloatValue(context: Context, dp: Float): Float {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
        }

        fun dpToPixel(context: Context, dp: Float, policy: CALCULATION = CALCULATION.CEIL): Int {
            val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
            return when (policy) {
                CALCULATION.CEIL -> Math.ceil(px.toDouble()).toInt()
                CALCULATION.ROUND -> Math.round(px)
                CALCULATION.FLOOR -> Math.floor(px.toDouble()).toInt()
            }
        }

        fun uriToPath(contentResolver: ContentResolver, uri: Uri): String? {
            var path: String? = null
            val cursor = contentResolver.query(uri, null, null, null, null)
            cursor!!.moveToNext()
            val columnIndex = cursor.getColumnIndex("_data")
            path = when (columnIndex > 0) {
                true -> cursor.getString(columnIndex)
                false -> uri.toString()
            }
            cursor.close()
            return path
        }

        fun uriToFile(context: Context, uri: Uri, photoPath: String): Boolean {
            var result = false
            try {
                val tempFile = File.createTempFile("TEMP_PHOTO", "AAF").apply { deleteOnExit() }
                val inputStream = context.contentResolver.openInputStream(uri)
                val outputStream = FileOutputStream(tempFile)
                IOUtils.copy(inputStream, outputStream)
                IOUtils.closeQuietly(inputStream)
                IOUtils.closeQuietly(outputStream)

                val compressedFile = Compressor(context).setQuality(70).compressToFile(tempFile)
                FileUtils.copyFile(compressedFile, File(photoPath))
                result = true
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return result
        }
    }
}

enum class CALCULATION {
    CEIL, ROUND, FLOOR
}