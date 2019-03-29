package org.openlayers

import android.Manifest
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.webkit.WebChromeClient
import android.widget.Toast
import com.example.android.architecture.blueprints.todoapp.mvp.util.showSnackBar
import io.github.aafactory.commons.activities.BaseSimpleActivity
import io.github.aafactory.sample.R
import kotlinx.android.synthetic.main.activity_openlayers.*
import permissions.dispatcher.*
import android.webkit.GeolocationPermissions



/**
 * Created by CHO HANJOONG on 2018-07-04.
 */
@RuntimePermissions
class OpenlayersActivity : BaseSimpleActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_openlayers)
        val webSettings = webView.settings
        webSettings.setGeolocationEnabled(true)
        webSettings.javaScriptEnabled = true
        webSettings.allowFileAccess = true
        webSettings.allowFileAccessFromFileURLs = true
        webSettings.allowUniversalAccessFromFileURLs = true
        
        webView.webChromeClient = object: WebChromeClient() {
            override fun onGeolocationPermissionsShowPrompt(origin: String, callback: GeolocationPermissions.Callback) {
                callback.invoke(origin, true, false)
            }
        }
        
        finish.setOnClickListener { finish() }
        accessFineLocationWithPermissionCheck()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated method
        onRequestPermissionsResult(requestCode, grantResults)
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun accessFineLocation() {
//        webView.loadUrl("http://172.30.1.24:9000/examples/main.html")
        webView.loadUrl("file:///android_asset/examples/main.html")
        webView.showSnackBar("accessFineLocation", Snackbar.LENGTH_SHORT)
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    fun accessFineLocationDenied() {
        Toast.makeText(this, "accessFineLocationDenied", Toast.LENGTH_SHORT).show()
    }

    @OnShowRationale(Manifest.permission.ACCESS_FINE_LOCATION)
    fun showRationaleForAccessFineLocation(request: PermissionRequest) {
        // NOTE: Show a rationale to explain why the permission is needed, e.g. with a dialog.
        // Call proceed() or cancel() on the provided PermissionRequest to continue or abort
//        showRationaleDialog(R.string.permission_contacts_rationale, request)
    }

    @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION)
    fun accessFineLocationAskAgain() {
        Toast.makeText(this, "accessFineLocationAskAgain", Toast.LENGTH_SHORT).show()
    }
}