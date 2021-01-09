package org.emakeeva.tisd

import org.emakeeva.tisd.Utils.getMatrixString
import org.emakeeva.tisd.Utils.printResult
import org.emakeeva.tisd.workers.MainWorker

/**
 * Разреженный строчный формат: упаковка, распаковка, сложение двух матриц, произведение двух матриц
 */

fun main(args: Array<String>) {
    val mainWorker = MainWorker()

    val matrix = mutableListOf(
        mutableListOf(1, -1, 0, -3, 0),
        mutableListOf(-2, 5, 0, 0, 0),
        mutableListOf(0, 0, 4, 6, 4),
        mutableListOf(-4, 0, 2, 7, 0),
        mutableListOf(0, 8, 0, 0, -5)
    )

    mainWorker.apply {
        println("Исходная матрица:")
        println(getMatrixString(matrix))

        val resultMatrix = matrixPacking(matrix)
        println("Упакованная матрица:")
        printResult(resultMatrix.arrays)

        val firstUnpackingMatrix = matrixUnpacking(resultMatrix.width, resultMatrix.arrays)
        println("\nРаспакованная матрица:")
        println(getMatrixString(firstUnpackingMatrix))

        additionMatrix(resultMatrix, resultMatrix)?.let { result ->
            println("Сложение:")
            printResult(result.arrays)

            val resultUnpackingMatrix = matrixUnpacking(result.width, result.arrays)
            println("\nРаспакованная матрица:")
            println(getMatrixString(resultUnpackingMatrix))
        }

        multiplicationMatrix(resultMatrix, resultMatrix)?.let { result ->
            println("Умножение:")
            printResult(result.arrays)
            val resultUnpackingMatrix = matrixUnpacking(result.width, result.arrays)
            println("\nРаспакованная матрица:")
            println(getMatrixString(resultUnpackingMatrix))
        }
    }
}