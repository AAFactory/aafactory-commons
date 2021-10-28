package io.github.aafactory.sample.models

/**
 * Created by CHO HANJOONG on 2018-04-20.
 */

data class Showcase(
    val owner: String,
    val name: String,
    val displayName: String,
    val isCheatSheet: Boolean,
    var description: String,
    val cheatSheetUrl: String = "",
    val forceAppendCodeBlock: Boolean = false,
    var stargazersCount: Int = 0,
    var forksCount: Int = 0,
) {
    fun repositoryName() = when (displayName.isEmpty()) {
        true -> name
        false -> displayName
    }
}