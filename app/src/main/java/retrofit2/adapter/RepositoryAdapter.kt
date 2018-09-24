package retrofit2.adapter

import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.model.Repository
import retrofit2.model.Result
import retrofit2.service.GitHubSearch
import java.io.IOException
import java.lang.reflect.Type

class RepositoryAdapter : Converter<ResponseBody, Result> {

    @Throws(IOException::class)
    override fun convert(responseBody: ResponseBody): Result {
        val document = Jsoup.parse(responseBody.string())
        val repoList = document.select(".repo-list > .repo-list-item")
        val listRepository = mutableListOf<Repository>()
        repoList?.let {
            it.onEach { itemDiv ->
                val col8 = itemDiv.select(".col-8")
                listRepository.add(
                        Repository(
                                col8.select("h3 > a").text(),
                                col8.select("p.col-9").text(),
                                "${GitHubSearch.API_URL}${col8.select("h3 > a").attr("href")}",
                                itemDiv.select(".col-2 > a").text()
                        )
                )
            }
        }

        return Result("", listRepository)
    }

    companion object {
        val createRepositoryAdapter: Converter.Factory = object : Converter.Factory() {
            override fun responseBodyConverter(
                    type: Type,
                    annotations: Array<Annotation>,
                    retrofit: Retrofit
            ): Converter<ResponseBody, *>? {
                return if (type === Result::class.java) RepositoryAdapter() else null
            }
        }
    }
}