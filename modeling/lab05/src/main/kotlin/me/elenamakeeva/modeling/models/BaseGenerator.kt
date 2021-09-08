package me.elenamakeeva.modeling.models

import kotlin.random.Random

open class BaseGenerator(
    private val a: Double,
    private val b: Double,
    private val systemTime: Double
) {
    var time: Double = 0.0

    fun updateTime() {
        time -= systemTime
    }

    fun getNewTime(): Double {
        return a + (b - a) * Random.nextDouble()
    }
}