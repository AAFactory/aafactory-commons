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

import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import com.google.android.gms.drive.DriveFile
import com.google.android.gms.drive.MetadataChangeSet
import com.google.android.gms.tasks.Tasks
import io.github.aafactory.commons.extensions.showSnackBar
import io.github.aafactory.sample.R
import java.io.OutputStreamWriter

/**
 * An activity to illustrate how to create a file.
 */
class CreateFileActivity : BaseDemoActivity() {

    override fun onDriveClientReady() {
        createFile()
    }

    private fun createFile() {
        // [START drive_android_create_file]
        val rootFolderTask = driveResourceClient.rootFolder
        val createContentsTask = driveResourceClient.createContents()
        Tasks.whenAll(rootFolderTask, createContentsTask)
                .continueWithTask<DriveFile> { task ->
                    val parent = rootFolderTask.result
                    val contents = createContentsTask.result
                    val outputStream = contents.outputStream
                    OutputStreamWriter(outputStream).use { writer -> writer.write("Hello World!") }

                    val changeSet = MetadataChangeSet.Builder()
                            .setTitle("AAF_TEST")
                            .setMimeType("text/plain")
                            .setStarred(true)
                            .build()

                    driveResourceClient.createFile(parent, changeSet, contents)
                }
                .addOnSuccessListener(this) { driveFile ->
//                    showMessage(getString(R.string.file_created, driveFile.getDriveId().encodeToString()))
                    findViewById<View>(android.R.id.content).showSnackBar(driveFile.getDriveId().encodeToString(), Snackbar.LENGTH_LONG)
//                    finish()
                }
                .addOnFailureListener(this) { e ->
                    Log.e(TAG, "Unable to create file", e)
                    showMessage(getString(R.string.file_create_error))
                    finish()
                }
        // [END drive_android_create_file]
    }

    companion object {
        private val TAG = "CreateFileActivity"
    }
}
