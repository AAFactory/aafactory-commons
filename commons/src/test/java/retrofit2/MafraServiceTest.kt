package retrofit2

import org.junit.Assert
import org.junit.Test
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException

class MafraServiceTest {

    @Test @Throws(IOException::class)
    fun callRestService() {
        val retrofit = Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val github = retrofit.create(Mafra::class.java)

        // Create a call instance for looking up Retrofit contributors.
        val call = github.dataGrid("TI_EPIS_MRKT_DAY_REAL_TIME_AUC", "1")

        // Fetch and print a list of the contributors to the library.
        val products = call.execute().body()
        products?.forEach {
            println(it)
        }
        Assert.assertTrue(true)
    }

    data class Product(
            val PRDLST_CD: String
            , val PRDLST_NM: String
            , val DELNG_DE: String
            , val CPR_NM: String
            , val PBLMNG_WHSAL_MRKT_NM: String
    )

    interface Mafra {
        @GET("/opendata/data/getDataGrid.do")
        fun dataGrid(
                @Query("s_entity_id") s_entity_id: String,
                @Query("cur_page") cur_page: String,
                @Query("s_search_form_name") s_search_form_name: String = "",
                @Query("s_search_form_value") s_search_form_value: String = ""
        ): Call<List<Product>>
    }

    //    http://data.mafra.go.kr/opendata/data/getDataGrid.do?s_entity_id=TI_EPIS_MRKT_DAY_REAL_TIME_AUC&cur_page=2&s_search_form_name=&s_search_form_value=
    companion object {
        const val API_URL = "http://data.mafra.go.kr"
    }
}