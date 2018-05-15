package com.bumptech.glide.samples.imgur.api

/**
 * Represents Imgur's Image resource.
 *
 *
 * Populated automatically by GSON
 */
class Image {
    val id: String? = null
    var title: String? = null
    var description: String? = null
    var link: String? = null
    var is_album: Boolean = false

    override fun toString(): String {
        return ("Image{"
                + "id='" + id + '\''.toString()
                + ", title='" + title + '\''.toString()
                + ", description='" + description + '\''.toString()
                + ", link='" + link + '\''.toString()
                + ", is_album='" + is_album + '\''.toString()
                + '}'.toString())
    }
}
