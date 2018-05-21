package io.github.aafactory.sample.models

import android.util.Log

/**
 * Created by CHO HANJOONG on 2018-04-20.
 */

data class Showcase(
    val owner: String,
    val name: String,
    val displayName: String,
    val description: String,
    val stargazersCount: Int,
    val forksCount: Int
)