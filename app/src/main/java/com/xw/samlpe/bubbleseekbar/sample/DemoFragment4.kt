package com.xw.samlpe.bubbleseekbar.sample

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xw.repo.BubbleSeekBar
import io.github.aafactory.sample.R
import kotlinx.android.synthetic.main.bubbleseekbar_fragment_demo_4.*
import java.util.*

/**
 * DemoFragment4
 *
 *
 * Created by woxingxiao on 2017-03-11.
 */

class DemoFragment4 : Fragment() {
    private lateinit var mActivity: Activity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bubbleseekbar_fragment_demo_4, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        demo_4_obs_scroll_view.setOnScrollChangedListener { scrollView, x, y, oldx, oldy -> demo_4_seek_bar_1.correctOffsetWhenContainerOnScrolling() }
        demo_4_seek_bar_2.onProgressChangedListener = object : BubbleSeekBar.OnProgressChangedListenerAdapter() {
            override fun onProgressChanged(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float, fromUser: Boolean) {
                val s = String.format(Locale.CHINA, "onChanged int:%d, float:%.1f", progress, progressFloat)
                demo_4_progress_text_1.text = s
            }

            override fun getProgressOnActionUp(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float) {
                val s = String.format(Locale.CHINA, "onActionUp int:%d, float:%.1f", progress, progressFloat)
                demo_4_progress_text_2.text = s
            }

            override fun getProgressOnFinally(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float, fromUser: Boolean) {
                val s = String.format(Locale.CHINA, "onFinally int:%d, float:%.1f", progress, progressFloat)
                demo_4_progress_text_3.text = s
            }
        }

        // trigger by set progress or seek by finger
        demo_4_seek_bar_3.setProgress(demo_4_seek_bar_3.max)

        // customize section texts
        demo_4_seek_bar_4.setCustomSectionTextArray { sectionCount, array ->
            array.clear()
            array.put(1, "bad")
            array.put(4, "ok")
            array.put(7, "good")
            array.put(9, "great")

            array
        }
        demo_4_seek_bar_4.onProgressChangedListener = object : BubbleSeekBar.OnProgressChangedListenerAdapter() {
            override fun onProgressChanged(bubbleSeekBar: BubbleSeekBar, progress: Int, progressFloat: Float, fromUser: Boolean) {
                val color: Int = when {
                    progress <= 10 -> ContextCompat.getColor(mActivity, R.color.color_red)
                    progress <= 40 -> ContextCompat.getColor(mActivity, R.color.color_red_light)
                    progress <= 70 -> ContextCompat.getColor(mActivity, R.color.colorAccent)
                    progress <= 90 -> ContextCompat.getColor(mActivity, R.color.color_blue) 
                    else -> ContextCompat.getColor(mActivity, R.color.color_green)
                }
                bubbleSeekBar.setSecondTrackColor(color)
                bubbleSeekBar.setThumbColor(color)
                bubbleSeekBar.setBubbleColor(color)
            }
        }
        demo_4_seek_bar_4.setProgress(60f)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = context as Activity
    }

    companion object {
        fun newInstance(): DemoFragment4 {
            return DemoFragment4()
        }
    }
}
