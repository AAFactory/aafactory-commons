package retrofit2

import org.junit.Assert
import org.junit.Test
import retrofit2.adapter.RepositoryAdapter
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.model.Repository
import retrofit2.service.GitHubSearch

/**
 * Created by CHO HANJOONG on 2018-05-06.
 */

class GitHubSearchTest {

    private val retrofitGitHubUrlService by lazy {
        Retrofit.Builder()
                .baseUrl(GitHubSearch.GIT_HUB_URL)
                .addConverterFactory(RepositoryAdapter.createRepositoryAdapter)
                .build().create(GitHubSearch.GitHub::class.java)
    }

    private val retrofitApiUrlService by lazy {
        Retrofit.Builder()
                .baseUrl(GitHubSearch.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(GitHubSearch.GitHub::class.java)
    }

    @Test
    fun crawlBestMatch() {

        // Create a call instance for looking up Retrofit contributors.
        val call = retrofitGitHubUrlService.contributors("kotlin", 1, "%E2%9C%93")
        
        // Fetch and print a list of the contributors to the library.
        val result = call.execute().body()
        result?.let {
            it.listRepository.onEach { repository ->
                printResult(repository)
            }
        }
        Assert.assertTrue(true)
    }

    @Test
    fun crawlRecentlyUpdated() {

        // Create a call instance for looking up Retrofit contributors.
        val call = retrofitGitHubUrlService.contributors("kotlin", 1, "%E2%9C%93", "desc", "updated")

        // Fetch and print a list of the contributors to the library.
        val result = call.execute().body()
        result?.let {
            it.listRepository.onEach { repository ->
                printResult(repository)
            }
        }
        Assert.assertTrue(true)
    }

    @Test
    fun crawlMostForks() {

        // Create a call instance for looking up Retrofit contributors.
        val call = retrofitGitHubUrlService.contributors("kotlin", 1, "%E2%9C%93", "desc", "forks")

        // Fetch and print a list of the contributors to the library.
        val result = call.execute().body()
        result?.let {
            it.listRepository.onEach { repository ->
                printResult(repository)
            }
        }
        Assert.assertTrue(true)
    }

    @Test
    fun callRestService() {

        val call = retrofitApiUrlService.contributors("square", "retrofit")

        val contributors = call.execute().body()
        for (contributor in contributors!!) {
            println("${contributor.login} (${contributor.contributions}) ")
        }
        Assert.assertTrue(true)
    }

    @Test
    fun crawlMostStars() {

        // Create a call instance for looking up Retrofit contributors.
        val call = retrofitGitHubUrlService.contributors("kotlin", 1, "%E2%9C%93", "desc", "stars")

        // Fetch and print a list of the contributors to the library.
        val result = call.execute().body()
        result?.let {
            it.listRepository.onEach { repository ->
                printResult(repository)
            }
        }
        Assert.assertTrue(true)
    }

    private fun printResult(repository: Repository) {
        println("${repository.name} ${repository.stargazer}\n${repository.link}\n${repository.description}\n")
    }
}