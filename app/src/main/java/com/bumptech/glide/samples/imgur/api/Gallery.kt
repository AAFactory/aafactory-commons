package com.bumptech.glide.samples.imgur.api

/**
 * Represents Imgur's Gallery resource.
 *
 *
 * Populated automatically by GSON.
 */
class Gallery {
    var data: List<Image>? = null

    override fun toString(): String {
        return ("Gallery{"
                + "data=" + data
                + '}'.toString())
    }
}
