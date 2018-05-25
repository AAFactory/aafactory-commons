package com.example.android.constraintlayoutexamples

import android.content.Intent
import android.os.Bundle
import android.view.View
import io.github.aafactory.sample.R
import io.github.aafactory.sample.helpers.SampleActivity
import kotlinx.android.synthetic.main.content_toolbar.*

class MainActivity : SampleActivity() {
    private var mShowingLayout = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.constraint_activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
        }
    }

    fun show(v: View) {
        val tag = v.tag as String
        val id = resources.getIdentifier(tag, "layout", packageName)
        setContentView(id)
        mShowingLayout = true
    }

    override fun onBackPressed() {
        if (mShowingLayout) {
            setContentView(R.layout.constraint_activity_main)
            mShowingLayout = false
        } else {
            super.onBackPressed()
        }
    }

    fun showConstraintSetExample(view: View) {
        startActivity(Intent(this, ConstraintSetExampleActivity::class.java))
    }
}
