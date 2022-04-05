package me.emakeeva.security.models

data class NodeModel(
    var frequency: Int = 0,
    var letter: Char? = null,
    var leftChild: NodeModel? = null,
    var rightChild: NodeModel? = null
)