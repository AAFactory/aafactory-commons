package retrofit2

import okhttp3.ResponseBody
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import org.junit.Assert
import org.junit.Test
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.helpers.ToStringConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.IOException
import java.lang.reflect.Type
import java.util.*

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
                .addConverterFactory(RepositoryAdapter.createRepositoryAdapter)
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
        ): Call<Repository>
    }

    data class Repository(val name: String, val description: String, val link: String)

    internal class RepositoryAdapter : Converter<ResponseBody, Repository> {

        @Throws(IOException::class)
        override fun convert(responseBody: ResponseBody): Repository {
            val document = Jsoup.parse(responseBody.string())
            val repoList = document.select(".repo-list")
            if (repoList.isNotEmpty()) {
                val list: Elements? = repoList.select("div")
                list?.let { 
                    println(it)
                }
            }
            
            return Repository("", "", "")
        }

        companion object {
            val createRepositoryAdapter: Converter.Factory = object : Converter.Factory() {
                override fun responseBodyConverter(
                        type: Type,
                        annotations: Array<Annotation>,
                        retrofit: Retrofit
                ): Converter<ResponseBody, *>? {
                    return if (type === Repository::class.java) RepositoryAdapter() else null
                }
            }
        }
    }
}