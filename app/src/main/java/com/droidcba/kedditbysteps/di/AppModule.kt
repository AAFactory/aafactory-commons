package com.droidcba.kedditbysteps.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import io.github.aafactory.sample.helpers.BaseApplication

/**
 *
 * @author juancho.
 */
@Module
class AppModule(val app: BaseApplication) {

    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideApplication() : Application = app

}
