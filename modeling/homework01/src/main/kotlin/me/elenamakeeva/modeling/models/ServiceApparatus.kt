package me.elenamakeeva.modeling.models

import kotlin.math.ln
import kotlin.random.Random

data class ServiceApparatus(
    var lambda: Float
) {

    fun getTime() = ((-1f / lambda) * ln(Random.nextFloat()))
}