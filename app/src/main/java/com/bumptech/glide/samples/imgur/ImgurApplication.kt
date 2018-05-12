package com.bumptech.glide.samples.imgur

import com.droidcba.kedditbysteps.di.AppModule
import com.droidcba.kedditbysteps.di.news.DaggerNewsComponent
import com.droidcba.kedditbysteps.di.news.NewsComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * Runs Dagger injection in the Imgur sample.
 */
class ImgurApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerImgurApplicationComponent.create()
    }
}
