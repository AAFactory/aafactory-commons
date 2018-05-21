package io.github.aafactory.sample.models

import android.util.Log

/**
 * Created by CHO HANJOONG on 2018-04-20.
 */

data class Showcase(
    val owner: String,
    val name: String,
    val description: String,
    val stargazersCount: Int,
    val forksCount: Int
) {
    fun getRepositoryName(): String {
        val nameInfo = name.split("|")
        return when (nameInfo.size == 1) {
            true -> name
            false -> nameInfo[0]
        }
    }
    
    fun getRepositoryNameWithSubName(): String {
        val nameInfo = name.split("|")
        return when (nameInfo.size == 1) {
            true -> name
            false -> String.format("%s %s", nameInfo[0], nameInfo[1])
        }
    }
}