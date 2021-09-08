package me.elenamakeeva.modeling.models

import org.apache.commons.math3.distribution.LogNormalDistribution

data class ServiceApparatus(
    var mu: Double,
    var sigma: Double
) {

    fun getTime(): Double {
        val logNormalDistribution = LogNormalDistribution(mu, sigma)
        return logNormalDistribution.sample()
    }
}