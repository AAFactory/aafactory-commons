package org.openlayers

import android.os.Bundle
import io.github.aafactory.commons.activities.BaseSimpleActivity
import io.github.aafactory.sample.R
import kotlinx.android.synthetic.main.activity_openlayers.*

/**
 * Created by CHO HANJOONG on 2018-07-04.
 */
class OpenlayersActivity : BaseSimpleActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_openlayers)
        val webSettings = webView.getSettings()
        webSettings.javaScriptEnabled = true
        webSettings.allowFileAccess = true
        webSettings.allowFileAccessFromFileURLs = true
        webSettings.allowUniversalAccessFromFileURLs = true
        webView.loadUrl("file:///android_asset/examples/vector-layer.html")

        finish.setOnClickListener { finish() }
        btn1.setOnClickListener {
            webView.loadUrl("javascript:toggleRoadLayer();");
        }
    }
}