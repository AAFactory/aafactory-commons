package com.bumptech.glide.samples.imgur

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.samples.imgur.api.Image
import dagger.android.AndroidInjection
import io.github.aafactory.sample.R
import io.github.aafactory.sample.helpers.SampleActivity
import kotlinx.android.synthetic.main.content_toolbar.*
import kotlinx.android.synthetic.main.glideimgur_activity_main.*
import rx.Observable
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named

/**
 * Displays images and GIFs from Imgur in a scrollable list of cards.
 */
class MainActivity : SampleActivity() {

    @field:[Inject Named("hotViralImages")]
    lateinit var fetchImagesObservable: Observable<List<Image>>

    private lateinit var adapter: ImgurImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.glideimgur_activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
        }

        recycler_view.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager
        adapter = ImgurImageAdapter()
        recycler_view.adapter = adapter

        fetchImagesObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<Image>> {
                    override fun onCompleted() {}

                    override fun onError(e: Throwable) {}

                    override fun onNext(images: List<Image>) {
                        adapter.setData(images)
                    }
                })
    }

    override fun onDestroy() {
        super.onDestroy()
        fetchImagesObservable
                .unsubscribeOn(AndroidSchedulers.mainThread())
    }

    private inner class ImgurImageAdapter : RecyclerView.Adapter<ViewHolder>() {
        private var images = emptyList<Image>()

        fun setData(images: List<Image>) {
            this.images = images
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.glideimgur_image_card, parent, false))
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val vh = holder as ViewHolder
            val image = images[position]
            vh.title.text = if (TextUtils.isEmpty(image.title)) image.description else image.title
            ImgurGlide.with(vh.imageView)
                    .load(image.link)
                    .into(vh.imageView)
        }

        override fun getItemCount(): Int {
            return images.size
        }

        internal inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imageView: ImageView = itemView.findViewById<View>(R.id.image) as ImageView
            val title: TextView = itemView.findViewById<View>(R.id.title) as TextView
        }
    }
}


