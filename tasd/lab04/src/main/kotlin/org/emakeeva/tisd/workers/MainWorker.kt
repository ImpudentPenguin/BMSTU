package org.emakeeva.tisd.workers

import org.emakeeva.tisd.models.Matrix

typealias Values = MutableList<Int> // ненулевые элементы в порядке просмотра матрицы построчно AN
typealias Columns = MutableList<Int> // столбцовые координаты ненулевых элементов JA
typealias Pointers = MutableList<Int> // модифицированный массив точек входа в строку JR
typealias Arrays = Triple<Values, Columns, Pointers>

class MainWorker {

    /**
     * Упаковка матрицы
     */
    fun matrixPacking(defaultList: MutableList<MutableList<Int>>): Matrix {
        val matrix = Matrix(defaultList)
        var isRow = false

        matrix.apply {
            for (row in 0 until height) {
                for (column in 0 until width) {
                    if (!isRow) {
                        isRow = true
                        matrix.pointers.add(matrix.values.size)
                    }
                    if (list[row][column] != 0) {
                        matrix.values.add(list[row][column])
                        matrix.columns.add(column)
                    }
                }
                isRow = false
            }
        }

        matrix.pointers.add(matrix.values.count())
        return matrix
    }

    /**
     * Распаковка матрицы
     */
    fun matrixUnpacking(columns: Int, arrays: Arrays): MutableList<MutableList<Int>> {
        val result = MutableList(arrays.third.size - 1) { MutableList(columns) { 0 } }
        arrays.apply {
            for (i in 0 until third.size - 1) {
                for (j in third[i] until third[i + 1]) {
                    if (j >= first.size)
                        continue
                    result[i][second[j]] = first[j]
                }
            }
        }

        return result
    }

    /**
     * Сложение матрицы
     */
    fun additionMatrix(firstMatrix: Matrix, secondMatrix: Matrix): Matrix? {
        if (firstMatrix.height != secondMatrix.height || firstMatrix.width != secondMatrix.width) {
            println("Матрицы разных размеров")
            return null
        }

        val result = Matrix(firstMatrix.height, firstMatrix.width, mutableListOf())
        var isRow = false

        for (i in 0 until firstMatrix.pointers.size - 1) {
            var first = firstMatrix.pointers[i]
            var second = secondMatrix.pointers[i]

            if (first == firstMatrix.pointers[i + 1] && second == secondMatrix.pointers[i + 1]) { // если индексы совпадают, значит обе строки пустые
                result.pointers.add(result.values.size)
                continue
            }

            while ((first < firstMatrix.pointers[i + 1] || second < secondMatrix.pointers[i + 1])) { // пока рассматриваемая строка не закончится
                if (second < secondMatrix.pointers[i + 1] && first < firstMatrix.pointers[i + 1]) { // если строки ненулевые
                    when {
                        firstMatrix.columns[first] == secondMatrix.columns[second] -> { // если элементы находятся в одном столбце
                            isRow = setPointers(isRow, result)
                            if (firstMatrix.values[first] + secondMatrix.values[second] != 0) {
                                result.values.add(firstMatrix.values[first] + secondMatrix.values[second])
                                result.columns.add(secondMatrix.arrays.second[second])
                            }
                            second++
                            first++
                        }
                        firstMatrix.columns[first] > secondMatrix.columns[second] -> { // если столбец первой матрицы больше второй
                            isRow = setPointers(isRow, result)
                            result.values.add(secondMatrix.values[second])
                            result.columns.add(secondMatrix.columns[second])
                            second++
                        }
                        firstMatrix.columns[first] < secondMatrix.columns[second] -> { // если столбец первой матрицы меньше второй
                            isRow = setPointers(isRow, result)
                            result.values.add(firstMatrix.values[first])
                            result.columns.add(firstMatrix.columns[first])
                            first++
                        }
                    }
                } else if (second < secondMatrix.pointers[i + 1]) { // строка второй матрицы ненулевая, строка первой матрицы нулевая
                    isRow = setPointers(isRow, result)
                    result.values.add(secondMatrix.values[second])
                    result.columns.add(secondMatrix.columns[second])
                    second++
                } else if (first < firstMatrix.pointers[i + 1]) { // строка первой матрицы ненулевая, строка второй матрицы нулевая
                    isRow = setPointers(isRow, result)
                    result.values.add(firstMatrix.values[first])
                    result.columns.add(firstMatrix.columns[first])
                    first++
                }
            }
            isRow = false
        }
        result.pointers.add(result.values.count())

        return result
    }

    /**
     * Умножение матрицы
     */
    fun multiplicationMatrix(firstMatrix: Matrix, secondMatrix: Matrix): Matrix? {
        if (firstMatrix.width != secondMatrix.height) {
            println("Матрицы не могут быть перемноженны, так как не являются совместимыми. Число столбцов первой матрицы не равно числу строк второй матрицы.")
            return null
        }

        val result = Matrix(firstMatrix.height, secondMatrix.width, mutableListOf())
        result.pointers.add(0)

        for (i in 0 until firstMatrix.pointers.size - 1) {
            var index = firstMatrix.pointers[i]

            val values = MutableList(secondMatrix.pointers.size) { 0 }

            while (index < firstMatrix.pointers[i + 1]) { // пока рассматриваемая строка не закончится
                for (l in 0 until secondMatrix.pointers.size - 1) {
                    if (l == firstMatrix.columns[index] && index < firstMatrix.pointers[i + 1]) {
                        for (m in secondMatrix.pointers[l] until secondMatrix.pointers[l + 1]) {
                            values[secondMatrix.columns[m]] += firstMatrix.values[index] * secondMatrix.values[m]
                        }
                        index++
                    }
                    if (index >= firstMatrix.pointers[i + 1]) {
                        break
                    }
                }
            }

            for (j in 0 until values.size) {
                if (values[j] != 0) {
                    result.values.add(values[j])
                    result.columns.add(j)
                }
            }
            result.pointers.add(result.values.count())
        }

        return result
    }
    
    private fun setPointers(result: Boolean, matrix: Matrix): Boolean {
        var isRow = result
        if (!isRow) {
            isRow = true
            matrix.pointers.add(matrix.values.size)
        }
        return isRow
    }
}