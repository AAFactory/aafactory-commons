package io.github.aafactory.commons.resolver

import android.app.Activity
import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by CHO HANJOONG on 2018-08-19.
 */
class MMSResolver {
    
    companion object {
        fun getMMSList(activity: Activity, callback: (msg: String) -> Unit): ArrayList<MMSDto> {
            var listOfSMSDto: ArrayList<MMSDto> = arrayListOf()
            val projection = arrayOf("*")
            val uri = Uri.parse("content://mms")
            val query = activity.contentResolver.query(uri, projection, null, null, null)
            if (query.moveToFirst()) {
                do {
                    val selectionPart = "mid = '${query.getString(0)}'"
                    val curPart = activity.contentResolver.query(Uri.parse("content://mms/part"), null, selectionPart, null, null)
                    Log.i("selectionPart", selectionPart + ", " + query.position + ", " + query.count)
                    val msg = "${query.position} / ${query.count}"
                    callback(msg)
//                    activity.runOnUiThread(Runnable { workProgress.setText(msg) })
                    while (curPart.moveToNext()) {
                        var body: String? = null
                        if (curPart.getString(3) == "image/jpeg") {
                            // implementation of this method is below
                            //                        bitmap = getMmsImage(curPart.getString(0));
                        } else if ("text/plain" == curPart.getString(3)) {
                            val data = curPart.getString(curPart.getColumnIndex("_data"))
                            if (data != null) {
                                // implementation of this method below
                                body = getMmsText(activity.contentResolver, curPart.getString(0))
                            } else {
                                body = curPart.getString(curPart.getColumnIndex("text"))
                            }
                        }
                        if (body != null) {
                            //                        Log.i("selectionPart", body);
                            val smsDto = MMSDto(query.getString(0))
                            smsDto.body = body
                            listOfSMSDto.add(smsDto)
                        }
                    }
                    curPart.close()
                } while (query.moveToNext())
            }
            return listOfSMSDto
        }

        private fun getMmsText(contentResolver: ContentResolver, id: String): String {
            val partURI = Uri.parse("content://mms/part/" + id)
            var `is`: InputStream? = null
            val sb = StringBuilder()
            try {
                `is` = contentResolver.openInputStream(partURI)
                if (`is` != null) {
                    val isr = InputStreamReader(`is`, "UTF-8")
                    val reader = BufferedReader(isr)
                    var temp: String? = reader.readLine()
                    while (temp != null) {
                        sb.append(temp)
                        temp = reader.readLine()
                    }
                }
            } catch (e: IOException) {
            } finally {
                if (`is` != null) {
                    try {
                        `is`.close()
                    } catch (e: IOException) {
                    }

                }
            }
            return sb.toString()
        }
    }

    data class MMSDto(
        var messageId: String?
    ) {
        private val formatter = SimpleDateFormat("MM/dd HH:mm")
        var threadId: Long = -1
        var address: String? = null
        var contactId: Long = -1
        var timestamp: Long = -1
        var body: String? = null
        var protocol: Long = -1
        var read: Long = -1
        var status: Long = -1
        var type: Long = -1
        var replyPathPresent: String? = null
        var subject: String? = null
        var serviceCenter: String? = null
        var locked: Long= -1
        var errorCode: Long= -1
        var seen: Long = -1
        fun getTimestampString(): String {
            return formatter.format(Date(timestamp))
        }
    }
}