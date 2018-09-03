package com.afollestad.materialdialogssample

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.checkbox.checkBoxPrompt
import io.github.aafactory.sample.R
import io.github.aafactory.sample.helpers.SampleActivity
import kotlinx.android.synthetic.main.materialdialog_activity_main.*

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

        basic_titled_buttons.setOnClickListener {
            MaterialDialog(this).show {
                title(R.string.useGoogleLocationServices)
                message(R.string.useGoogleLocationServicesPrompt)
                positiveButton(R.string.agree)
                negativeButton(R.string.disagree)
            }
        }

        basic_long_titled_buttons.setOnClickListener {
            MaterialDialog(this).show {
                title(R.string.useGoogleLocationServices)
                message(R.string.loremIpsum)
                positiveButton(R.string.agree)
                negativeButton(R.string.disagree)
            }
        }

        basic_icon.setOnClickListener {
            MaterialDialog(this).show {
                title(R.string.useGoogleLocationServices)
                icon(R.mipmap.ic_launcher)
                message(R.string.useGoogleLocationServicesPrompt)
                positiveButton(R.string.agree)
                negativeButton(R.string.disagree)
            }
        }

        basic_checkbox_titled_buttons.setOnClickListener {
            MaterialDialog(this).show {
                title(R.string.useGoogleLocationServices)
                message(R.string.useGoogleLocationServicesPrompt)
                positiveButton(R.string.agree)
                negativeButton(R.string.disagree)
                checkBoxPrompt(R.string.checkboxConfirm) { checked ->
                    toast("Checked? $checked")
                }
            }
        }
        
        buttons_callbacks.setOnClickListener {
            MaterialDialog(this).show {
                title(R.string.useGoogleLocationServices)
                message(R.string.useGoogleLocationServicesPrompt)
                positiveButton(R.string.agree) { _ ->
                    toast("On positive")
                }
                negativeButton(R.string.disagree) { _ ->
                    toast("On negative")
                }
                neutralButton(R.string.more_info) { _ ->
                    toast("On neutral")
                }
            }
        }
    }

    var toast: Toast? = null
    internal fun Activity.toast(message: CharSequence) {
        toast?.cancel()
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast!!.show()
    }
}