package io.github.aafactory.commons.resolver

import android.app.Activity
import android.content.ContentResolver
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.text.MessageFormat
import java.text.SimpleDateFormat
import java.util.*



/**
 * Created by CHO HANJOONG on 2018-08-19.
 */
class MMSResolver {
    
    companion object {

        /*0 = "_id"
        1 = "thread_id"
        2 = "date"
        3 = "date_sent"
        4 = "msg_box"
        5 = "read"
        6 = "m_id"
        7 = "sub"
        8 = "sub_cs"
        9 = "ct_t"
        10 = "ct_l"
        11 = "exp"
        12 = "m_cls"
        13 = "m_type"
        14 = "v"
        15 = "m_size"
        16 = "pri"
        17 = "rr"
        18 = "rpt_a"
        19 = "resp_st"
        20 = "st"
        21 = "tr_id"
        22 = "retr_st"
        23 = "retr_txt"
        24 = "retr_txt_cs"
        25 = "read_status"
        26 = "ct_cls"
        27 = "resp_txt"
        28 = "d_tm"
        29 = "d_rpt"
        30 = "locked"
        31 = "sub_id"
        32 = "seen"
        33 = "creator"
        34 = "sim_slot"
        35 = "sim_imsi"
        36 = "deletable"
        37 = "hidden"
        38 = "app_id"
        39 = "msg_id"
        40 = "callback_set"
        41 = "reserved"
        42 = "text_only"
        43 = "spam_report"
        44 = "secret_mode"
        45 = "safe_message"
        46 = "favorite"
        47 = "d_rpt_st"
        48 = "rr_st"
        49 = "using_mode"
        50 = "from_address"
        51 = "device_name"

        0 = "_id"
        1 = "mid"
        2 = "seq"
        3 = "ct"
        4 = "name"
        5 = "chset"
        6 = "cd"
        7 = "fn"
        8 = "cid"
        9 = "cl"
        10 = "ctt_s"
        11 = "ctt_t"
        12 = "_data"
        13 = "text*/
        fun getMMSList(activity: Activity, listOfSMSDto: ArrayList<MMSDto>, endIndex: Int = 10, callback: (msg: String) -> Unit): ArrayList<MMSDto> {
            var fetchCount = 0
            val projection = arrayOf("*")
            val uri = Uri.parse("content://mms")
            val query = activity.contentResolver.query(uri, projection, null, null, null)
            if (query.moveToPosition(listOfSMSDto.size)) {
                do {
                    if (fetchCount >= endIndex) break
                    val selectionPart = "mid = '${query.getString(0)}'"
                    val timestamp = query.getLong(query.getColumnIndex("date")) * 1000
                    val curPart = activity.contentResolver.query(Uri.parse("content://mms/part"), null, selectionPart, null, null)
//                    Log.i("selectionPart", selectionPart + ", " + query.position + ", " + query.count)
                    val msg = "${query.position + 1} / ${query.count}"
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
                                body = getMMSText(activity.contentResolver, curPart.getString(0))
                            } else {
                                body = curPart.getString(curPart.getColumnIndex("text"))
                            }
                        }
                        
                        if (body == null) body = "Contents is empty"
                        val mmsDto = MMSDto(query.getString(0))
                        mmsDto.timestamp = timestamp
                        mmsDto.address = getAddressNumber(activity, query.getString(0).toInt())
                        mmsDto.body = body
                        listOfSMSDto.add(mmsDto)
                        fetchCount++
                    }
                    curPart.close()
                } while (query.moveToNext())
            }
            return listOfSMSDto
        }

        private fun getAddressNumber(activity: Activity, id: Int): String? {
            val selectionAdd = "msg_id=$id"
            val uriStr = "content://mms/$id/addr"
            val uriAddress = Uri.parse(uriStr)
            val cursor = activity.contentResolver.query(uriAddress, null,
                    selectionAdd, null, null)
            var name: String? = null
            if (cursor.moveToFirst()) {
                do {
                    val number = cursor.getString(cursor.getColumnIndex("address"))
                    if (number != null) {
                        try {
                            java.lang.Long.parseLong(number.replace("-", ""))
                            name = number
                        } catch (nfe: NumberFormatException) {
                            if (name == null) {
                                name = number
                            }
                        }

                    }
                } while (cursor.moveToNext())
            }
            
            cursor.close()
            return name
        }
        
        private fun getMMSText(contentResolver: ContentResolver, id: String): String {
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

        private fun getSMSList(activity: Activity): ArrayList<MMSDto> {
            val listOfSMSDto = ArrayList<MMSDto>()
            val uri = Uri.parse("content://sms/inbox")
            val cursor = activity.contentResolver.query(
                    uri,
                    arrayOf("_id", "thread_id", "address", "person", "date", "body", "protocol", "read", "status", "type", "reply_path_present", "subject", "service_center", "locked", "error_code", "seen"),
                    null, null,
                    "date DESC"
            )
            val formatter = SimpleDateFormat("MM/dd HH:mm")
            val count = 0

            while (cursor.moveToNext()) {
                val mmsDto = MMSDto(cursor.getString(0))
                mmsDto.threadId = cursor.getLong(1)
                mmsDto.address = cursor.getString(2)
                mmsDto.contactId = cursor.getLong(3)
                mmsDto.timestamp = cursor.getLong(4)
                mmsDto.body = cursor.getString(5)
                mmsDto.protocol = cursor.getLong(6)
                mmsDto.read = cursor.getLong(7)
                mmsDto.status = cursor.getLong(8)
                mmsDto.type = cursor.getLong(9)
                mmsDto.replyPathPresent = cursor.getString(10)
                mmsDto.subject = cursor.getString(11)
                mmsDto.serviceCenter = cursor.getString(12)
                mmsDto.locked = cursor.getLong(13)
                mmsDto.errorCode = cursor.getLong(14)
                mmsDto.seen = cursor.getLong(15)
                listOfSMSDto.add(mmsDto)
            }
            cursor.close()
            
            return listOfSMSDto
        }
    }

    data class MMSDto(
        var messageId: String?
    ) : Parcelable {
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

        constructor(parcel: Parcel) : this(parcel.readString()) {
            threadId = parcel.readLong()
            address = parcel.readString()
            contactId = parcel.readLong()
            timestamp = parcel.readLong()
            body = parcel.readString()
            protocol = parcel.readLong()
            read = parcel.readLong()
            status = parcel.readLong()
            type = parcel.readLong()
            replyPathPresent = parcel.readString()
            subject = parcel.readString()
            serviceCenter = parcel.readString()
            locked = parcel.readLong()
            errorCode = parcel.readLong()
            seen = parcel.readLong()
        }

        fun getTimestampString(): String {
            return formatter.format(Date(timestamp))
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(messageId)
            parcel.writeLong(threadId)
            parcel.writeString(address)
            parcel.writeLong(contactId)
            parcel.writeLong(timestamp)
            parcel.writeString(body)
            parcel.writeLong(protocol)
            parcel.writeLong(read)
            parcel.writeLong(status)
            parcel.writeLong(type)
            parcel.writeString(replyPathPresent)
            parcel.writeString(subject)
            parcel.writeString(serviceCenter)
            parcel.writeLong(locked)
            parcel.writeLong(errorCode)
            parcel.writeLong(seen)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<MMSDto> {
            override fun createFromParcel(parcel: Parcel): MMSDto {
                return MMSDto(parcel)
            }

            override fun newArray(size: Int): Array<MMSDto?> {
                return arrayOfNulls(size)
            }
        }
    }
}