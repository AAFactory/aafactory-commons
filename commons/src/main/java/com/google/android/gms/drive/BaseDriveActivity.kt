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
package com.google.android.gms.drive

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.android.gms.drive.query.Filters
import com.google.android.gms.drive.query.SearchableField
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import io.github.aafactory.commons.R
import io.github.aafactory.commons.activities.BaseSimpleActivity
import java.util.*

/**
 * An abstract activity that handles authorization and connection to the Drive services.
 */
abstract class BaseDriveActivity : BaseSimpleActivity() {

    /**
     * Handles high-level drive functions like sync
     */
    protected var driveClient: DriveClient? = null
        private set

    /**
     * Handle access to Drive resources/files.
     */
    protected var driveResourceClient: DriveResourceClient? = null
        private set

    /**
     * Tracks completion of the drive picker
     */
    private var mOpenItemTaskSource: TaskCompletionSource<DriveId>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_drive)
    }
    
    override fun onStart() {
        super.onStart()
        signIn()
    }

    /**
     * Handles resolution callbacks.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_SIGN_IN -> {
                if (resultCode != Activity.RESULT_OK) {
                    // Sign-in may fail or be cancelled by the user. For this sample, sign-in is
                    // required and is fatal. For apps where sign-in is optional, handle
                    // appropriately
                    Log.e(TAG, "Sign-in failed.")
                    finish()
                    return
                }

                val getAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
                if (getAccountTask.isSuccessful) {
                    initializeDriveClient(getAccountTask.result)
                } else {
                    Log.e(TAG, "Sign-in failed.")
                    finish()
                }
            }
            REQUEST_CODE_OPEN_ITEM -> if (resultCode == Activity.RESULT_OK) {
                val driveId = data?.getParcelableExtra<DriveId>(OpenFileActivityOptions.EXTRA_RESPONSE_DRIVE_ID)
                mOpenItemTaskSource?.setResult(driveId)
            } else {
                mOpenItemTaskSource?.setException(RuntimeException("Unable to open file"))
            }
        }
    }

    /**
     * Starts the sign-in process and initializes the Drive client.
     */
    protected fun signIn() {
        val requiredScopes = HashSet<Scope>(2)
        requiredScopes.add(Drive.SCOPE_FILE)
        requiredScopes.add(Drive.SCOPE_APPFOLDER)
        val signInAccount = GoogleSignIn.getLastSignedInAccount(this)
        if (signInAccount != null && signInAccount.grantedScopes.containsAll(requiredScopes)) {
            initializeDriveClient(signInAccount)
        } else {
            val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestScopes(Drive.SCOPE_FILE)
                    .requestScopes(Drive.SCOPE_APPFOLDER)
                    .build()
            val googleSignInClient = GoogleSignIn.getClient(this, signInOptions)
            startActivityForResult(googleSignInClient.signInIntent, REQUEST_CODE_SIGN_IN)
        }
    }

    /**
     * Continues the sign-in process, initializing the Drive clients with the current
     * user's account.
     */
    private fun initializeDriveClient(signInAccount: GoogleSignInAccount) {
        driveClient = Drive.getDriveClient(applicationContext, signInAccount)
        driveResourceClient = Drive.getDriveResourceClient(applicationContext, signInAccount)
        onDriveClientReady()
    }

    /**
     * Prompts the user to select a text file using OpenFileActivity.
     *
     * @return Task that resolves with the selected item's ID.
     */
    protected fun pickTextFile(): Task<DriveId>? {
        val openOptions = OpenFileActivityOptions.Builder()
                .setSelectionFilter(Filters.eq(SearchableField.MIME_TYPE, "text/plain"))
                .setActivityTitle(getString(R.string.select_file))
                .build()
        return pickItem(openOptions)
    }

    /**
     * Prompts the user to select a folder using OpenFileActivity.
     *
     * @return Task that resolves with the selected item's ID.
     */
    protected fun pickFolder(): Task<DriveId>? {
        val openOptions = OpenFileActivityOptions.Builder()
                .setSelectionFilter(Filters.eq(SearchableField.MIME_TYPE, DriveFolder.MIME_TYPE))
                .setActivityTitle(getString(R.string.select_folder))
                .build()
        return pickItem(openOptions)
    }

    /**
     * Prompts the user to select a folder using OpenFileActivity.
     *
     * @param openOptions Filter that should be applied to the selection
     * @return Task that resolves with the selected item's ID.
     */
    protected fun pickItem(openOptions: OpenFileActivityOptions): Task<DriveId>? {
        mOpenItemTaskSource = TaskCompletionSource()
        driveClient?.run {
            newOpenFileActivityIntentSender(openOptions).continueWith { task ->
                startIntentSenderForResult(task.result, REQUEST_CODE_OPEN_ITEM, null, 0, 0, 0)
            }
        }
        return mOpenItemTaskSource?.task
    }

    /**
     * Shows a toast message.
     */
    protected fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    /**
     * Called after the user has signed in and the Drive client has been initialized.
     */
    protected abstract fun onDriveClientReady()

    companion object {
        private const val TAG = "BaseDriveActivity"

        /**
         * Request code for Google Sign-in
         */
        protected const val REQUEST_CODE_SIGN_IN = 0

        /**
         * Request code for the Drive picker
         */
        protected const val REQUEST_CODE_OPEN_ITEM = 1
    }
}
