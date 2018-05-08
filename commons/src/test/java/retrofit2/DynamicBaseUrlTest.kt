package retrofit2

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.apache.commons.lang3.StringUtils
import org.junit.Assert
import org.junit.Test
import retrofit2.http.GET
import java.io.IOException

/**
 * Created by hanjoong on 2018-05-05.
 */

class DynamicBaseUrlTest {

    @Test
    @Throws(IOException::class)
    fun hostSelectionInterceptor() {
        val hostSelectionInterceptor = HostSelectionInterceptor()

        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(hostSelectionInterceptor)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl("http://www.coca-cola.com/")
                .callFactory(okHttpClient)
                .build()

        val pop = retrofit.create(Pop::class.java)

        val response1 = pop.robots().execute()
        println("Response from: " + response1.raw().request().url())

        hostSelectionInterceptor.setHost("www.pepsi.com")
        val response2 = pop.robots().execute()
        Assert.assertEquals("http://www.pepsi.com/robots.txt", StringUtils.trim(response2.raw().request().url().toString()))
    }

    interface Pop {
        @GET("robots.txt")
        fun robots(): Call<ResponseBody>
    }

    internal class HostSelectionInterceptor : Interceptor {
        @Volatile private var host: String? = null

        fun setHost(host: String) {
            this.host = host
        }

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            var request = chain.request()
            val host = this.host
            if (host != null) {
                val newUrl = request.url().newBuilder()
                        .host(host)
                        .build()
                request = request.newBuilder()
                        .url(newUrl)
                        .build()
            }
            return chain.proceed(request)
        }
    }
}
