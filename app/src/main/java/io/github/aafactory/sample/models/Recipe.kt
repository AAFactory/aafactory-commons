package io.github.aafactory.sample.models

data class Recipe(
        var title: String = "",
        var description: String = "",
        var callback: () -> Unit
)