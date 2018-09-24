package retrofit2.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.model.Contributor
import retrofit2.model.Result

class GitHubSearch {

    companion object {
        const val GIT_HUB_URL = "https://github.com"
        const val API_URL = "https://api.github.com"
    }

    interface GitHub {
        @GET("/search")
        fun contributors(
                @Query("q") question: String,
                @Query("p") page: Int,
                @Query("utf8") utf8: String,
                @Query("order") order: String = "desc",
                @Query("s") status: String = ""
        ): Call<Result>

        @GET("/repos/{owner}/{repo}/contributors")
        fun contributors(
                @Path("owner") owner: String,
                @Path("repo") repo: String
        ): Call<List<Contributor>>
    }
}