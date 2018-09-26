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

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.ListView
import com.google.android.gms.drive.*
import com.google.android.gms.drive.events.OpenFileCallback

import com.google.android.gms.drive.query.Filters
import com.google.android.gms.drive.query.Query
import com.google.android.gms.drive.query.SearchableField
import com.google.android.gms.drive.widget.DataBufferAdapter
import com.google.android.gms.tasks.Task

import io.github.aafactory.sample.R
import org.apache.commons.io.FileUtils
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader

/**
 * An activity that illustrates how to query files in a folder.
 */
class QueryFilesInFolderActivity : BaseDemoActivity() {

    private var mResultsAdapter: DataBufferAdapter<Metadata>? = null

    override fun onCreate(b: Bundle?) {
        super.onCreate(b)
        setContentView(R.layout.drive_activity_listfiles)
        val mListView = findViewById<ListView>(R.id.listViewResults)
        mResultsAdapter = ResultsAdapter(this)
        mListView.adapter = mResultsAdapter
    }

    override fun onDriveClientReady() {
        pickFolder()
                .addOnSuccessListener(this) { driveId -> listFilesInFolder(driveId.asDriveFolder()) }
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
        val query = Query.Builder()
                .addFilter(Filters.eq(SearchableField.MIME_TYPE, AAF_EASY_DIARY_PHOTO))
                .build()
        // [START drive_android_query_children]
        val queryTask = driveResourceClient.queryChildren(folder, query)
        // END drive_android_query_children]
        queryTask
                .addOnSuccessListener(this) { metadataBuffer ->
                    mResultsAdapter?.append(metadataBuffer)
                    metadataBuffer.forEachIndexed { index, metadata ->
                        metadata?.let {
                            Log.i(TAG, it.title)
                            val photoPath = "${Environment.getExternalStorageDirectory().absolutePath}$AAF_EASY_DIARY_PHOTO_DIRECTORY"
                            retrieveContents(it.driveId.asDriveFile(), "$photoPath${it.title}")
                        }
                    }
                }
                .addOnFailureListener(this) { e ->
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

        driveResourceClient.openFile(file, DriveFile.MODE_READ_ONLY, openCallback)
        // [END drive_android_read_with_progress_listener]
    }
    
    companion object {
        private val TAG = "QueryFilesInFolder"
    }
}
