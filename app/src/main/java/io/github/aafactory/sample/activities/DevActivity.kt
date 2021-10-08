package io.github.aafactory.sample.activities

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.github.aafactory.commons.activities.BaseSimpleActivity
import io.github.aafactory.commons.extensions.*
import io.github.aafactory.sample.adapters.RecipeAdapter
import io.github.aafactory.sample.databinding.ActivityDevBinding
import io.github.aafactory.sample.models.Recipe
import kotlinx.android.synthetic.main.content_main.*

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
//        adapter.notifyDataSetChanged()
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