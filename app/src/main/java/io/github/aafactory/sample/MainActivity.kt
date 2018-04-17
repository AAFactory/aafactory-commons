package io.github.aafactory.sample

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import com.tapadoo.alerter.Alerter
import io.github.aafactory.commons.utils.DateUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.run { 
            title = getString(R.string.app_name)
        }
        welcomeMessage.text = DateUtils.getDateStringFromTimeMillis(System.currentTimeMillis())

        bindEvent()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun bindEvent() {
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        btn_keddit_by_steps.setOnClickListener { _ ->
            Alerter.create(this@MainActivity)
                    .setTitle("Keddit")
                    .setText(getString(R.string.keddit_by_steps_description))
                    .setBackgroundColorRes(R.color.colorAccent)
                    .show()
        }

        btn_alerter.setOnClickListener { _ ->
            Alerter.create(this@MainActivity)
                    .setTitle("Alerter")
                    .setText(getString(R.string.alerter_description))
                    .setBackgroundColorRes(R.color.colorAccent)
                    .show()
        }
    }
}
