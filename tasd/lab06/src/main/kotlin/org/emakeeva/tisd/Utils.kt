package org.emakeeva.tisd

object Utils {
    fun getMatrixString(matrix: List<List<Char>>): String {
        val stringBuilder = StringBuilder()
        for (element in matrix) {
            stringBuilder.append(element)
            stringBuilder.append("\n")
        }

        return stringBuilder.toString()
    }
}