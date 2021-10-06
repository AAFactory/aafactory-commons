package io.github.aafactory.sample.activities

import android.os.Bundle
import android.view.MenuItem
import io.github.aafactory.commons.activities.BaseSimpleActivity
import io.github.aafactory.sample.R
import kotlinx.android.synthetic.main.activity_dev.*

class DevActivity : BaseSimpleActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dev)
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            title = ""
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }
}