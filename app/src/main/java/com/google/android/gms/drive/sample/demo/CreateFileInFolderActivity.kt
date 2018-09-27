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
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Environment
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.android.gms.drive.*
import io.github.aafactory.sample.R
import org.apache.commons.io.FileUtils
import permissions.dispatcher.*
import java.io.File

/**
 * An activity to create a file inside a folder.
 */
@RuntimePermissions
class CreateFileInFolderActivity : BaseDemoActivity() {
    private lateinit var driveId: DriveId
    private lateinit var notificationBuilder: NotificationCompat.Builder
    private lateinit var notificationManager: NotificationManager
    private var totalCount: Int = 0
    private var currentCount: Int = 0
    
    override fun onDriveClientReady() {
        pickFolder()
                .addOnSuccessListener(this) { driveId ->
                    this.driveId = driveId
                    readExternalStorageWithPermissionCheck()
                }
                .addOnFailureListener(this) { e ->
                    Log.e(TAG, "No folder selected", e)
                    showMessage(getString(R.string.folder_not_selected))
                    finish()
                }
    }

    private fun createFileInFolder() {
        notificationBuilder = NotificationCompat.Builder(applicationContext, "M_CH_ID")
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
            totalCount = File(photoPath).listFiles().size 
            uploadDiaryPhoto(file)
        }
    }
    
    private fun uploadDiaryPhoto(file: File) {
        Log.i(TAG, file.absolutePath)
        
        driveResourceClient
        .createContents()
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

            driveResourceClient.createFile(driveId.asDriveFolder(), changeSet, contents)
        }
        .addOnSuccessListener(this
        ) { driveFile ->
//            showMessage(getString(R.string.file_created, driveFile.getDriveId().encodeToString()))
            notificationBuilder.setContentTitle("${++currentCount}/$totalCount")
            notificationManager.notify(1, notificationBuilder.build())
        }
        .addOnFailureListener(this) { e ->
            Log.e(TAG, "Unable to create file", e)
            showMessage(getString(R.string.file_create_error))
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated method
        onRequestPermissionsResult(requestCode, grantResults)
    }
    
    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun readExternalStorage() {
        createFileInFolder()
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