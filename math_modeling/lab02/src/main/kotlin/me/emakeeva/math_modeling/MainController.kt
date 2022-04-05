package me.emakeeva.math_modeling

import javafx.scene.chart.XYChart
import me.emakeeva.math_modeling.math.Legendre
import tornadofx.Controller
import tornadofx.observableListOf
import kotlin.math.*

class MainController : Controller() {

    private val limits = 0.0 to PI / 2

    private val extraFunc: (Double, Double) -> Double = { x, y ->
        (2 * cos(x)) / (1 - sin(x).pow(2) * cos(y).pow(2))
    }

    private val mainFunc: (Double, Double, Double) -> Double = { y, x, tao ->
        (4 / Math.PI) * (1 - exp(-tao * extraFunc(x, y))) * cos(x) * sin(x)
    }

    fun gaussMethod(
        func: (Double, Double?) -> Double,
        n: Int,
        a: Double,
        b: Double,
        extraParam: Double? = null
    ): Double {
        val legendre = Legendre.getRootsAndWeights(n)
        val roots = legendre.first
        val weights = legendre.second

        return ((b - a) / 2) * (0 until n).fold(0.0) { total, i ->
            total + weights[i] * func(calculate(a, b, roots[i]), extraParam)
        }
    }

    fun simpsonMethod(
        func: (Double, Double?) -> Double,
        n: Int,
        a: Double,
        b: Double,
        extraParam: Double? = null
    ): Double {
        val h = (b - a) / (n - 1)
        var temp = a

        return (h / 3.0) * (0 until ((n - 1) / 2)).fold(0.0) { total, _ ->
            val result = func(temp, extraParam) + 4 * func(temp + h, extraParam) + func(temp + 2.0 * h, extraParam)
            temp += 2.0 * h
            total + result
        }
    }

    fun getResult(
        n: Int,
        m: Int,
        taoList: List<Double>,
        firstMethod: (func: (Double, Double?) -> Double, n: Int, a: Double, b: Double, x: Double?) -> Double,
        secondMethod: (func: (Double, Double?) -> Double, n: Int, a: Double, b: Double, x: Double?) -> Double
    ): XYChart.Series<Number, Number> {
        val result: XYChart.Series<Number, Number> = XYChart.Series(observableListOf())

        taoList.forEach { tao ->
            val func: (Double, Double?) -> Double = { x, y ->
                mainFunc.invoke(x, y ?: 0.0, tao)
            }

            val internalIntegral: (Double, Double?) -> Double = { x, _ ->
                secondMethod.invoke(func, m, limits.first, limits.second, x)
            }

            result.data.add(
                XYChart.Data(tao, firstMethod.invoke(internalIntegral, n, limits.first, limits.second, null))
            )
        }

        return result
    }

    private fun calculate(a: Double, b: Double, t: Double): Double = (a + b) / 2 + ((b - a) / 2) * t
}