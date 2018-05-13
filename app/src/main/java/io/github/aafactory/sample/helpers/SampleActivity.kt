package io.github.aafactory.sample.helpers

import android.support.v7.app.AppCompatActivity
import android.view.MenuItem

/**
 * Created by CHO HANJOONG on 2018-05-13.
 */
open class SampleActivity : AppCompatActivity() {

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}