package io.github.aafactory.sample.api

import io.github.aafactory.sample.helpers.GIT_HUB_API_BASE_URL
import org.junit.Assert
import org.junit.Test
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

/**
 * Created by CHO HANJOONG on 2018-04-21.
 */

class GitHubServiceTest {

    @Test
    fun repository() {
        val retrofit = Retrofit.Builder()
                .baseUrl(GIT_HUB_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val gitHubService = retrofit.create(GitHubService::class.java)
        val call = gitHubService.repository("hanjoongcho", "aaf-easydiary")
        val repository = call.execute().body()
        Assert.assertEquals("", repository.toString())
    }
}