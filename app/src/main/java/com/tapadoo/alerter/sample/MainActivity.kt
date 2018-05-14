package com.tapadoo.alerter.sample

import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.tapadoo.alerter.Alerter
import io.github.aafactory.sample.R
import kotlinx.android.synthetic.main.alerter_activity_main.*
import kotlinx.android.synthetic.main.alerter_content_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alerter_activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)  
        }

        btnAlertDefault.setOnClickListener(this)
        btnAlertColoured.setOnClickListener(this)
        btnAlertCustomIcon.setOnClickListener(this)
        btnAlertTextOnly.setOnClickListener(this)
        btnAlertOnClick.setOnClickListener(this)
        btnAlertVerbose.setOnClickListener(this)
        btnAlertCallback.setOnClickListener(this)
        btnAlertInfiniteDuration.setOnClickListener(this)
        btnAlertWithProgress.setOnClickListener(this)
        btnAlertWithCustomFont.setOnClickListener(this)
        btnAlertSwipeToDismissEnabled.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        window.setBackgroundDrawableResource(android.R.color.white)
    }

    override fun onClick(view: View) {
        val i = 
        when (view.id) {
            R.id.btnAlertColoured -> showAlertColoured()
            R.id.btnAlertCustomIcon -> showAlertWithIcon()
            R.id.btnAlertTextOnly -> showAlertTextOnly()
            R.id.btnAlertOnClick -> showAlertWithOnClick()
            R.id.btnAlertVerbose-> showAlertVerbose()
            R.id.btnAlertCallback -> showAlertCallbacks()
            R.id.btnAlertInfiniteDuration -> showAlertInfiniteDuration()
            R.id.btnAlertWithProgress -> showAlertWithProgress()
            R.id.btnAlertWithCustomFont -> showAlertWithCustomFont()
            R.id.btnAlertSwipeToDismissEnabled -> showAlertSwipeToDismissEnabled() 
            else -> showAlertDefault()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDefault() {
        Alerter.create(this@MainActivity)
                .setTitle(R.string.title_activity_example)
                .setText("Alert text...")
                .show()
    }

    private fun showAlertColoured() {
        Alerter.create(this@MainActivity)
                .setTitle("Alert Title")
                .setText("Alert text...")
                .setBackgroundColorRes(R.color.colorAccent)
                .show()
    }

    private fun showAlertWithIcon() {
        Alerter.create(this@MainActivity)
                .setText("Alert text...")
                .setIcon(R.drawable.alerter_ic_mail_outline)
                .setIconColorFilter(0) // Optional - Removes white tint
                .show()
    }

    private fun showAlertTextOnly() {
        Alerter.create(this@MainActivity)
                .setText("Alert text...")
                .show()
    }

    private fun showAlertWithOnClick() {
        Alerter.create(this@MainActivity)
                .setTitle("Alert Title")
                .setText("Alert text...")
                .setDuration(10000)
                .setOnClickListener { Toast.makeText(this@MainActivity, "OnClick Called", Toast.LENGTH_LONG).show() }
                .show()
    }

    private fun showAlertVerbose() {
        Alerter.create(this@MainActivity)
                .setTitle("Alert Title")
                .setText("The alert scales to accommodate larger bodies of text. " +
                        "The alert scales to accommodate larger bodies of text. " +
                        "The alert scales to accommodate larger bodies of text.")
                .show()
    }

    private fun showAlertCallbacks() {
        Alerter.create(this@MainActivity)
                .setTitle("Alert Title")
                .setText("Alert text...")
                .setDuration(10000)
                .setOnShowListener { Toast.makeText(this@MainActivity, "Show Alert", Toast.LENGTH_LONG).show() }
                .setOnHideListener { Toast.makeText(this@MainActivity, "Hide Alert", Toast.LENGTH_LONG).show() }
                .show()
    }

    private fun showAlertInfiniteDuration() {
        Alerter.create(this@MainActivity)
                .setTitle("Alert Title")
                .setText("Alert text...")
                .enableInfiniteDuration(true)
                .show()
    }

    private fun showAlertWithProgress() {
        Alerter.create(this@MainActivity)
                .setTitle("Alert Title")
                .setText("Alert text...")
                .enableProgress(true)
                .setProgressColorRes(R.color.colorPrimary)
                .show()
    }

    private fun showAlertWithCustomFont() {
        Alerter.create(this@MainActivity)
                .setTitle("Alert Title")
                .setTitleAppearance(R.style.AlertTextAppearance_Title)
                .setTitleTypeface(Typeface.createFromAsset(assets, "Pacifico-Regular.ttf"))
                .setText("Alert text...")
                .setTextAppearance(R.style.AlertTextAppearance_Text)
                .setTextTypeface(Typeface.createFromAsset(assets, "ScopeOne-Regular.ttf"))
                .show()
    }

    private fun showAlertSwipeToDismissEnabled() {
        Alerter.create(this@MainActivity)
                .setTitle("Alert Title")
                .setText("Alert text...")
                .enableSwipeToDismiss()
                .setOnHideListener { Toast.makeText(this@MainActivity, "Hide Alert", Toast.LENGTH_LONG).show() }
                .show()
    }
}
