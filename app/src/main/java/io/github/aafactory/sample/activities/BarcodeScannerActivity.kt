package io.github.aafactory.sample.activities

import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.zxing.BarcodeFormat
import com.google.zxing.ResultPoint
import com.google.zxing.client.android.BeepManager
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import com.simplemobiletools.commons.extensions.toast
import io.github.aafactory.commons.activities.BaseSimpleActivity
import io.github.aafactory.sample.R
import io.github.aafactory.sample.adapters.BarcodeAdapter
import io.github.aafactory.sample.databinding.ActivityBarcodeScannerBinding
import io.github.aafactory.sample.helpers.ItemDecoration
import io.github.aafactory.sample.models.Barcode


class BarcodeScannerActivity : BaseSimpleActivity() {
    private lateinit var mBinding: ActivityBarcodeScannerBinding
    private lateinit var mBeepManager: BeepManager
    private var mItems: ArrayList<Barcode> = arrayListOf()
    private var mLastText: String? = null
    private val mAdapter: BarcodeAdapter by lazy {
        BarcodeAdapter(
                this,
                mItems
        ) { _, _, position, _ ->
            toast("position: $position")
        }
    }
    private val callback: BarcodeCallback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult) {
            if (result.text == null || result.text == mLastText) {
                // Prevent duplicate scans
                return
            }
            mLastText = result.text
            mBinding.barcodeScanner.setStatusText(result.text)
//            mBeepManager.playBeepSoundAndVibrate()
            val toneGen1 = ToneGenerator(AudioManager.STREAM_MUSIC, 100)
            toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150)
            mItems.add(Barcode(mLastText!!))
            mAdapter.notifyDataSetChanged()
            mBinding.recyclerView.smoothScrollToPosition(mItems.size)
//             mBinding.recyclerView.scrollToPosition(mItems.size.minus(1))
        }
        override fun possibleResultPoints(resultPoints: List<ResultPoint>) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityBarcodeScannerBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setSupportActionBar(mBinding.toolbar)
        supportActionBar?.run {
            title = "Barcode Scan"
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_cross)
        }

        val formats: Collection<BarcodeFormat> = listOf(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39)
        mBinding.barcodeScanner.run {
            initializeFromIntent(intent)
            decodeContinuous(callback)
            barcodeView.decoderFactory = DefaultDecoderFactory(formats)
        }
        mBeepManager = BeepManager(this)

        mBinding.recyclerView.addItemDecoration(ItemDecoration(this))
        mAdapter.attachTo(mBinding.recyclerView)
    }

    override fun onResume() {
        super.onResume()
        mBinding.barcodeScanner.resume()
    }

    override fun onPause() {
        super.onPause()
        mBinding.barcodeScanner.pause()
    }
}