package io.github.aafactory.sample.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.widget.Toast
import io.github.aafactory.commons.activities.BaseSimpleActivity
import io.github.aafactory.commons.extensions.makeToast
import io.github.aafactory.sample.R
import io.github.aafactory.sample.databinding.ActivityWebviewSignaturePadBinding

class WebViewSignaturePadActivity : BaseSimpleActivity() {
    private lateinit var mBinding: ActivityWebviewSignaturePadBinding
    private var mJSMessage: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityWebviewSignaturePadBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setSupportActionBar(mBinding.toolbar)
        supportActionBar?.run {
            title = "Signature Pad HTML5"
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_cross)
        }

        mBinding.webSignaturePad.run {
            settings.setGeolocationEnabled(true)
            settings.javaScriptEnabled = true
            settings.allowFileAccess = true
            settings.allowFileAccessFromFileURLs = true
            settings.allowUniversalAccessFromFileURLs = true
            webChromeClient = WebChromeClient()
            addJavascriptInterface(WebAppInterface(this@WebViewSignaturePadActivity), "nativeInterface")

            intent.getStringExtra(TARGET_URL)?.let { mBinding.webSignaturePad.loadUrl(it) }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_webview_signature_pad, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.executeJSFunction -> {
                mBinding.webSignaturePad.loadUrl("javascript:showAlert('$mJSMessage')");
            }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class WebAppInterface(private val mContext: Context) {

        /** Show a toast from the web page  */
        @JavascriptInterface
        fun showToast(message: String) {
            mJSMessage = message
            Toast.makeText(mContext, mJSMessage, Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        const val TARGET_URL = "target_url"
    }
}