package io.github.aafactory.resolver

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
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
    companion object {
        const val SMS_LIST = "sms_list"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.aaf_activity_mms)
        adapter = MMSArrayAdapter(this, R.layout.aaf_item_mms, listOfSMSDto)
        listView.adapter = adapter
        savedInstanceState?.getParcelableArrayList<MMSResolver.MMSDto>(SMS_LIST)?.let {
            listOfSMSDto.addAll(it)
        }

        listView.setOnScrollListener(object: AbsListView.OnScrollListener {
            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
            }

            override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                val lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount && progress.visibility == View.GONE) {
                    Log.i("testCode", "onScroll")
                    loadItems()           
                }
            }
        })
        
        showReadSMSWithPermissionCheck()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putParcelableArrayList(SMS_LIST, listOfSMSDto)
        super.onSaveInstanceState(outState)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated method
        onRequestPermissionsResult(requestCode, grantResults)
    }

    fun loadItems() {
        progress.visibility = View.VISIBLE
        Thread(Runnable {
            MMSResolver.getMMSList(this, listOfSMSDto, 100, { msg -> runOnUiThread { workProgress.text = msg } })
            runOnUiThread { 
                adapter.notifyDataSetChanged()
                progress.visibility = View.GONE
            }
        }).start()
    }
    
    @NeedsPermission(Manifest.permission.READ_SMS)
    fun showReadSMS() {
        loadItems()
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