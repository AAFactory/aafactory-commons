package io.github.aafactory.sample.activities

import android.os.Bundle
import io.github.aafactory.commons.R
import io.github.aafactory.commons.activities.BaseSimpleActivity
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

        intent.getStringExtra(TARGET_URL)?.let { mBinding.webSignaturePad.loadUrl(it) }
    }

    companion object {
        const val TARGET_URL = "target_url"
    }
}