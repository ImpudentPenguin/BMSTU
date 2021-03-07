package org.emakeeva.tisd

import io.qameta.allure.Allure.addAttachment
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class Algorithm2x2Test {

    @Test
    fun `call startBaseAlgorithm with size 2*3 and 3*2`() {
        val baseAlgorithm = LabWork.startBaseAlgorithm()

        addAttachment("Результат:", "text/plain", baseAlgorithm.second)
    }

    @Test
    fun `call startVinAlgorithm with size 2*3 and 3*2`() {
        val vinAlgorithm = LabWork.startVinAlgorithm()

        addAttachment("Результат:", "text/plain", vinAlgorithm.second)

        assertEquals(baseMatrix, vinAlgorithm.first)
    }

    @Test
    fun `call startOptimVinAlgorithm with size 2*3 and 3*2`() {
        val optimVinAlgorithm = LabWork.startOptimVinAlgorithm()

        addAttachment("Результат:", "text/plain", optimVinAlgorithm.second)

        assertEquals(baseMatrix, optimVinAlgorithm.first)
    }

    companion object {
        private const val SIZE_2x3 = "2*3"
        private const val SIZE_3x2 = "3*2"
        private var baseMatrix: Matrix? = null

        @BeforeAll
        @JvmStatic
        fun setup() {
            LabWork.read(SIZE_2x3, SIZE_3x2)
            baseMatrix = LabWork.startBaseAlgorithm(TypeAlgorithm.START).first
        }
    }
}