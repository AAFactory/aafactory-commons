package retrofit2

import okhttp3.ResponseBody
import org.jsoup.Jsoup
import org.junit.Assert
import org.junit.Test
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException
import java.lang.reflect.Type

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
        val call = github.contributors("kotlin", 1, "%E2%9C%93")
        // Fetch and print a list of the contributors to the library.
        val responseBody = call.execute().body()
        println(responseBody)
        Assert.assertTrue(true)
    }
    
    interface GitHub {
        @GET("/search")
        fun contributors(
                @Query("q") question: String,
                @Query("p") page: Int,
                @Query("utf8") utf8: String
        ): Call<Repository>
    }

    data class Repository(val name: String, val description: String, val link: String)

    internal class RepositoryAdapter : Converter<ResponseBody, Repository> {

        @Throws(IOException::class)
        override fun convert(responseBody: ResponseBody): Repository {
            val document = Jsoup.parse(responseBody.string())
            val repoList = document.select(".repo-list > .repo-list-item")
            repoList?.let {
                it.onEach { itemDiv ->
                    val col8 = itemDiv.select(".col-8")
                    println("link $API_URL${col8.select("h3 > a").attr("href")}")
                    println("description ${col8.select("p.col-9").text()}")
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