package me.elenamakeeva.modeling.models

import java.util.*

data class Generator(
    var a: Double,
    var b: Double,
) {

    fun getTime() = a + (b - a) * Random().nextDouble()
}