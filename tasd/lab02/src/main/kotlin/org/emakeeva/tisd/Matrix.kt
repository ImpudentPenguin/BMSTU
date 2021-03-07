package org.emakeeva.tisd

import java.lang.StringBuilder

/**
 * Класс матрицы
 * @param width столбцы матрицы
 * @param height строки матрицы
 */
class Matrix(override val width: Int, override val height: Int) : IMatrix<Int> {

    private val list = MutableList(height) { MutableList(width) { 0 } }

    override fun get(row: Int, column: Int): Int = list[row][column]

    override fun set(row: Int, column: Int, value: Int) {
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

    override fun equals(other: Any?): Boolean {
        var result = false

        if (other is Matrix) {
            val otherMatrix = other as Matrix?

            for (row in 0 until height) {
                for (column in 0 until width) {
                    result = (this[row, column] == otherMatrix?.get(row, column))
                }
            }
        }

        return result
    }
}