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
import kotlinx.android.synthetic.main.bubbleseekbar_fragment_demo_3.*;

/**
 * DemoFragment3
 *
 *
 * Created by woxingxiao on 2017-03-11.
 */

class DemoFragment3 : Fragment() {
    private lateinit var mActivity: Activity 

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bubbleseekbar_fragment_demo_3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        demo_3_seek_bar_1.configBuilder
                .min(0f)
                .max(5f)
                .progress(2f)
                .sectionCount(5)
                .trackColor(ContextCompat.getColor(mActivity, R.color.color_gray))
                .secondTrackColor(ContextCompat.getColor(mActivity, R.color.color_blue))
                .thumbColor(ContextCompat.getColor(mActivity, R.color.color_blue))
                .showSectionText()
                .sectionTextColor(ContextCompat.getColor(mActivity, R.color.colorPrimary))
                .sectionTextSize(18)
                .showThumbText()
                .thumbTextColor(ContextCompat.getColor(mActivity, R.color.color_red))
                .thumbTextSize(18)
                .bubbleColor(ContextCompat.getColor(mActivity, R.color.color_red))
                .bubbleTextSize(18)
                .showSectionMark()
                .seekStepSection()
                .touchToSeek()
                .sectionTextPosition(BubbleSeekBar.TextPosition.BELOW_SECTION_MARK)
                .build()

        demo_3_seek_bar_2.configBuilder
                .min(0f)
                .max(5f)
                .progress(2f)
                .sectionCount(5)
                .trackColor(ContextCompat.getColor(mActivity, R.color.color_gray))
                .secondTrackColor(ContextCompat.getColor(mActivity, R.color.color_blue))
                .thumbColor(ContextCompat.getColor(mActivity, R.color.color_blue))
                .showSectionText()
                .sectionTextColor(ContextCompat.getColor(mActivity, R.color.colorPrimary))
                .sectionTextSize(18)
                .showThumbText()
                .touchToSeek()
                .thumbTextColor(ContextCompat.getColor(mActivity, R.color.color_red))
                .thumbTextSize(18)
                .bubbleColor(ContextCompat.getColor(mActivity, R.color.color_red))
                .bubbleTextSize(18)
                .showSectionMark()
                .seekBySection()
                .autoAdjustSectionMark()
                .sectionTextPosition(BubbleSeekBar.TextPosition.BELOW_SECTION_MARK)
                .build()

        demo_3_seek_bar_3.configBuilder
                .min(-50f)
                .max(50f)
                .sectionCount(10)
                .sectionTextInterval(2)
                .trackColor(ContextCompat.getColor(mActivity, R.color.color_red_light))
                .secondTrackColor(ContextCompat.getColor(mActivity, R.color.color_red))
                .seekBySection()
                .showSectionText()
                .sectionTextPosition(BubbleSeekBar.TextPosition.BELOW_SECTION_MARK)
                .build()

        demo_3_seek_bar_4.configBuilder
                .min(1f)
                .max(1.5f)
                .floatType()
                .sectionCount(10)
                .secondTrackColor(ContextCompat.getColor(mActivity, R.color.color_green))
                .showSectionText()
                .showThumbText()
                .build()

        demo_3_seek_bar_5.configBuilder
                .min(-0.4f)
                .max(0.4f)
                .progress(0f)
                .floatType()
                .sectionCount(10)
                .sectionTextInterval(2)
                .showSectionText()
                .sectionTextPosition(BubbleSeekBar.TextPosition.BELOW_SECTION_MARK)
                .autoAdjustSectionMark()
                .build()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = context as Activity
    }

    companion object {
        fun newInstance(): DemoFragment3 {
            return DemoFragment3()
        }
    }
}
