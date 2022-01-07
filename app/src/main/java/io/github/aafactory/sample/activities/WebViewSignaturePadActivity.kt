package io.github.aafactory.sample.activities

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import io.github.aafactory.commons.R
import io.github.aafactory.commons.activities.BaseSimpleActivity
import io.github.aafactory.sample.databinding.ActivityDevBinding
import io.github.aafactory.sample.databinding.ActivityWebviewSignaturePadBinding

class WebViewSignaturePadActivity : BaseSimpleActivity() {
    private lateinit var mBinding: ActivityWebviewSignaturePadBinding
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

        val webSettings = mBinding.webSignaturePad.settings
        webSettings.setGeolocationEnabled(true)
        webSettings.javaScriptEnabled = true
        webSettings.allowFileAccess = true
        webSettings.allowFileAccessFromFileURLs = true
        webSettings.allowUniversalAccessFromFileURLs = true

        mBinding.webSignaturePad.loadUrl("file:///android_asset/signature-pad/signature-pad.html")
    }
}