package com.bumptech.glide.samples.imgur

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

/**
 * The Application Dagger module for the Imgur sample.
 */
@Module
internal class ApplicationModule {
    @Provides
    fun okHttpClient(): OkHttpClient {
        return OkHttpClient()
    }
}
