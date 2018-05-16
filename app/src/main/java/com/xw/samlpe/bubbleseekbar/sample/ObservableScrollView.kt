package com.xw.samlpe.bubbleseekbar.sample

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView

class ObservableScrollView : ScrollView {
    private var mOnScrollChangedListener: OnScrollChangedListener? = null

    constructor(context: Context) : super(context) 

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) 

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) 

    fun setOnScrollChangedListener(onScrollChangedListener: OnScrollChangedListener) {
        this.mOnScrollChangedListener = onScrollChangedListener
    }

    override fun onScrollChanged(x: Int, y: Int, oldx: Int, oldy: Int) {
        super.onScrollChanged(x, y, oldx, oldy)
        if (mOnScrollChangedListener != null) {
            mOnScrollChangedListener!!.onScrollChanged(this, x, y, oldx, oldy)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return false // 此处为了演示，阻止ScrollView拦截Touch事件
    }

    interface OnScrollChangedListener {
        fun onScrollChanged(scrollView: ObservableScrollView, x: Int, y: Int, oldx: Int, oldy: Int)
    }
}