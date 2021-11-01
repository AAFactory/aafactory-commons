package io.github.aafactory.sample

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.simplemobiletools.commons.extensions.showKeyboard
import io.github.aafactory.commons.activities.BaseMarkDownViewActivity
import io.github.aafactory.commons.activities.BaseSimpleActivity
import io.github.aafactory.commons.extensions.dpToPixel
import io.github.aafactory.sample.activities.DevActivity
import io.github.aafactory.sample.activities.MarkDownViewActivity
import io.github.aafactory.sample.adapters.ShowcaseAdapter
import io.github.aafactory.sample.databinding.ActivityMainBinding
import io.github.aafactory.sample.databinding.DialogSearchMainBinding
import io.github.aafactory.sample.models.Showcase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : BaseSimpleActivity() {
    private lateinit var mActivityMainBinding: ActivityMainBinding
    private lateinit var mDialogSearchMainBinding: DialogSearchMainBinding

    private val showcaseItems = mutableListOf(
            Showcase("hanjoongcho", "CheatSheet", false, ""),
            Showcase("AppIntro", "AppIntro", false, ""),
            Showcase("Werb", "PickPhotoSample", false, ""),
            Showcase("ParkSangGwon", "TedBottomPicker", false, ""),
            Showcase("donglua", "PhotoPicker", false, ""),
            Showcase("gsuitedevs", "android-samples", false, ""),
            Showcase("kioko", "motion-layout-playground", false, ""),
            Showcase("zoonooz", "simple-view-behavior", false, ""),
            Showcase("googlesamples", "android-architecture", false, ""),
            Showcase("afollestad", "material-dialogs", false, ""),
            Showcase("googlesamples", "android-FingerprintDialog", false, ""),
            Showcase("rubensousa", "ViewPagerCards", false, ""),
            Showcase("AAFactory", "aafactory-commons", false, ""),
            Showcase("devunwired", "recyclerview-playground", false, ""),
            Showcase("openlayers", "openlayers", false, ""),
            Showcase("juanchosaravia", "KedditBySteps", false, ""),
            Showcase("Tapadoo", "Alerter", false, ""),
            Showcase("saulmm", "CoordinatorExamples", false, ""),
            Showcase("medyo", "Fancybuttons", false, ""),
            Showcase("pedant", "sweet-alert-dialog", false, ""),
            Showcase("timusus", "RecyclerView-FastScroll", false, ""),
            Showcase("woxingxiao", "BubbleSeekBar", false, ""),
            Showcase("PhilJay", "MPAndroidChart", false, ""),
            Showcase("navermaps", "maps.android", false, ""),
            Showcase("bumptech", "glide", false, ""),
            Showcase("googlesamples", "android-ConstraintLayoutExamples", false, ""),
            Showcase("", "Regular Expression", true, "Basic Chapter-01", "https://raw.githubusercontent.com/hanjoongcho/CheatSheet/master/regular-expression/chapter01.md"),
            Showcase("", "Git-01", true, "Git and Git Flow Cheat Sheet", "https://raw.githubusercontent.com/arslanbilal/git-cheat-sheet/master/other-sheets/git-cheat-sheet-ko.md"),
            Showcase("", "Android-01", true, "Project guidelines", "https://raw.githubusercontent.com/hanjoongcho/android-guidelines/master/project_and_code_guidelines.md"),
            Showcase("", "Android-02", true, "Architecture Guidelines / MVP", "https://raw.githubusercontent.com/hanjoongcho/android-guidelines/master/architecture_guidelines/android_architecture.md"),
            Showcase("", "Kotlin-01", true, "Explanation of kotlin basic functions", "https://raw.githubusercontent.com/hanjoongcho/CheatSheet/master/kotlin/kotlin.md"),
            Showcase("", "Kotlin-02", true, "Explanation of kotlin collection functions", "https://raw.githubusercontent.com/hanjoongcho/CheatSheet/master/kotlin/kotlin.collections.md"),
            Showcase("", "Kotlin-03", true, "Coding Conventions", "https://raw.githubusercontent.com/hanjoongcho/CheatSheet/master/kotlin/coding-conventions.md"),
            Showcase("", "ES6-01", true, "var, let, const", "https://gist.githubusercontent.com/hanjoongcho/983fe388a669f1da9df13cf64f63c5f3/raw/d1587f1da1d7ead1ba695e50094dbf52daaf6e1e/var-let-const.md"),
            Showcase("", "ES6-02", true, "Promise", "https://raw.githubusercontent.com/hanjoongcho/CheatSheet/master/es6/promise.md"),
            Showcase("", "Data-Structure-01", true, "국가상수도데이터베이스표준화지침(20210101 개정)", "https://raw.githubusercontent.com/hanjoongcho/CheatSheet/master/design/database/standardization.md"),
            Showcase("", "Data-Structure-02", true, "Unicode", "https://raw.githubusercontent.com/hanjoongcho/CheatSheet/master/data-structure/unicode.md"),
            Showcase("", "Data-Structure-03", true, "ASCII", "https://raw.githubusercontent.com/hanjoongcho/CheatSheet/master/data-structure/ascii.md"),
            Showcase("", "Database-01", true, "Oracle", "https://raw.githubusercontent.com/hanjoongcho/CheatSheet/master/database/oracle-01.md"),

            Showcase("", "Java-01", true, "Describes annotations mainly used in Spring Framework", "https://raw.githubusercontent.com/hanjoongcho/CheatSheet/master/annotations/spring.md"),
            Showcase("", "Java-02", true, "Java 8 - Lambda Expression", "https://raw.githubusercontent.com/hanjoongcho/java8-guides-tutorials/master/src/test/java/lambda/LambdaExpressionTest.java", true),
            Showcase("", "Java-03", true, "Java 8 - Default Methods", "https://raw.githubusercontent.com/hanjoongcho/java8-guides-tutorials/master/src/test/java/defaultmethod/DefaultMethodTest.java", true),
            Showcase("", "Java-04", true, "Java 8 - Functions", "https://raw.githubusercontent.com/hanjoongcho/java8-guides-tutorials/master/src/test/java/functions/FunctionFunctionalInterfaceTest.java", true),
            Showcase("", "Java-05", true, "Java 8 - Stream Count", "https://raw.githubusercontent.com/hanjoongcho/java8-guides-tutorials/master/src/test/java/streams/StreamWithCountTest.java", true),
            Showcase("", "Java-06", true, "Java 8 - Stream with Filter", "https://raw.githubusercontent.com/hanjoongcho/java8-guides-tutorials/master/src/test/java/streams/StreamWithFilterTest.java", true),
            Showcase("", "Java-07", true, "Java 8 - Stream with Map", "https://raw.githubusercontent.com/hanjoongcho/java8-guides-tutorials/master/src/test/java/streams/StreamWithMapTest.java", true),
            Showcase("", "Java-08", true, "Java 8 - Stream with Sorted", "https://raw.githubusercontent.com/hanjoongcho/java8-guides-tutorials/master/src/test/java/streams/StreamWithSortedTest.java", true),
            Showcase("", "Java-09", true, "Java 8 - Stream with Match", "https://raw.githubusercontent.com/hanjoongcho/java8-guides-tutorials/master/src/test/java/streams/StreamWithMatchTest.java", true),
            Showcase("", "Java-10", true, "Java 8 - Stream Reduce", "https://raw.githubusercontent.com/hanjoongcho/java8-guides-tutorials/master/src/test/java/streams/StreamReduceTest.java", true),
            Showcase("", "Java-11", true, "Java 8 - Stream Consumer", "https://raw.githubusercontent.com/hanjoongcho/java8-guides-tutorials/master/src/test/java/consumer/ConsumerFunctionalInterfaceTest.java", true),
            Showcase("", "Java-12", true, "Java 8 - Predicate", "https://raw.githubusercontent.com/hanjoongcho/java8-guides-tutorials/master/src/test/java/predicate/PredicateFunctionalInterfaceTest.java", true),
            Showcase("", "Java-13", true, "Java 8 - Comparator", "https://raw.githubusercontent.com/hanjoongcho/java8-guides-tutorials/master/src/test/java/comparator/ComparatorFunctionalInterfaceTest.java", true),
            Showcase("", "Java-14", true, "Java 8 - Suppliers", "https://raw.githubusercontent.com/hanjoongcho/java8-guides-tutorials/master/src/test/java/suppliers/SupplierFunctionalInterfaceTest.java", true),
    ).apply { this.sortBy { item -> item.name } }

    private var mListItem: ArrayList<Showcase> = arrayListOf()
    private val mAdapter: ShowcaseAdapter by lazy {
        ShowcaseAdapter(
                this,
                mListItem
        ) { _, _, position, _ ->
            val showCase = mAdapter.getItem(position)
            startActivity(Intent(this, MarkDownViewActivity::class.java).apply {
                val openUrlInfo = if (showCase.isCheatSheet) showCase.cheatSheetUrl else "https://raw.githubusercontent.com/${showCase.owner}/${showCase.name}/master/README.md"
                putExtra(BaseMarkDownViewActivity.OPEN_URL_INFO, openUrlInfo)
                putExtra(BaseMarkDownViewActivity.OPEN_URL_DESCRIPTION, showCase.name)
                putExtra(BaseMarkDownViewActivity.FORCE_APPEND_CODE_BLOCK, showCase.forceAppendCodeBlock)
            })
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        mDialogSearchMainBinding = DialogSearchMainBinding.inflate(layoutInflater)
        setContentView(mActivityMainBinding.root)
        setSupportActionBar(toolbar)
        supportActionBar?.run { 
            title = getString(R.string.app_name)
        }
        recyclerView.addItemDecoration(ItemDecoration(this))
        mAdapter.attachTo(recyclerView)
        refresh()
        runOnUiThread {
            progressBar.visibility = View.GONE
        }
    }

    private fun refresh(repoName: String = "", description: String = "") {
        mListItem.clear()
        showcaseItems.filter { item ->
            when (repoName.isEmpty()) {
                true -> true
                false -> { item.name.contains(repoName, true) }
            }
        }.filter { item ->
            when (description.isEmpty()) {
                true -> true
                false -> { item.description.contains(description, true) }
            }
        }.run { mListItem.addAll(this) }
        mAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    private var mDialogSearchMain: Dialog? = null
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.devConsole -> {
                startActivity(Intent(this, DevActivity::class.java))
            }
            R.id.search -> {
                mDialogSearchMain?.show() ?: run {
                    val builder = AlertDialog.Builder(this).apply {
                        setNegativeButton(getString(android.R.string.cancel), null)
                        setPositiveButton(getString(android.R.string.ok)) { _, _ ->
                            refresh(mDialogSearchMainBinding.repositoryNameQuery.text.toString(), mDialogSearchMainBinding.repositoryDescriptionQuery.text.toString())
                            mDialogSearchMain?.dismiss()
                        }
                    }
                    mDialogSearchMain = builder.create().apply {
                        setTitle("Repository Filter")
//                    val dialogSearchMain = layoutInflater.inflate(R.layout.dialog_search_main, null)
//                    setView(dialogSearchMain)
                        setView(mDialogSearchMainBinding.root)
                        mDialogSearchMainBinding.repositoryNameQuery.run {
                            requestFocus()
                            showKeyboard(this)
                        }
                        show()
                    }
                }

//                Handler(Looper.getMainLooper()).postDelayed({
//                    mDialogSearchMainBinding.repositoryNameQuery.run {
//                        runOnUiThread {
//                            requestFocus()
//                            showKeyboard(this)
//                        }
//                    }
//                }, 1000)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private inner class ItemDecoration(private val context: Context) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.bottom = context.dpToPixel(3F)
        }
//        override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
//            super.getItemOffsets(outRect, view, parent, state)
//            outRect?.bottom = context.dpToPixel(3F)
//        }
    }
}
