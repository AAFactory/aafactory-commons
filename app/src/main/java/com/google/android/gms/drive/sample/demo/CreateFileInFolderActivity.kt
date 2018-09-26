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
import android.os.Environment
import android.util.Log
import com.google.android.gms.drive.*
import io.github.aafactory.sample.R
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import permissions.dispatcher.*
import java.io.File
import java.io.OutputStreamWriter

/**
 * An activity to create a file inside a folder.
 */
@RuntimePermissions
class CreateFileInFolderActivity : BaseDemoActivity() {
    lateinit var driveId: DriveId
    
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
        val photoPath = "${Environment.getExternalStorageDirectory().absolutePath}$AAF_EASY_DIARY_PHOTO_DIRECTORY"    
        File(photoPath).listFiles().map { file ->
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
            showMessage(getString(R.string.file_created,
                    driveFile.getDriveId().encodeToString()))
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