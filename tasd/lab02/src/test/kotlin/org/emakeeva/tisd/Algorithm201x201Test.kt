package org.emakeeva.tisd

import io.qameta.allure.Allure.addAttachment
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class Algorithm201x201Test {

    @Test
    fun `call startBaseAlgorithm with size 201*201 and 201*201`() {
        val baseAlgorithm = LabWork.startBaseAlgorithm()

        addAttachment("Результат:", "text/plain", baseAlgorithm.second)
    }

    @Test
    fun `call startVinAlgorithm with size 201*201 and 201*201`() {
        val vinAlgorithm = LabWork.startVinAlgorithm()

        addAttachment("Результат:", "text/plain", vinAlgorithm.second)

        assertEquals(baseMatrix, vinAlgorithm.first)
    }

    @Test
    fun `call startOptimVinAlgorithm with size 201*201 and 201*201`() {
        val optimVinAlgorithm = LabWork.startOptimVinAlgorithm()

        addAttachment("Результат:", "text/plain", optimVinAlgorithm.second)

        assertEquals(baseMatrix, optimVinAlgorithm.first)
    }

    companion object {
        private const val SIZE_201x201 = "201*201"
        private var baseMatrix: Matrix? = null

        @BeforeAll
        @JvmStatic
        fun setup() {
            LabWork.read(SIZE_201x201, SIZE_201x201)
            baseMatrix = LabWork.startBaseAlgorithm(TypeAlgorithm.START).first
        }
    }
}