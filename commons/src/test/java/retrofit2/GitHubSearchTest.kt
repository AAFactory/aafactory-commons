package retrofit2

import org.junit.Assert
import org.junit.Test
import retrofit2.adapter.RepositoryAdapter
import retrofit2.model.Repository
import retrofit2.service.GitHubSearch

/**
 * Created by CHO HANJOONG on 2018-05-06.
 */

class GitHubSearchTest {

    @Test
    fun crawlBestMatch() {

        val retrofit = Retrofit.Builder()
                .baseUrl(GitHubSearch.API_URL)
                .addConverterFactory(RepositoryAdapter.createRepositoryAdapter)
                .build()

        // Create an instance of our GitHub API interface.
        val github = retrofit.create(GitHubSearch.GitHub::class.java)

        // Create a call instance for looking up Retrofit contributors.
        val call = github.contributors("kotlin", 1, "%E2%9C%93")
        
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

        val retrofit = Retrofit.Builder()
                .baseUrl(GitHubSearch.API_URL)
                .addConverterFactory(RepositoryAdapter.createRepositoryAdapter)
                .build()

        // Create an instance of our GitHub API interface.
        val github = retrofit.create(GitHubSearch.GitHub::class.java)

        // Create a call instance for looking up Retrofit contributors.
        val call = github.contributors("kotlin", 1, "%E2%9C%93", "desc", "updated")

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

        val retrofit = Retrofit.Builder()
                .baseUrl(GitHubSearch.API_URL)
                .addConverterFactory(RepositoryAdapter.createRepositoryAdapter)
                .build()

        // Create an instance of our GitHub API interface.
        val github = retrofit.create(GitHubSearch.GitHub::class.java)

        // Create a call instance for looking up Retrofit contributors.
        val call = github.contributors("kotlin", 1, "%E2%9C%93", "desc", "forks")

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
    fun crawlMostStars() {

        val retrofit = Retrofit.Builder()
                .baseUrl(GitHubSearch.API_URL)
                .addConverterFactory(RepositoryAdapter.createRepositoryAdapter)
                .build()

        // Create an instance of our GitHub API interface.
        val github = retrofit.create(GitHubSearch.GitHub::class.java)

        // Create a call instance for looking up Retrofit contributors.
        val call = github.contributors("kotlin", 1, "%E2%9C%93", "desc", "stars")

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