package me.emakeeva.math_modeling

import javafx.scene.chart.XYChart
import tornadofx.Controller
import tornadofx.observableListOf
import kotlin.math.*

class MainController : Controller() {
    private val limits = 0.0 to 1.0

    private val twoIntegral: (Double, Double, Double) -> Double = { x, y, tao ->
        (ln(x.pow(2) * tao + y.pow(2) + 1))
    }

    private val threeIntegral: (Double, Double?, Double?, Double) -> Double = { x, _, _, tao ->
        (1 - x.pow(2)) * tao
    }

    fun getSequentialIntegrationResult(
        n: Int,
        m: Int,
        k: Int,
        taoList: List<Double>
    ): XYChart.Series<Number, Number> {
        val result: XYChart.Series<Number, Number> = XYChart.Series(observableListOf())

        taoList.forEach { tao ->
            val func: (Double, Double?, Double?) -> Double = { z, y, x ->
                threeIntegral.invoke(x ?: 0.0, y, z, tao)
            }

            val firstInternalIntegral: (Double, Double?, Double?) -> Double = { y, x, _ ->
                simpsonMethod(func, k, limits.first, limits.second, y, x)
            }

            val secondInternalIntegral: (Double, Double?, Double?) -> Double = { x, y, _ ->
                simpsonMethod(firstInternalIntegral, m, limits.first, limits.second, x, y)
            }

            result.data.add(
                XYChart.Data(
                    tao, simpsonMethod(
                        func = secondInternalIntegral,
                        n = n,
                        a = limits.first,
                        b = limits.second,
                        firstExtraParam = null,
                        secondExtraParam = null
                    )
                )
            )
        }

        return result
    }

    fun getCellMethodResult(
        n: Int,
        m: Int,
        taoList: List<Double>
    ): XYChart.Series<Number, Number> {
        val result: XYChart.Series<Number, Number> = XYChart.Series(observableListOf())

        taoList.forEach { tao ->
            val func: (Double, Double) -> Double = { x, y ->
                twoIntegral.invoke(x, y, tao)
            }

            cellMethod(
                func = func,
                n = n,
                m = m,
                a = limits.first,
                b = limits.second,
                c = limits.first,
                d = limits.second
            )

            result.data.add(
                XYChart.Data(
                    tao, cellMethod(
                        func = func,
                        n = n,
                        m = m,
                        a = limits.first,
                        b = limits.second,
                        c = limits.first,
                        d = limits.second
                    )
                )
            )
        }

        return result
    }

    /**
     * Вычисление с помощью метода Симпсона
     * @param func подинтегральная функция
     * @param n количество интервалов
     * @param a предел интегрирования
     * @param b предел интегрирования
     * @param firstExtraParam дополнительный параметр (x, y, z)
     * @param secondExtraParam дополнительный параметр (x, y, z)
     */
    private fun simpsonMethod(
        func: (Double, Double?, Double?) -> Double,
        n: Int,
        a: Double,
        b: Double,
        firstExtraParam: Double?,
        secondExtraParam: Double?
    ): Double {
        val h = (b - a) / (n - 1)
        var temp = a

        return (h / 3.0) * (0 until ((n - 1) / 2)).fold(0.0) { total, _ ->
            val result = func(temp, firstExtraParam, secondExtraParam) + 4 * func(
                temp + h,
                firstExtraParam,
                secondExtraParam
            ) + func(temp + 2.0 * h, firstExtraParam, secondExtraParam)
            temp += 2.0 * h
            total + result
        }
    }

    /**
     * Вычисление двойного интеграла методом ячеек
     * @param func подинтегральная функция
     * @param n количество разбиений по горизонтали
     * @param m количество разбиений по вертикали
     * @param a предел интегрирования внешнего направления
     * @param b предел интегрирования внешнего направления
     * @param c предел интегрирования внутреннего направления
     * @param d предел интегрирования внутреннего направления
     */
    private fun cellMethod(
        func: (Double, Double) -> Double,
        n: Int,
        m: Int,
        a: Double,
        b: Double,
        c: Double,
        d: Double
    ): Double {
        val h1 = (b - a) / n
        val h2 = (d - c) / m
        var prevX = a
        var prevY = c
        val s = h1 * h2
        var result = 0.0

        for (i in 1 until n) {
            for (j in 1 until m) {
                val currentX = a + i * h1
                val currentY = c + j * h2

                val averageX = (prevX + currentX) / 2
                val averageY = (prevY + currentY) / 2

                prevX = currentX
                prevY = currentY

                result += (s * func(averageX, averageY))
            }
        }

        return result
    }
}