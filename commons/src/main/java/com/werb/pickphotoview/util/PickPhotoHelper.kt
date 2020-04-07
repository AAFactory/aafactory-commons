package com.werb.pickphotoview.util

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.werb.eventbus.EventBus
import com.werb.pickphotoview.event.PickFinishEvent
import com.werb.pickphotoview.model.DirImage
import com.werb.pickphotoview.model.GroupImage
import com.werb.pickphotoview.util.PickConfig.URI_STRING_SUFFIX
import java.io.File
import java.util.*


/**
 * Created by wanbo on 2016/12/31.
 */

object PickPhotoHelper {

    val selectImages: MutableList<String> by lazy { mutableListOf<String>() }
    private val mGroupMap = LinkedHashMap<String, ArrayList<String>>()
    private val dirNames = ArrayList<String>()
    var groupImage: GroupImage? = null
        private set
    var dirImage: DirImage? = null
        private set

    fun start(showGif: Boolean, resolver: ContentResolver) {
        clear()
        imageThread(showGif, resolver).start()
        Log.d("PickPhotoView", "PickPhotoHelper start")
    }

    fun stop() {
        clear()
        Log.d("PickPhotoView", "PickPhotoHelper stop")
    }

    private fun clear() {
        selectImages.clear()
        dirNames.clear()
        mGroupMap.clear()
        groupImage = null
        dirImage = null
    }

    private fun imageThread(showGif: Boolean, resolver: ContentResolver): Thread {
        return Thread(Runnable {
            val mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

            //jpeg & png & gif & video
            val mCursor: Cursor?
            mCursor = if (showGif) {
                resolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        arrayOf("image/jpeg", "image/png", "image/x-ms-bmp", "image/gif"), MediaStore.Images.Media.DATE_MODIFIED + " desc")
            } else {
                resolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        arrayOf("image/jpeg", "image/png", "image/x-ms-bmp"), MediaStore.Images.Media.DATE_MODIFIED + " desc")
            }

            if (mCursor == null) {
                return@Runnable
            }
            while (mCursor.moveToNext()) {
                // get image path
                val path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA))
                val uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, mCursor.getLong(mCursor.getColumnIndex(MediaStore.Images.Media._ID)).toString())
                val file = File(path)
                if (!file.exists()) {
                    continue
                }

                // get image parent name
                val parentName = File(path).parentFile.name
//                    Log.d(PickConfig.TAG, parentName + ":" + path)
                // save all Photo
                if (!mGroupMap.containsKey(PickConfig.ALL_PHOTOS)) {
                    dirNames.add(PickConfig.ALL_PHOTOS)
                    mGroupMap[PickConfig.ALL_PHOTOS] = arrayListOf(path)
                    mGroupMap[PickConfig.ALL_PHOTOS + URI_STRING_SUFFIX] = arrayListOf(uri.toString())
                } else {
                    mGroupMap[PickConfig.ALL_PHOTOS]?.add(path)
                    mGroupMap[PickConfig.ALL_PHOTOS + URI_STRING_SUFFIX]?.add(uri.toString())
                }
                // save by parent name
                if (!mGroupMap.containsKey(parentName)) {
                    dirNames.add(parentName)
                    mGroupMap[parentName] = arrayListOf(path)
                    mGroupMap[parentName + URI_STRING_SUFFIX] = arrayListOf(uri.toString())
                } else {
                    mGroupMap[parentName]?.add(path)
                    mGroupMap[parentName + URI_STRING_SUFFIX]?.add(uri.toString())
                }
            }
            mCursor.close()
            val groupImage = GroupImage()
            groupImage.mGroupMap = mGroupMap
            val dirImage = DirImage(dirNames)
            this.groupImage = groupImage
            this.dirImage = dirImage
            EventBus.post(PickFinishEvent())
        })
    }
}
