package io.github.aafactory.sample.models

import android.util.Log

/**
 * Created by CHO HANJOONG on 2018-04-20.
 */

data class Showcase(
    val owner: String,
    val name: String,
    val displayName: String,
    var description: String,
    var stargazersCount: Int,
    var forksCount: Int
) {
    fun repositoryName() = when (displayName.isEmpty()) {
        true -> name
        false -> displayName
    }
}