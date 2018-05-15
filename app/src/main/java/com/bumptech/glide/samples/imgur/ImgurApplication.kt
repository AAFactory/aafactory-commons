package com.bumptech.glide.samples.imgur

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
