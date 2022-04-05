package me.emakeeva.security.models

data class TreeModel(
    val freq: Map<Char, Int>,
    val head: NodeModel
)