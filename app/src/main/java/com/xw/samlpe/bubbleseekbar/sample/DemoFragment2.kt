package com.xw.samlpe.bubbleseekbar.sample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.github.aafactory.sample.R

/**
 * DemoFragment2
 *
 *
 * Created by woxingxiao on 2017-03-11.
 */

class DemoFragment2 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bubbleseekbar_fragment_demo_2, container, false)
    }

    companion object {
        fun newInstance(): DemoFragment2 {
            return DemoFragment2()
        }
    }
}
