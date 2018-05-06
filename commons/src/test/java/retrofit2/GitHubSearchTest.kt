package retrofit2

import org.junit.Assert
import org.junit.Test
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.helpers.ToStringConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by CHO HANJOONG on 2018-05-06.
 */

class GitHubSearchTest() {

    companion object {
        val API_URL = "https://github.com"
    }
    
    @Test
    fun crawlPage() {

        val retrofit = Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(ToStringConverterFactory())
                .build()

        // Create an instance of our GitHub API interface.
        val github = retrofit.create(GitHub::class.java)

        // Create a call instance for looking up Retrofit contributors.
        val call = github.contributors("kotlin", "%E2%9C%93")
        // Fetch and print a list of the contributors to the library.
        val responseBody = call.execute().body()
        println(responseBody)
        Assert.assertTrue(true)
    }
    
    interface GitHub {
        @GET("/search")
        fun contributors(
                @Query("q") question: String,
                @Query("utf8") utf8: String
        ): Call<String>
    }
}