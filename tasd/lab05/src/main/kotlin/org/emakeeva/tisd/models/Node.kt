package org.emakeeva.tisd.models

class Node(
    var frequency: Int = 0, // частота
    var letter: Char? = null, // символ
    var leftChild: Node? = null, // левый потомок
    var rightChild: Node? = null, // правый потомок
    var flag: Boolean = true // флаг доступа
)