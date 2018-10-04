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

import android.content.Intent
import android.content.IntentSender
import android.util.Log
import com.google.android.gms.drive.*
import io.github.aafactory.sample.R
import java.io.OutputStreamWriter

/**
 * An activity that illustrates how to use the creator
 * intent to create a new file. The creator intent allows the user
 * to select the parent folder and the title of the newly
 * created file.
 */
class CreateFileWithCreatorActivity : BaseDriveActivity() {

    override fun onDriveClientReady() {
        createFileWithIntent()
    }

    private fun createFileWithIntent() {
        // [START drive_android_create_file_with_intent]
        val createContentsTask = driveResourceClient?.createContents()
        createContentsTask?.let {
            it.continueWithTask { task ->
                val contents = task.result
                val outputStream = contents.outputStream
                OutputStreamWriter(outputStream).use { writer -> writer.write("Hello World!") }

                val changeSet = MetadataChangeSet.Builder()
                        .setTitle("New file")
                        .setMimeType("text/plain")
                        .setStarred(true)
                        .build()

                val createOptions = CreateFileActivityOptions.Builder()
                        .setInitialDriveContents(contents)
                        .setInitialMetadata(changeSet)
                        .build()
            driveClient?.newCreateFileActivityIntentSender(createOptions)
            }.addOnSuccessListener(this) { intentSender ->
            
                try {
                    startIntentSenderForResult(
                            intentSender, REQUEST_CODE_CREATE_FILE, null, 0, 0, 0)
                } catch (e: IntentSender.SendIntentException) {
                    Log.e(TAG, "Unable to create file", e)
                    showMessage(getString(R.string.file_create_error))
                    finish()
                }
            }.addOnFailureListener(this) { e ->
                Log.e(TAG, "Unable to create file", e)
                showMessage(getString(R.string.file_create_error))
                finish()
            }
        }
        // [END drive_android_create_file_with_intent]
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_CREATE_FILE) {
            if (resultCode != RESULT_OK) {
                Log.e(TAG, "Unable to create file")
                showMessage(getString(R.string.file_create_error))
            } else {
                data?.let {
                    val driveId = it.getParcelableExtra<DriveId>(OpenFileActivityOptions.EXTRA_RESPONSE_DRIVE_ID)
                    showMessage(getString(R.string.file_created, "File created with ID: $driveId"))
                }
            }
            finish()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private const val TAG = "CreateFileWithCreator"
        private const val REQUEST_CODE_CREATE_FILE = 2
    }
}
