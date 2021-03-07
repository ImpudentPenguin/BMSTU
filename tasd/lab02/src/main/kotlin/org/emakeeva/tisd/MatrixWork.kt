package org.emakeeva.tisd

typealias FirstMatrix = Matrix
typealias SecondMatrix = Matrix

/**
 * Алгоритмы умножения матриц
 */
class MatrixWork {

    /**
     * Переменные времени
     */
    private val logTime = LogTime()
    private var startCpu: Long = 0
    private var startTime: Long = 0
    private var time: Long = 0
    private var cpuTime: Long = 0

    /**
     * Базовый алгоритм матриц
     * @param matrix пара матриц для умножения
     */
    fun baseMultiplyMatrix(matrix: Pair<FirstMatrix, SecondMatrix>): Matrix? {
     //   setTime()

        val heightFirstMatrix = matrix.first.height
        val widthFirstMatrix = matrix.first.width
        val widthSecondMatrix = matrix.second.width

        if (heightFirstMatrix != widthSecondMatrix)
            return null

        val result = Matrix(widthSecondMatrix, heightFirstMatrix)

        for (row in 0 until heightFirstMatrix)
            for (column in 0 until widthSecondMatrix)
                for (columnSecond in 0 until widthFirstMatrix)
                    result[row, column] = result[row, column] + matrix.first[row, columnSecond] * matrix.second[columnSecond, column]

        return result
    }

    /**
     * Алгоритм Винограда
     * @param matrix пара матриц для умножения
     */
    fun vinMultiplyMatrix(matrix: Pair<FirstMatrix, SecondMatrix>): Matrix? {
      //  setTime()

        val heightFirstMatrix = matrix.first.height // M
        val widthFirstMatrix = matrix.first.width // N
        val widthSecondMatrix = matrix.second.width // Q

        if (heightFirstMatrix != widthSecondMatrix)
            return null

        val result = Matrix(heightFirstMatrix, widthSecondMatrix)
        val arrFirstMatrix = MutableList(heightFirstMatrix) { 0 }
        val arrSecondMatrix = MutableList(widthFirstMatrix) { 0 }

        for (i in 0 until heightFirstMatrix) {
            arrFirstMatrix[i] = 0
            for (k in 0 until widthFirstMatrix / 2) {
                arrFirstMatrix[i] = arrFirstMatrix[i] + matrix.first[i, k * 2] * matrix.first[i, k * 2 + 1]
            }
        }

        for (i in 0 until heightFirstMatrix) {
            arrSecondMatrix[i] = 0
            for (k in 0 until widthFirstMatrix / 2) {
                arrSecondMatrix[i] = arrSecondMatrix[i] + matrix.second[k * 2, i] * matrix.second[k * 2 + 1, i]
            }
        }

        for (i in 0 until heightFirstMatrix) {
            for (j in 0 until widthSecondMatrix) {
                result[i, j] = -arrFirstMatrix[i] - arrSecondMatrix[j]
                for (k in 0 until widthFirstMatrix / 2) {
                    result[i, j] = result[i, j] + (matrix.first[i, 2 * k + 1] + matrix.second[2 * k, j]) * (matrix.first[i, 2 * k] + matrix.second[2 * k + 1, j])
                }
            }
        }

        if (widthFirstMatrix % 2 != 0) {
            for (i in 0 until heightFirstMatrix) {
                for (j in 0 until widthSecondMatrix) {
                    result[i, j] = result[i, j] + matrix.first[i, widthFirstMatrix - 1] * matrix.second[widthFirstMatrix - 1, j]
                }
            }
        }

        return result
    }

    /**
     * Оптимизированный алгоритм Винограда
     * @param matrix пара матриц для умножения
     */
    fun optimVinMultiplyMatrix(matrix: Pair<FirstMatrix, SecondMatrix>): Matrix? {
     //   setTime()

        val heightFirstMatrix = matrix.first.height // M
        val widthFirstMatrix = matrix.first.width // N
        val widthSecondMatrix = matrix.second.width // Q

        if (heightFirstMatrix != widthSecondMatrix)
            return null

        val result = Matrix(heightFirstMatrix, widthSecondMatrix)
        val arrFirstMatrix = MutableList(heightFirstMatrix) { 0 }
        val arrSecondMatrix = MutableList(widthSecondMatrix) { 0 }

        for (i in 0 until heightFirstMatrix) {
            arrFirstMatrix[i] = 0
            for (k in 1 until widthFirstMatrix step 2) {
                arrFirstMatrix[i] -= matrix.first[i, k - 1] * matrix.first[i, k]
            }
        }

        for (i in 0 until heightFirstMatrix) {
            arrSecondMatrix[i] = 0
            for (k in 1 until widthSecondMatrix step 2) {
                arrSecondMatrix[i] -= matrix.second[k - 1, i] * matrix.second[k, i]
            }
        }

        for (i in 0 until heightFirstMatrix) {
            for (j in 0 until widthSecondMatrix) {
                var buf = arrFirstMatrix[i] + arrSecondMatrix[j]
                for (k in 1 until widthFirstMatrix step 2) {
                    buf += (matrix.first[i, k - 1] + matrix.second[k, j]) * (matrix.first[i, k] + matrix.second[k - 1, j])
                }

                result[i, j] = buf
            }
        }

        if (widthFirstMatrix % 2 != 0) {
            val index = widthFirstMatrix - 1
            for (i in 0 until heightFirstMatrix) {
                for (j in 0 until widthSecondMatrix) {
                    result[i, j] += matrix.first[i, index] * matrix.second[index, j]
                }
            }
        }

        return result
    }

    fun createMatrix(height: Int, width: Int): Matrix {
        val matrix = Matrix(width, height)
        for (row in 0 until height)
            for (column in 0 until width)
                matrix[row, column] = (-10..20).random()

        return matrix
    }

    private fun setTime() {
        logTime.cpuTimeEnable(true)
        time = 0
        cpuTime = 0
        startTime = logTime.getStartTime()
        startCpu = logTime.getCpuTime()
    }
}