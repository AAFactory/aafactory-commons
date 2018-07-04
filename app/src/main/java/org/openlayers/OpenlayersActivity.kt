package org.openlayers

import android.os.Bundle
import android.webkit.WebView
import io.github.aafactory.commons.activities.BaseSimpleActivity
import io.github.aafactory.sample.R

/**
 * Created by CHO HANJOONG on 2018-07-04.
 */
class OpenlayersActivity : BaseSimpleActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        var webView: WebView = findViewById<WebView>(R.id.webView)
        val webSettings = webView.getSettings()
        webSettings.javaScriptEnabled = true
        webSettings.allowFileAccess = true
        webSettings.allowFileAccessFromFileURLs = true
        webSettings.allowUniversalAccessFromFileURLs = true
        webView.loadUrl("file:///android_asset/examples/vector-layer.html")

        findViewById<WebView>(R.id.finish).setOnClickListener { finish() }
    }
}