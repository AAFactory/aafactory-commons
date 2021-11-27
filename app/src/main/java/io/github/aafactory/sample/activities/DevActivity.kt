package io.github.aafactory.sample.activities

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.github.aafactory.commons.activities.BaseSimpleActivity
import io.github.aafactory.commons.extensions.*
import io.github.aafactory.sample.R
import io.github.aafactory.sample.adapters.RecipeAdapter
import io.github.aafactory.sample.databinding.ActivityDevBinding
import io.github.aafactory.sample.models.Recipe
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.IOException
import java.lang.StringBuilder


class DevActivity : BaseSimpleActivity() {
    private lateinit var mActivityDevBinding: ActivityDevBinding
    private var mItems: ArrayList<Recipe> = arrayListOf()
    private val adapter: RecipeAdapter by lazy {
        RecipeAdapter(
                this,
                mItems
        ) { _, _, position, _ ->
            adapter.getItem(position).callback.invoke()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityDevBinding = ActivityDevBinding.inflate(layoutInflater)
        setContentView(mActivityDevBinding.root)
        setSupportActionBar(mActivityDevBinding.toolbar)
        supportActionBar?.run {
            title = ""
            setDisplayHomeAsUpEnabled(true)
        }

        recyclerView.addItemDecoration(ItemDecoration(this))
        adapter.attachTo(recyclerView)

        mItems.add(Recipe("Next Alarm", "시스템에 설정된 다음 알람 시간을 확인합니다.") { determineNextAlarm() })
        mItems.add(Recipe("Restart App", "애플리케이션을 다시 시작합니다.") { triggerRestart(DevActivity::class.java) })
        mItems.add(Recipe("Orientation Sensor", "방향 센서를 비활성화 시킴니다.") { setScreenOrientationSensor(false) })
        mItems.add(Recipe("Orientation Sensor", "방향 센서를 활성화 시킴니다.") { setScreenOrientationSensor(true) })
        mItems.add(Recipe("Orientation Lock", "현재 화면방향 기준으로 화면을 고정합니다.") { holdCurrentOrientation() })
        mItems.add(Recipe("Orientation Unlock", "고정된 화면을 해제합니다.") { clearHoldOrientation() })
        mItems.add(Recipe("AlertDialog", "OK AlertDialog") { showAlertDialog("Dialog Title", "OK AlertDialog", null) })
        mItems.add(Recipe("AppIntro", "AppIntro library sample") { startActivity(Intent(this, AppIntroActivity::class.java)) })
        mItems.add(Recipe("okhttp", "Http Client Example") {
            val client = OkHttpClient()
            val request: Request = Request.Builder()
                    .url("https://raw.githubusercontent.com/hanjoongcho/CheatSheet/master/README.md")
                    .build()
            // enqueue is async
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}
                override fun onResponse(call: Call, response: Response) {
                    val body = response.body()?.string() ?: ""
                    runOnUiThread { makeToast(body, Toast.LENGTH_LONG) }
                    response.close()
                }
            })

            // execute is sync
            CoroutineScope(Dispatchers.IO).launch {
                runCatching {
                    val response = client.newCall(request).execute()
                    val body = response.body()?.string() ?: ""
                    withContext(Dispatchers.Main) { makeToast(body, Toast.LENGTH_LONG)}
                }
            }
        })
        mItems.add(Recipe("Retrofit + Glide", "Retrofit, Glide를 이용한 이미지 로딩") {
            val width = LinearLayout.LayoutParams.MATCH_PARENT
            val height = LinearLayout.LayoutParams.MATCH_PARENT
            val rootView = findViewById<ViewGroup>(android.R.id.content).rootView
            var popupWindow: PopupWindow? = null
            popupWindow = PopupWindow(layoutInflater.inflate(R.layout.partial_contributor_info, null).apply {
                findViewById<ImageView>(R.id.image_close).setOnClickListener { popupWindow?.dismiss() }
                val infoText = findViewById<TextView>(R.id.text_info)
                val avatarContainer = findViewById<LinearLayoutCompat>(R.id.layout_info)
                val sb = StringBuilder()
                CoroutineScope(Dispatchers.IO).launch {
                    kotlin.runCatching {
                        val baseUrl = "https://api.github.com"
                        val retrofit: Retrofit = Retrofit.Builder()
                                .baseUrl(baseUrl)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build()

                        val github = retrofit.create(GitHub::class.java)
                        val call = github.contributors("hanjoongcho", "aaf-easydiary")
                        val contributors: List<Contributor>? = call.execute().body()
                        contributors?.let {
                            for (contributor in it) {
                                withContext(Dispatchers.Main) {
                                    val avatarImageView = ImageView(this@DevActivity).apply {
                                        layoutParams = ViewGroup.LayoutParams(100, 100)
                                    }
                                    Glide.with(this@DevActivity).load(contributor.avatar_url).into(avatarImageView)
                                    avatarContainer.addView(avatarImageView, 0)
                                }
                                sb.append(java.lang.String.format("%s %d %s\n", contributor.login, contributor.contributions, contributor.avatar_url))
                            }
                            withContext(Dispatchers.Main) {
                                infoText.text = sb.toString()
                            }
                        }
                    }
                }

            }, width, height, true).apply {
                showAtLocation(rootView, Gravity.CENTER, 0, 0)
            }
        })
//        adapter.notifyDataSetChanged()
    }
    data class Contributor(val login: String, val contributions: Int, val avatar_url: String)
    interface GitHub {
        @GET("/repos/{owner}/{repo}/contributors")
        fun contributors(@Path("owner") owner: String?, @Path("repo") repo: String?): retrofit2.Call<List<Contributor>>
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

    private inner class ItemDecoration(private val context: Context) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.bottom = context.dpToPixel(3F)
        }
    }
}

