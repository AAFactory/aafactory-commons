package com.bumptech.glide.samples.imgur.api

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import javax.inject.Named
import javax.inject.Singleton

/** Provides classes related to the Imgur API via Dagger.  */
@Module
class ApiModule {

    @Singleton
    @Named("hotViralImages")
    @Provides
    internal fun provideHotViralImages(imgurObservables: ImgurObservables): Observable<List<Image>> {
        return imgurObservables.getHotViralImages(5 /*maxPages*/)
    }

    @Provides
    internal fun imgurObservables(imgurService: ImgurService): ImgurObservables {
        return ImgurObservables(imgurService)
    }

    @Provides
    internal fun getImgurService(retrofit: Retrofit): ImgurService {
        return retrofit.create(ImgurService::class.java)
    }

    @Provides
    internal fun retrofit(): Retrofit {
        val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    chain.proceed(
                            chain.request()
                                    .newBuilder()
                                    .addHeader("Authorization", "Client-ID " + ImgurService.CLIENT_ID)
                                    .build())
                }
                .build()
        return Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("https://api.imgur.com/3/")
                .build()
    }
}
