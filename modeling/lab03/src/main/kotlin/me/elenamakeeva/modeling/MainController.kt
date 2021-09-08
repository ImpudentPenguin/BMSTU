package me.elenamakeeva.modeling

import org.apache.commons.math3.linear.*
import tornadofx.Controller
import kotlin.math.abs

typealias Times = List<Double>
typealias Probabilities = List<List<Double>>

class MainController : Controller() {

    private val deltaTime = 1e-3

    fun getProbabilities(matrix: List<List<Double>>): DoubleArray {
        val coefficient = getCoefficientMatrix(matrix)
        val coefficients: RealMatrix = Array2DRowRealMatrix(coefficient, false)
        val doubleArr = DoubleArray(coefficient.size) { 0.0 }
        doubleArr[coefficient.size - 1] = 1.0
        val constants: RealVector = ArrayRealVector(doubleArr, false)
        val solution = SingularValueDecomposition(coefficients).solver.solve(constants)

        return (solution as ArrayRealVector).dataRef
    }

    fun getTimes(matrix: List<List<Double>>, solution: DoubleArray): Times {
        val startProbabilities = MutableList(matrix.size) { 0.0 }
        val times = MutableList(matrix.size) { 0.0 }
        val mu = matrix.sumOf { it.sum() } * 10
        val ro = solution.toMutableList().map { it / mu } // среднее число заявок, приходящие в систему за среднее время обслуживания 1 заявки
        var currentTime = 0.0
        startProbabilities[0] = 1.0

        while (times.firstOrNull { it == 0.0 } != null) {
            val increment = dp(matrix, startProbabilities)
            for (i in increment.indices) {
                if (times[i] == 0.0 && increment[i] <= ro[i]
                    && abs(startProbabilities[i] - solution[i]) <= ro[i])
                    times[i] = currentTime
                startProbabilities[i] += increment[i]
            }
            currentTime += deltaTime
        }

        return times
    }

    fun getDataForGraph(matrix: List<List<Double>>): Pair<Times, Probabilities> {
        val startProbabilities = MutableList(matrix.size) { 0.0 }
        val probabilities = mutableListOf<MutableList<Double>>()
        val times = mutableListOf<Double>()
        var currentTime = 0.0
        startProbabilities[0] = 1.0

        while (currentTime < matrix.size) {
            probabilities.add(startProbabilities.toMutableList())
            val increment = dp(matrix, startProbabilities)
            for (i in increment.indices)
                startProbabilities[i] += increment[i]

            currentTime += deltaTime
            times.add(currentTime)
        }

        return times to probabilities
    }

    private fun getCoefficientMatrix(matrix: List<List<Double>>): Array<DoubleArray> {
        val size = matrix.size
        val result = MutableList(size) { DoubleArray(size) { 0.0 } }

        for (i in 0 until size)
            for (j in 0 until size)
                result[i][i] -= matrix[i][j]

        for (i in 0 until size)
            for (j in 0 until size)
                result[j][i] += matrix[i][j]

        val normEquation = DoubleArray(matrix[0].size) { 1.0 }
        result.add(normEquation)

        return result.toTypedArray()
    }

    private fun dp(matrix: List<List<Double>>, probabilities: List<Double>): List<Double> {
        val dp = mutableListOf<Double>()

        for (i in matrix.indices) {
            var increment = 0.0
            for (j in matrix.indices)
                increment += if (i == j)
                    probabilities[j] * (matrix[i][i] - matrix[i].sum())
                else probabilities[j] * matrix[j][i]

            dp.add(increment * deltaTime)
        }

        return dp
    }
}