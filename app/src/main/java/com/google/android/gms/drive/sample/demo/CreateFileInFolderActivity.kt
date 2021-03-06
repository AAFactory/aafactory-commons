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
import android.os.Environment
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.android.gms.drive.*
import com.google.android.gms.drive.query.Filters
import com.google.android.gms.drive.query.Query
import com.google.android.gms.drive.query.SearchableField
import io.github.aafactory.sample.R
import org.apache.commons.io.FileUtils
import permissions.dispatcher.*
import java.io.File

/**
 * An activity to create a file inside a folder.
 */
@RuntimePermissions
class CreateFileInFolderActivity : BaseDriveActivity() {
    private lateinit var driveId: DriveId
    private lateinit var notificationBuilder: NotificationCompat.Builder
    private lateinit var notificationManager: NotificationManager
    private var totalCount: Int = 0
    private var currentCount: Int = 0
    private var uploadedFilenames = arrayListOf<String>()
    private var newFiles = arrayListOf<File>()
    
    override fun onDriveClientReady() {
        pickFolder()
    }

    override fun addListener() {
        mTask?.let {
            it.addOnSuccessListener(this) { driveId ->
                this.driveId = driveId
                readExternalStorageWithPermissionCheck()
            }.addOnFailureListener(this) { e ->
                Log.e(TAG, "No folder selected", e)
                showMessage(getString(R.string.folder_not_selected))
                finish()
            }
        }
    }

    override fun showDialog() {}

    private fun createFileInFolder() {
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
        
        notificationBuilder = NotificationCompat.Builder(applicationContext, channelId)
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(android.R.drawable.ic_input_get)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_round))
                .setPriority(Notification.PRIORITY_MAX) // this is deprecated in API 26 but you can still use for below 26. check below update for 26 API
                .setContentText("Uploading files to Google Drive.")
                .setOnlyAlertOnce(true)
        notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notificationBuilder.build())
        
        val photoPath = "${Environment.getExternalStorageDirectory().absolutePath}$AAF_EASY_DIARY_PHOTO_DIRECTORY"    
        File(photoPath).listFiles().map { file ->
            if (!uploadedFilenames.contains(file.name)) {
                newFiles.add(file)
            }
        }

        totalCount = newFiles.size
        if (totalCount == 0) finish()
        newFiles.map {
            uploadDiaryPhoto(it)
        }
    }
    
    private fun uploadDiaryPhoto(file: File) {
        driveResourceClient?.let {
            it.createContents()
                    .continueWithTask<DriveFile> { task ->
                        val contents = task.result
                        val outputStream = contents.outputStream
                        FileUtils.copyFile(file, outputStream)
//            OutputStreamWriter(outputStream).use { writer -> writer.write("Hello World!") }
                        val changeSet = MetadataChangeSet.Builder()
                            .setTitle(file.name)
                            .setMimeType(AAF_EASY_DIARY_PHOTO)
                            .setStarred(true)
                            .build()
                    it.createFile(driveId.asDriveFolder(), changeSet, contents)
                    }
                    .addOnSuccessListener(this) { driveFile ->
                    //            showMessage(getString(R.string.file_created, driveFile.getDriveId().encodeToString()))
                        notificationBuilder.setContentTitle("${++currentCount}/$totalCount")
                        notificationBuilder.setProgress(totalCount, currentCount, false)
                        notificationManager.notify(1, notificationBuilder.build())
                        if (currentCount == totalCount) finish()
                    }
                    .addOnFailureListener(this) { e ->
                        Log.e(TAG, "Unable to create file", e)
                        showMessage(getString(R.string.file_create_error))
                    }
        }
    }

    private fun listFilesInFolder() {
        val query = Query.Builder()
                .addFilter(Filters.eq(SearchableField.MIME_TYPE, AAF_EASY_DIARY_PHOTO))
                .build()
        val queryTask = driveResourceClient?.queryChildren(driveId.asDriveFolder(), query)
        queryTask?.let { 
            it.addOnSuccessListener { metadataBuffer ->
                metadataBuffer.forEachIndexed { index, metadata ->
                    metadata?.let {
                        uploadedFilenames.add(it.title)
                    }
                }
                createFileInFolder()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated method
        onRequestPermissionsResult(requestCode, grantResults)
    }
    
    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun readExternalStorage() {
        listFilesInFolder()
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun readExternalStorageDenied() {

    }

    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun showRationaleForReadExternalStorage(request: PermissionRequest) {

    }

    @OnNeverAskAgain(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun readExternalStorageAskAgain() {

    }
    
    companion object {
        private val TAG = "CreateFileInFolderActivity"
    }
}