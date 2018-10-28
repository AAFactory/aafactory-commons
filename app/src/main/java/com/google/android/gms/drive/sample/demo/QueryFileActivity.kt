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
import android.app.NotificationManager
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.widget.ListView
import com.google.android.gms.drive.*
import com.google.android.gms.drive.events.OpenFileCallback
import com.google.android.gms.drive.widget.DataBufferAdapter
import io.github.aafactory.sample.R
import org.apache.commons.io.IOUtils
import permissions.dispatcher.*
import java.io.IOException


/**
 * An activity that illustrates how to query files in a folder.
 */
@RuntimePermissions
class QueryFileActivity : BaseDriveActivity() {

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
        val openOptions = OpenFileActivityOptions.Builder()
                .setActivityTitle(getString(io.github.aafactory.commons.R.string.select_file))
                .build()
        pickItem(openOptions)
    }

    override fun addListener() {
        mTask?.let {
            it.addOnSuccessListener(this) { driveId ->
                this.driveId = driveId
                writeExternalStorageWithPermissionCheck()
            }.addOnFailureListener(this) { e ->
                Log.e(TAG, "No folder selected", e)
                showMessage(getString(R.string.folder_not_selected))
                finish()
            }
        }
    }
    
    /**
     * Clears the result buffer to avoid memory leaks as soon
     * as the activity is no longer visible by the user.
     */
    override fun onStop() {
        super.onStop()
        mResultsAdapter?.clear()
    }

    private fun retrieveContents(file: DriveFile) {
        // [START drive_android_read_with_progress_listener]
        val openCallback = object : OpenFileCallback() {
            override fun onProgress(bytesDownloaded: Long, bytesExpected: Long) {}

            override fun onContents(driveContents: DriveContents) {
                // [START_EXCLUDE]
                try {
                    val listOfLine = IOUtils.readLines(driveContents.inputStream, "UTF-8")
                    showMessage(listOfLine[0])
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
        retrieveContents(driveId.asDriveFile())
        
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
