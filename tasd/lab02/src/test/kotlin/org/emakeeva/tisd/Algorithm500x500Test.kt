package org.emakeeva.tisd

import io.qameta.allure.Allure.addAttachment
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class Algorithm500x500Test {

    @Test
    fun `call startBaseAlgorithm with size 500*500 and 500*500`() {
        val baseAlgorithm = LabWork.startBaseAlgorithm()

        addAttachment("Результат:", "text/plain", baseAlgorithm.second)
    }

    @Test
    fun `call startVinAlgorithm with size 500*500 and 500*500`() {
        val vinAlgorithm = LabWork.startVinAlgorithm()

        addAttachment("Результат:", "text/plain", vinAlgorithm.second)

        assertEquals(baseMatrix, vinAlgorithm.first)
    }

    @Test
    fun `call startOptimVinAlgorithm with size 500*500 and 500*500`() {
        val optimVinAlgorithm = LabWork.startOptimVinAlgorithm()

        addAttachment("Результат:", "text/plain", optimVinAlgorithm.second)

        assertEquals(baseMatrix, optimVinAlgorithm.first)
    }

    companion object {
        private const val SIZE_500x500 = "500*500"
        private var baseMatrix: Matrix? = null

        @BeforeAll
        @JvmStatic
        fun setup() {
            LabWork.read(SIZE_500x500, SIZE_500x500)
            baseMatrix = LabWork.startBaseAlgorithm(TypeAlgorithm.START).first
        }
    }
}