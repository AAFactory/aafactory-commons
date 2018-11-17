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

import com.google.android.gms.drive.BaseDriveActivity
import com.google.android.gms.drive.CreateFileActivityOptions
import com.google.android.gms.drive.MetadataChangeSet
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

    override fun addListener() {
        mTask?.let {
            it.addOnSuccessListener(this) { _ ->
            }.addOnFailureListener(this) { _ ->
            }
        }
    }

    override fun showDialog() {}
    
    private fun createFileWithIntent() {
        // [START drive_android_create_file_with_intent]
        driveResourceClient?.createContents()?.continueWith { task ->
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
            pickUploadFolder(createOptions)
        } 
    }

    companion object {
        private const val TAG = "CreateFileWithCreator"
    }
}
