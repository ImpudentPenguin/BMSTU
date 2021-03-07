package org.emakeeva.tisd

import io.qameta.allure.Allure.addAttachment
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class Algorithm501x501Test {

    @Test
    fun `call startBaseAlgorithm with size 501*501 and 501*501`() {
        val baseAlgorithm = LabWork.startBaseAlgorithm()

        addAttachment("Результат:", "text/plain", baseAlgorithm.second)
    }

    @Test
    fun `call startVinAlgorithm with size 501*501 and 501*501`() {
        val vinAlgorithm = LabWork.startVinAlgorithm()

        addAttachment("Результат:", "text/plain", vinAlgorithm.second)

        assertEquals(baseMatrix, vinAlgorithm.first)
    }

    @Test
    fun `call startOptimVinAlgorithm with size 501*501 and 501*501`() {
        val optimVinAlgorithm = LabWork.startOptimVinAlgorithm()

        addAttachment("Результат:", "text/plain", optimVinAlgorithm.second)

        assertEquals(baseMatrix, optimVinAlgorithm.first)
    }

    companion object {
        private const val SIZE_501x501 = "501*501"
        private var baseMatrix: Matrix? = null

        @BeforeAll
        @JvmStatic
        fun setup() {
            LabWork.read(SIZE_501x501, SIZE_501x501)
            baseMatrix = LabWork.startBaseAlgorithm(TypeAlgorithm.START).first
        }
    }
}