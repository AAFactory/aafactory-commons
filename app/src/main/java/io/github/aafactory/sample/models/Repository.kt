package io.github.aafactory.sample.models

/**
 * Created by CHO HANJOONG on 2018-04-21.
 */

data class Repository (
    val name: String,
    val description: String,
    val stargazers_count: Int,
    val forks_count: Int
)