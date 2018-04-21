package io.github.aafactory.sample.api

import io.github.aafactory.sample.models.Repository
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * Created by CHO HANJOONG on 2018-04-21.
 */

interface GitHubService {
    
    @GET("/repos/{owner}/{repo}")
    fun repository(
            @Path("owner") owner: String,
            @Path("repo") repo: String): Call<Repository>
}