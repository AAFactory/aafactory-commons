package com.example.android.constraintlayoutexamples

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
import android.transition.TransitionManager
import android.view.View

import io.github.aafactory.sample.R

/**
 * An example activity that show cases the use of [ConstraintSet].
 * It starts out with a single ConstraintLayout which is then transitioned to
 * a different ConstraintLayout using [ConstraintSet.applyTo].
 */
class ConstraintSetExampleActivity : AppCompatActivity() {

    /**
     * Whether to show an enlarged image
     */
    private var mShowBigImage = false
    /**
     * The ConstraintLayout that any changes are applied to.
     */
    private var mRootLayout: ConstraintLayout? = null
    /**
     * The ConstraintSet to use for the normal initial state
     */
    private val mConstraintSetNormal = ConstraintSet()
    /**
     * ConstraintSet to be applied on the normal ConstraintLayout to make the Image bigger.
     */
    private val mConstraintSetBig = ConstraintSet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.constraintset_example_main)

        mRootLayout = findViewById<View>(R.id.activity_constraintset_example) as ConstraintLayout
        // Note that this can also be achieved by calling
        // `mConstraintSetNormal.load(this, R.layout.constraintset_example_main);`
        // Since we already have an inflated ConstraintLayout in `mRootLayout`, clone() is
        // faster and considered the best practice.
        mConstraintSetNormal.clone(mRootLayout!!)
        // Load the constraints from the layout where ImageView is enlarged.
        mConstraintSetBig.load(this, R.layout.constraintset_example_big)

        if (savedInstanceState != null) {
            val previous = savedInstanceState.getBoolean(SHOW_BIG_IMAGE)
            if (previous != mShowBigImage) {
                mShowBigImage = previous
                applyConfig()
            }
        }
    }

    public override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putBoolean(SHOW_BIG_IMAGE, mShowBigImage)
    }

    // Method called when the ImageView within R.layout.constraintset_example_main
    // is clicked.
    fun toggleMode(v: View) {
        TransitionManager.beginDelayedTransition(mRootLayout)
        mShowBigImage = !mShowBigImage
        applyConfig()
    }

    private fun applyConfig() {
        if (mShowBigImage) {
            mConstraintSetBig.applyTo(mRootLayout!!)
        } else {
            mConstraintSetNormal.applyTo(mRootLayout!!)
        }
    }

    companion object {
        private val SHOW_BIG_IMAGE = "showBigImage"
    }
}
