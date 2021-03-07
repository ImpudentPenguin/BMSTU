package org.emakeeva.tisd

import java.lang.StringBuilder

/**
 * Класс матрицы
 */
class Matrix(override val height: Int, override val width: Int): IMatrix<Int> {

    private val list = MutableList(height) { MutableList(width) { Int.MAX_VALUE } }

    override fun get(row: Int, column: Int): Int = list[row][column]

    override fun set(row: Int, column: Int, value: Int) {
        list[row][column] = value
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        for (row in 0 until height) {
            for (column in 0 until width) {
                stringBuilder.append(String.format("%d ",list[row][column]))
            }
            stringBuilder.append("\n")
        }

        return stringBuilder.toString()
    }
}