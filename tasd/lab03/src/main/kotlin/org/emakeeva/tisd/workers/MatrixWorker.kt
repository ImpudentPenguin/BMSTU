package org.emakeeva.tisd.workers

import org.emakeeva.tisd.utils.MatrixInitException

class MatrixWorker {
    fun initMatrix(size: Int, value: Double): MutableList<MutableList<Double>> {
        validateMatrix(size, value)
        return MutableList(size) { MutableList(size) { value } }
    }

    private fun validateMatrix(size: Int, value: Double) {
        when {
            size !in 2..20 -> throw MatrixInitException("Matrix initialization error")
            value < 0.0 -> throw MatrixInitException("Matrix initialization with negative value is not possible")
        }
    }
}