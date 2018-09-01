package com.xw.samlpe.bubbleseekbar.sample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import io.github.aafactory.sample.R
import kotlinx.android.synthetic.main.bubbleseekbar_activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var mTag: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bubbleseekbar_activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)  
        }

        main_tab_btn_1.setOnClickListener(this)
        main_tab_btn_2.setOnClickListener(this)
        main_tab_btn_3.setOnClickListener(this)
        main_tab_btn_4.setOnClickListener(this)

        if (savedInstanceState == null) {
            val ft = supportFragmentManager.beginTransaction()
            ft.add(R.id.container, DemoFragment1.newInstance(), "demo1")
            ft.commit()
            mTag = "demo1"
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.main_tab_btn_1 -> switchContent("demo1")
            R.id.main_tab_btn_2 -> switchContent("demo2")
            R.id.main_tab_btn_3 -> switchContent("demo3")
            R.id.main_tab_btn_4 -> switchContent("demo4")
        }
    }

    private fun switchContent(toTag: String) {
        if (mTag == toTag)
            return

        val fm = supportFragmentManager
        val from = fm.findFragmentByTag(mTag)
        var to: Fragment? = fm.findFragmentByTag(toTag)

        val ft = fm.beginTransaction()
        
        if (to == null) {
            to = when (toTag) {
                "demo1" -> DemoFragment1.newInstance()
                "demo2" -> DemoFragment2.newInstance()
                "demo3" -> DemoFragment3.newInstance()
                else -> DemoFragment4.newInstance()
            }
        }

        from?.let {
            if (it.isAdded) {
                ft.hide(from).show(to)
            } else {
                ft.hide(from).add(R.id.container, to, toTag)
            }
        }

        ft.commit()
        mTag = toTag
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
