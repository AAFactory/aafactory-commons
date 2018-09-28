/*
 * Copyright 2013 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.gms.drive.sample.demo

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.widget.ListView
import com.google.android.gms.drive.*
import com.google.android.gms.drive.events.OpenFileCallback
import com.google.android.gms.drive.query.Filters
import com.google.android.gms.drive.query.Query
import com.google.android.gms.drive.query.SearchableField
import com.google.android.gms.drive.widget.DataBufferAdapter
import io.github.aafactory.sample.R
import org.apache.commons.io.FileUtils
import permissions.dispatcher.*
import java.io.File
import java.io.IOException


/**
 * An activity that illustrates how to query files in a folder.
 */
@RuntimePermissions
class QueryFilesInFolderActivity : BaseDriveActivity() {

    private var mResultsAdapter: DataBufferAdapter<Metadata>? = null
    private var totalCount: Int = 0
    private var currentCount: Int = 0
    private lateinit var notificationBuilder: NotificationCompat.Builder
    private lateinit var notificationManager: NotificationManager
    private lateinit var driveId: DriveId
    

    override fun onCreate(b: Bundle?) {
        super.onCreate(b)
        setContentView(R.layout.drive_activity_listfiles)
        val mListView = findViewById<ListView>(R.id.listViewResults)
        mResultsAdapter = ResultsAdapter(this)
        mListView.adapter = mResultsAdapter
    }

    override fun onDriveClientReady() {
        pickFolder()
                .addOnSuccessListener(this) { driveId ->
                    this.driveId = driveId
                    writeExternalStorageWithPermissionCheck()
                }
                .addOnFailureListener(this) { e ->
                    Log.e(TAG, "No folder selected", e)
                    showMessage(getString(R.string.folder_not_selected))
                    finish()
                }
    }

    /**
     * Clears the result buffer to avoid memory leaks as soon
     * as the activity is no longer visible by the user.
     */
    override fun onStop() {
        super.onStop()
        mResultsAdapter!!.clear()
    }

    /**
     * Retrieves results for the next page. For the first run,
     * it retrieves results for the first page.
     */
    private fun listFilesInFolder(folder: DriveFolder) {
        val channelId = "M_CH_ID"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = "CHANNEL_NAME"
            val descriptionText = "CHANNEL_DESCRIPTION"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(channelId, name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
        
        val query = Query.Builder()
                .addFilter(Filters.eq(SearchableField.MIME_TYPE, AAF_EASY_DIARY_PHOTO))
                .build()
        // [START drive_android_query_children]
        val queryTask = driveResourceClient?.queryChildren(folder, query)
        // END drive_android_query_children]
        queryTask
                ?.addOnSuccessListener(this) { metadataBuffer ->
                    
                    notificationBuilder = NotificationCompat.Builder(applicationContext, channelId)
                    notificationBuilder.setAutoCancel(true)
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setWhen(System.currentTimeMillis())
                            .setSmallIcon(android.R.drawable.ic_input_get)
                            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_round))
                            .setTicker("Hearty365")
                            .setPriority(Notification.PRIORITY_MAX) // this is deprecated in API 26 but you can still use for below 26. check below update for 26 API
                            .setContentTitle("Default notification")
                            .setContentText("Downloading stored file from Google Drive.")
                            .setContentInfo("Info")
                            .setOnlyAlertOnce(true)
                    notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.notify(1, notificationBuilder.build())

                    totalCount = metadataBuffer.count
                    currentCount = 0
                    mResultsAdapter?.append(metadataBuffer)
                    metadataBuffer.forEachIndexed { index, metadata ->
                        metadata?.let {
                            Log.i(TAG, it.title)
                            val photoPath = "${Environment.getExternalStorageDirectory().absolutePath}$AAF_EASY_DIARY_PHOTO_DIRECTORY"
                            retrieveContents(it.driveId.asDriveFile(), "$photoPath${it.title}")
                        }
                    }
                }
                ?.addOnFailureListener(this) { e ->
                    Log.e(TAG, "Error retrieving files", e)
                    showMessage(getString(R.string.query_failed))
                    finish()
                }
    }

    private fun retrieveContents(file: DriveFile, destFilePath: String) {
        // [START drive_android_read_with_progress_listener]
        val openCallback = object : OpenFileCallback() {
            override fun onProgress(bytesDownloaded: Long, bytesExpected: Long) {}

            override fun onContents(driveContents: DriveContents) {
                // [START_EXCLUDE]
                try {
                    FileUtils.copyInputStreamToFile(driveContents.inputStream, File(destFilePath))
                    notificationBuilder.setContentTitle("${++currentCount}/$totalCount")
                    notificationBuilder.setProgress(totalCount, currentCount, false)
                    notificationManager.notify(1, notificationBuilder.build())
                    if (currentCount == totalCount) finish()
                } catch (e: IOException) {
                    onError(e)
                }
                // [END_EXCLUDE]
            }

            override fun onError(e: Exception) {
                // Handle error
                // [START_EXCLUDE]
                Log.e(TAG, "Unable to read contents", e)
                showMessage(getString(R.string.read_failed))
                finish()
                // [END_EXCLUDE]
            }
        }

        driveResourceClient?.openFile(file, DriveFile.MODE_READ_ONLY, openCallback)
        // [END drive_android_read_with_progress_listener]
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated method
        onRequestPermissionsResult(requestCode, grantResults)
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun writeExternalStorage() {
        listFilesInFolder(driveId.asDriveFolder())
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun writeExternalStorageDenied() {

    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun showRationaleForWriteExternalStorage(request: PermissionRequest) {

    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun writeExternalStorageAskAgain() {

    }
    
    companion object {
        private val TAG = "QueryFilesInFolder"
    }
}
