package io.github.aafactory.sample.helpers

import com.bumptech.glide.samples.imgur.DaggerImgurApplicationComponent
import com.droidcba.kedditbysteps.di.AppModule
import com.droidcba.kedditbysteps.di.news.DaggerNewsComponent
import com.droidcba.kedditbysteps.di.news.NewsComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * Created by CHO HANJOONG on 2018-05-12.
 */

class BaseApplication : DaggerApplication() {

    companion object {
        lateinit var newsComponent: NewsComponent
    }

    override fun onCreate() {
        super.onCreate()
        newsComponent = DaggerNewsComponent.builder()
                .appModule(AppModule(this))
                //.newsModule(NewsModule()) Module with empty constructor is implicitly created by dagger.
                .build()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerImgurApplicationComponent.create()
    }
}
