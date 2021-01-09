package org.emakeeva.tisd.models

import java.lang.StringBuilder

/**
 * Класс матрицы
 */
class Matrix(var list: MutableList<MutableList<Int>>)  {

    constructor(height: Int, width: Int, list: MutableList<MutableList<Int>>) : this(list) {
        this.height = height
        this.width = width
    }

    var height = list.size
    var width = list.firstOrNull()?.size ?: 0
    var values = mutableListOf<Int>()
    var columns = mutableListOf<Int>()
    var pointers = mutableListOf<Int>()
    var arrays = Triple(values, columns, pointers)

    fun get(row: Int, column: Int): Int = list[row][column]

    fun set(row: Int, column: Int, value: Int) {
        list[row][column] = value
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        for (row in 0 until height) {
            for (column in 0 until width) {
                stringBuilder.append(String.format("%d ", list[row][column]))
            }
            stringBuilder.append("\n")
        }

        return stringBuilder.toString()
    }
}