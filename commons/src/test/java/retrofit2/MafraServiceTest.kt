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
            val AUCNG_DE: String                   // 경락일
            , val PBLMNG_WHSAL_MRKT_NM: String     // 공영 도매시장명
            , val PBLMNG_WHSAL_MRKT_CD: String     // 공영 도매시장코드
            , val CPR_NM: String                   // 법인명
            , val CPR_CD: String                   // 법인코드
            , val PRDLST_NM: String                // 품목명
            , val PRDLST_CD: String                // 품목코드
            , val SPCIES_NM: String                // 품종명
            , val SPCIES_CD: String                // 품종코드
            , val GRAD: String                     // 등급
            , val GRAD_CD: String                  // 등급코드
            , val DELNGBUNDLE_QY: String           // 거래단량
            , val STNDRD: String                   // 규격
            , val STNDRD_CD: String                // 규격코드
            , val DELNG_QY: String                 // 거래량
            , val MUMM_AMT: String                 // 최소가
            , val AVRG_AMT: String                 // 평균가
            , val MXMM_AMT: String                 // 최대가
            , val AUC_CO: String                   // 경매건수
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

//    http://data.mafra.go.kr/opendata/data/getDataGrid.do?s_entity_id=TI_EPIS_MRKT_DAY_REAL_TIME_AUC&cur_page=1&s_search_form_name=&s_search_form_value=
//    http://www.okdab.kr/dma/opendata/ajaxDataGrid.do?entityId=TI_EPIS_MRKT_DAY_REAL_TIME_AUC&cur_page=1&s_search_form_name=&s_search_form_value=
    companion object {
        const val API_URL = "http://data.mafra.go.kr"
    }
}