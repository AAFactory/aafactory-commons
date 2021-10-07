package io.github.aafactory.sample.activities

import android.os.Bundle
import android.view.MenuItem
import io.github.aafactory.commons.activities.BaseSimpleActivity
import io.github.aafactory.commons.extensions.determineNextAlarm
import io.github.aafactory.commons.extensions.triggerRestart
import io.github.aafactory.sample.MainActivity
import io.github.aafactory.sample.databinding.ActivityDevBinding

class DevActivity : BaseSimpleActivity() {
    private lateinit var mActivityDevBinding: ActivityDevBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityDevBinding = ActivityDevBinding.inflate(layoutInflater)
        setContentView(mActivityDevBinding.root)
        setSupportActionBar(mActivityDevBinding.toolbar)
        supportActionBar?.run {
            title = ""
            setDisplayHomeAsUpEnabled(true)
        }

        mActivityDevBinding.run {
            buttonDetermineNextAlarm.setOnClickListener { determineNextAlarm() }
            buttonRestartApp.setOnClickListener { triggerRestart(MainActivity::class.java) }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }
}