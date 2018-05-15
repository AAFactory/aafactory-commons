package com.bumptech.glide.samples.imgur.api

import java.util.ArrayList

import rx.Observable
import rx.functions.Func1
import rx.functions.Func2

/**
 * Observables for retrieving metadata from Imgur's API.
 */
internal class ImgurObservables(private val imgurService: ImgurService) {

    fun getHotViralImages(maxPages: Int): Observable<List<Image>> {
        return Observable.range(0, maxPages)
                .flatMap { integer ->
                    imgurService.getHotViral(integer!!).map(GetData()).flatMap { images ->
                        val iterator = images.iterator()
                        while (iterator.hasNext()) {
                            if (iterator.next().is_album) {
//                                iterator.remove()
                            }
                        }
                        Observable.just(images)
                    }
                }
                .takeWhile { images -> !images.isEmpty() }
                .scan { images, images2 ->
                    val result = ArrayList<Image>(images.size + images2.size)
                    result.addAll(images)
                    result.addAll(images2)
                    result
                }
                .cache()
    }

    private class GetData : Func1<Gallery, List<Image>> {
        override fun call(gallery: Gallery): List<Image>? {
            return gallery.data
        }
    }
}
