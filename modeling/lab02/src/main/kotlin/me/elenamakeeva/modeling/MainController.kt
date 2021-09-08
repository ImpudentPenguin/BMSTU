package me.elenamakeeva.modeling

import javafx.scene.chart.XYChart
import org.apache.commons.math3.special.Erf
import tornadofx.Controller
import tornadofx.observableListOf
import kotlin.math.*

class MainController : Controller() {

    /**
     * Функция плотности
     * Равномерное распределение
     */
    private fun uniformPMF(x: Float, a: Float, b: Float): Float {
        if (x < a || x > b)
            return 0f

        return 1f / (b - a)
    }

    /**
     * Функция распределения
     * Равномерное распределение
     */
    private fun uniformCDF(x: Float, a: Float, b: Float): Float {
        if (x < a)
            return 0f

        if (x > b)
            return 1f

        return (x - a) / (b - a)
    }

    /**
     * Функция распределения
     * Нормальное распределение
     */
    private fun normCDF(x: Double, mu: Double, sigma: Double): Double {
        return (1.0 / 2.0) * (1.0 + Erf.erf((x - mu) / (sqrt(2 * sigma.pow(2)))))
    }

    /**
     * Функция плотности
     * Нормальное распределение
     */
    private fun normPMF(x: Double, mu: Double, sigma: Double): Double {
        return (1 / (sigma * sqrt(PI))) * exp(-((x - mu).pow(2.0) / (2 * sigma.pow(2))))
    }

    /**
     * Получение значений нормального распределения
     */
    fun getNormValues(
        mu: Double,
        sigma: Double
    ): Pair<XYChart.Series<Number, Number>, XYChart.Series<Number, Number>> {
        val cdf: XYChart.Series<Number, Number> = XYChart.Series(observableListOf())
        val pmf: XYChart.Series<Number, Number> = XYChart.Series(observableListOf())

        for (i in 0 until 10) {
            val firstX = mu - i * sigma
            val secondX = mu + i * sigma

            cdf.data.add(XYChart.Data(firstX, normCDF(firstX, mu, sigma)))
            pmf.data.add(XYChart.Data(firstX, normPMF(firstX, mu, sigma)))

            cdf.data.add(XYChart.Data(secondX, normCDF(secondX, mu, sigma)))
            pmf.data.add(XYChart.Data(secondX, normPMF(secondX, mu, sigma)))
        }

        return cdf to pmf
    }

    /**
     * Получение значений равномерного распределения
     */
    fun getUniformValues(
        a: Float,
        b: Float
    ): Pair<XYChart.Series<Number, Number>, XYChart.Series<Number, Number>> {
        val size = (b - a) / 5
        val cdf: XYChart.Series<Number, Number> = XYChart.Series(observableListOf())
        val pmf: XYChart.Series<Number, Number> = XYChart.Series(observableListOf())

        generateSequence(a - size, { value ->
            (value + 0.01f).takeIf { value < b + size }
        }).forEach {
            cdf.data.add(XYChart.Data(it, uniformCDF(it, a, b)))
            pmf.data.add(XYChart.Data(it, uniformPMF(it, a, b)))
        }

        return cdf to pmf
    }
}