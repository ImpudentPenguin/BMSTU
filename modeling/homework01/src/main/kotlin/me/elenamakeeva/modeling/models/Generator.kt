package me.elenamakeeva.modeling.models

import kotlin.math.ln
import kotlin.random.Random

data class Generator(
    var lambda: Float
) {

    fun getTime() = ((-1f / lambda) * ln(Random.nextDouble()))
}