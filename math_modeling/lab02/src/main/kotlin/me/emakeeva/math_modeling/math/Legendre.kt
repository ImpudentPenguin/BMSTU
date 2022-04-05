package me.emakeeva.math_modeling.math

import kotlin.math.PI
import kotlin.math.cos

typealias Weights = DoubleArray
typealias Roots = DoubleArray

object Legendre {

    private var coefficients = arrayOf<DoubleArray>()

    fun getRootsAndWeights(N: Int): Pair<Roots, Weights> {
        val roots = DoubleArray(N)
        val weights = DoubleArray(N)
        coefficients = Array(N + 1) { DoubleArray(N + 1) }

        coefficients[0][0] = 1.0
        coefficients[1][1] = 1.0
        for (n in 2..N) {
            coefficients[n][0] = (1 - n) * coefficients[n - 2][0] / n
            for (i in 1..n)
                coefficients[n][i] = ((2 * n - 1) * coefficients[n - 1][i - 1] - (n - 1) * coefficients[n - 2][i]) / n
        }

        var x: Double
        var x1: Double
        for (i in 1..N) {
            x = cos(PI * (i - 0.25) / (N + 0.5))
            do {
                x1 = x
                x -= evaluate(N, x) / diff(N, x)
            } while (x != x1)

            x1 = diff(N, x)
            roots[i - 1] = x
            weights[i - 1] = 2 / ((1 - x * x) * x1 * x1)
        }

        return roots to weights
    }

    private fun evaluate(n: Int, x: Double) =
        (n downTo 1).fold(coefficients[n][n]) { s, i -> s * x + coefficients[n][i - 1] }

    private fun diff(n: Int, x: Double) = n * (x * evaluate(n, x) - evaluate(n - 1, x)) / (x * x - 1)
}