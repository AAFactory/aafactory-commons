package io.github.aafactory.sample.activities

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.github.aafactory.commons.activities.BaseSimpleActivity
import io.github.aafactory.commons.extensions.determineNextAlarm
import io.github.aafactory.commons.extensions.dpToPixel
import io.github.aafactory.commons.extensions.triggerRestart
import io.github.aafactory.sample.adapters.RecipeAdapter
import io.github.aafactory.sample.databinding.ActivityDevBinding
import io.github.aafactory.sample.models.Recipe
import kotlinx.android.synthetic.main.content_main.*

class DevActivity : BaseSimpleActivity() {
    companion object {
        const val NEXT_ALARM = "nextAlarm"
        const val RESTART_APP = "restartApp"
    }

    private lateinit var mActivityDevBinding: ActivityDevBinding
    private var mItems: ArrayList<Recipe> = arrayListOf()
    private val adapter: RecipeAdapter by lazy {
        RecipeAdapter(
                this,
                mItems
        ) { _, _, position, _ ->
            val recipe = adapter.getItem(position)
            when (recipe.title) {
                NEXT_ALARM -> determineNextAlarm()
                RESTART_APP -> triggerRestart(DevActivity::class.java)
            }
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

        mItems.add(Recipe(NEXT_ALARM, "시스템에 설정된 다음 알람 시간을 확인합니다."))
        mItems.add(Recipe(RESTART_APP, "애플리케이션을 다시 시작합니다."))
        adapter.notifyDataSetChanged()
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