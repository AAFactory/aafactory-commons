package retrofit2

import org.junit.Assert
import org.junit.Test
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.IOException

/**
 * Created by CHO HANJOONG on 2018-05-05.
 */

class SimpleServiceTest {

    @Test
    @Throws(IOException::class)
    fun callRestService() {
        val retrofit = Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        // Create an instance of our GitHub API interface.
        val github = retrofit.create(GitHub::class.java)

        // Create a call instance for looking up Retrofit contributors.
        val call = github.contributors("square", "retrofit")

        // Fetch and print a list of the contributors to the library.
        val contributors = call.execute().body()
        for (contributor in contributors!!) {
            println(contributor.login + " (" + contributor.contributions + ")")
        }
        Assert.assertTrue(true)
    }

    class Contributor(val login: String, val contributions: Int)

    interface GitHub {
        @GET("/repos/{owner}/{repo}/contributors")
        fun contributors(
                @Path("owner") owner: String,
                @Path("repo") repo: String): Call<List<Contributor>>
    }

    companion object {
        val API_URL = "https://api.github.com"
    }
}
