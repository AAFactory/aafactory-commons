package com.xw.samlpe.bubbleseekbar.sample

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.xw.repo.BubbleSeekBar

import java.util.Random

import io.github.aafactory.sample.R

/**
 * DemoFragment1
 *
 *
 * Created by woxingxiao on 2017-03-11.
 */

class DemoFragment1 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.bubbleseekbar_fragment_demo_1, container, false)
        val bubbleSeekBar = view.findViewById<BubbleSeekBar>(R.id.demo_1_seek_bar)
        bubbleSeekBar.setProgress(20f)
        val button = view.findViewById<Button>(R.id.demo_1_button)

        button.setOnClickListener { v ->
            val progress = Random().nextInt(bubbleSeekBar.max.toInt())
            bubbleSeekBar.setProgress(progress.toFloat())
            Snackbar.make(v, "set random progress = " + progress, Snackbar.LENGTH_SHORT).show()
        }

        return view
    }

    companion object {
        fun newInstance(): DemoFragment1 {
            return DemoFragment1()
        }
    }
}
