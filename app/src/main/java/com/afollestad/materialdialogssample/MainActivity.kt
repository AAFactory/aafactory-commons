package com.afollestad.materialdialogssample

import android.app.Activity
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.checkbox.checkBoxPrompt
import com.afollestad.materialdialogs.list.listItems
import com.afollestad.materialdialogs.list.listItemsMultiChoice
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.afollestad.materialdialogs.input.input
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

        list_titled_buttons.setOnClickListener {
            MaterialDialog(this).show {
                title(R.string.socialNetworks)
                listItems(R.array.socialNetworks) { _, index, text ->
                    toast("Selected item $text at index $index")
                }
                positiveButton(R.string.agree)
                negativeButton(R.string.disagree)
            }
        }

        list_long_titled_buttons.setOnClickListener {
            MaterialDialog(this).show {
                title(R.string.states)
                listItems(R.array.states) { _, index, text ->
                    toast("Selected item $text at index $index")
                }
                positiveButton(R.string.agree)
                negativeButton(R.string.disagree)
            }
        }


        list_checkPrompt_buttons.setOnClickListener {
            MaterialDialog(this).show {
                title(R.string.socialNetworks)
                listItems(R.array.socialNetworks_longItems) { _, index, text ->
                    toast("Selected item $text at index $index")
                }
                positiveButton(R.string.agree)
                negativeButton(R.string.disagree)
                checkBoxPrompt(R.string.checkboxConfirm) { checked ->
                    toast("Checked? $checked")
                }
            }
        }

        single_choice_disabled_items.setOnClickListener {
            MaterialDialog(this).show {
                title(R.string.socialNetworks)
                listItemsSingleChoice(
                        R.array.socialNetworks, initialSelection = 1, disabledIndices = intArrayOf(1, 3)
                ) { _, index, text ->
                    toast("Selected item $text at index $index")
                }
                positiveButton(R.string.choose)
            }
        }

        multiple_choice_disabled_items.setOnClickListener {
            MaterialDialog(this).show {
                title(R.string.socialNetworks)
                listItemsMultiChoice(
                        R.array.socialNetworks,
                        initialSelection = intArrayOf(2, 3),
                        disabledIndices = intArrayOf(1, 3)
                ) { _, indices, text ->
                    toast("Selected items ${text.joinToString()} at indices ${indices.joinToString()}")
                }
                positiveButton(R.string.choose)
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

        input.setOnClickListener {
            MaterialDialog(this).show {
                title(R.string.useGoogleLocationServices)
                input(
                        hint = "Type something",
                        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS
                ) { _, text ->
                    toast("Input: $text")
                }
                positiveButton(R.string.agree)
                negativeButton(R.string.disagree)
            }
        }

        input_message.setOnClickListener {
            MaterialDialog(this).show {
                title(R.string.useGoogleLocationServices)
                message(R.string.useGoogleLocationServicesPrompt)
                input(
                        hint = "Type something",
                        prefill = "Pre-filled!",
                        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS
                ) { _, text ->
                    toast("Input: $text")
                }
                positiveButton(R.string.agree)
                negativeButton(R.string.disagree)
            }
        }

        input_counter.setOnClickListener {
            MaterialDialog(this).show {
                title(R.string.useGoogleLocationServices)
                input(
                        hint = "Type something",
                        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS,
                        maxLength = 8
                ) { _, text ->
                    toast("Input: $text")
                }
                positiveButton(R.string.agree)
                negativeButton(R.string.disagree)
            }
        }

        input_check_prompt.setOnClickListener {
            MaterialDialog(this).show {
                title(R.string.useGoogleLocationServices)
                input(
                        hint = "Type something",
                        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS
                ) { _, text ->
                    toast("Input: $text")
                }
                positiveButton(R.string.agree)
                negativeButton(R.string.disagree)
                checkBoxPrompt(R.string.checkboxConfirm) { checked ->
                    toast("Checked? $checked")
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