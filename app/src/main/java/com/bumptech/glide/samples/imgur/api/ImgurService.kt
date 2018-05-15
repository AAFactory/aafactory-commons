package com.bumptech.glide.samples.imgur.api

import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

/**
 * Define's Imgur's API for Retrofit.
 */
interface ImgurService {

    @GET("gallery/hot/viral/{page}")
    fun getHotViral(@Path("page") page: Int): Observable<Gallery>

    @GET("gallery/hot/{sort}/{page}.json")
    fun getHot(@Path("sort") sort: Sort, @Path("page") page: Int): Observable<Gallery>

    @GET("gallery/{section}/{sort}/{page}.json")
    fun getGallery(@Path("section") section: Section,
                   @Path("sort") sort: Sort, @Path("page") page: Int): Observable<Gallery>

    /**
     * Sections that Imgur's API allows us to query from.
     */
    enum class Section {
        hot,
        top,
        user
    }

    /**
     * The sort order for content within a particular section.
     */
    enum class Sort {
        viral,
        top,
        time,
        rising
    }

    companion object {
        val CLIENT_ID = "36d1f6bef16370c"
    }
}
