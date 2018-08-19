package io.github.aafactory.resolver

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import io.github.aafactory.commons.activities.BaseSimpleActivity
import io.github.aafactory.commons.resolver.MMSResolver
import io.github.aafactory.sample.R
import kotlinx.android.synthetic.main.aaf_activity_mms.*
import permissions.dispatcher.*
import java.util.*

/**
 * Created by CHO HANJOONG on 2018-08-19.
 */

@RuntimePermissions
class MMSActivity: BaseSimpleActivity() {

    lateinit var adapter: MMSArrayAdapter
    var listOfSMSDto = ArrayList<MMSResolver.MMSDto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.aaf_activity_mms)
        adapter = MMSArrayAdapter(this, R.layout.aaf_item_mms, listOfSMSDto)
        listView.adapter = adapter
        
        showReadSMSWithPermissionCheck()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated method
        onRequestPermissionsResult(requestCode, grantResults)
    }

    @NeedsPermission(Manifest.permission.READ_SMS)
    fun showReadSMS() {
        Thread(Runnable {
            listOfSMSDto.addAll(MMSResolver.getMMSList(this, { msg -> runOnUiThread { workProgress.text = msg } }))
            runOnUiThread { adapter.notifyDataSetChanged() }
        }).start()
    }

    @OnPermissionDenied(Manifest.permission.READ_SMS)
    fun readSMSDenied() {
        Toast.makeText(this, "readSMSDenied", Toast.LENGTH_SHORT).show()
    }

    @OnShowRationale(Manifest.permission.READ_SMS)
    fun showRationaleForReadSMS(request: PermissionRequest) {
        // NOTE: Show a rationale to explain why the permission is needed, e.g. with a dialog.
        // Call proceed() or cancel() on the provided PermissionRequest to continue or abort
//        showRationaleDialog(R.string.permission_contacts_rationale, request)
    }

    @OnNeverAskAgain(Manifest.permission.READ_SMS)
    fun readSMSNeverAskAgain() {
        Toast.makeText(this, "readSMSNeverAskAgain", Toast.LENGTH_SHORT).show()
    }
}   