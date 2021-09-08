package org.emakeeva.lab01

import kotlin.math.*

/**
 * Алгоритм получения псевдослучайных чисел с помощью линейного конгруэнтного метода
 * и оценка случайности
 */
object AlgorithmWorker {

    private const val factor = 1103515245 // множитель o <= factor < module
    private const val increment = 12345 // приращение o <= increment < module

    /**
     * Получение следующего n-разрядного числа
     */
    fun nextDigit(value: Int, from: Int, until: Int): Int = from + (abs(getResult(value)).toInt() % until)

    /**
     * Расчет коэффициента по самописному критерию оценки случайности
     */
    fun rateRandom(value: Int, count: Int, len: Int): Double {
        val res = (sqrt(count.toDouble() * value) / len) / ((1.0 - ln(len.toDouble())) / 2)
        return abs(res / 100)
    }

    private fun getResult(value: Int): Double {
        val module = 32767.0
        return (((factor * value) + increment) % module)
    }
}