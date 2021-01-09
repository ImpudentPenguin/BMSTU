package org.emakeeva.tisd

import org.emakeeva.tisd.workers.Columns
import org.emakeeva.tisd.workers.Pointers
import org.emakeeva.tisd.workers.Values

object Utils {
    fun getMatrixString(matrix: List<List<Int>>): String {
        val stringBuilder = StringBuilder()
        for (element in matrix) {
            stringBuilder.append(element)
            stringBuilder.append("\n")
        }

        return stringBuilder.toString()
    }

    fun printResult(result: Triple<Values, Columns, Pointers>) {
        result.apply {
            println("Ненулевые элементы матрицы:")
            println(first)
            println("Столбцовые координаты ненулевых элементов:")
            println(second)
            println("Модифицированный массив точек входа в строку:")
            println(third)
        }
    }
}