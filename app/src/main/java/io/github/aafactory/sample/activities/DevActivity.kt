package io.github.aafactory.sample.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import io.github.aafactory.commons.activities.BaseSimpleActivity
import io.github.aafactory.commons.extensions.*
import io.github.aafactory.sample.adapters.RecipeAdapter
import io.github.aafactory.sample.databinding.ActivityDevBinding
import io.github.aafactory.sample.helpers.ItemDecoration
import io.github.aafactory.sample.models.Recipe
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import java.io.IOException


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
            title = "Dev Console"
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
            startActivity(Intent(this, ContributorActivity::class.java))
        })
        mItems.add(Recipe("Signature Pad", "JS Signature Pad - Local") {
            startActivity(Intent(this, WebViewSignaturePadActivity::class.java).apply { putExtra(WebViewSignaturePadActivity.TARGET_URL, "file:///android_asset/signature-pad/signature-pad.html") })
        })
        mItems.add(Recipe("Signature Pad", "JS Signature Pad - Online") {
            startActivity(Intent(this, WebViewSignaturePadActivity::class.java).apply { putExtra(WebViewSignaturePadActivity.TARGET_URL, "https://hanjoongcho.github.io/CheatSheet/signature-pad/signature-pad.html") })
        })
//        adapter.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }
}

