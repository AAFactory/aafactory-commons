package com.droidcba.kedditbysteps.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import io.github.aafactory.sample.helpers.SampleApplication

/**
 *
 * @author juancho.
 */
@Module
class AppModule(val app: SampleApplication) {

    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideApplication() : Application = app

}
