package com.thomaskioko.materialmotion

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import io.github.aafactory.sample.R
import kotlinx.android.synthetic.main.materialmotion_activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.materialmotion_activity_main)

        recycler_view_tweets.apply {
            adapter = TweetsAdapter()
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
}
