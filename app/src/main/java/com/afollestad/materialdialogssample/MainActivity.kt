package com.afollestad.materialdialogssample

import android.os.Bundle
import io.github.aafactory.sample.R
import io.github.aafactory.sample.helpers.SampleActivity
import kotlinx.android.synthetic.main.bubbleseekbar_activity_main.*

/**
 * Created by CHO HANJOONG on 2018-09-01.
 */
class MainActivity : SampleActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.materialdialog_activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
        }
    }
}