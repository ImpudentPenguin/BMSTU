package org.emakeeva.tisd

import io.qameta.allure.Allure.addAttachment
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class Algorithm401x401Test {

    @Test
    fun `call startBaseAlgorithm with size 401*401 and 401*401`() {
        val baseAlgorithm = LabWork.startBaseAlgorithm()

        addAttachment("Результат:", "text/plain", baseAlgorithm.second)
    }

    @Test
    fun `call startVinAlgorithm with size 401*401 and 401*401`() {
        val vinAlgorithm = LabWork.startVinAlgorithm()

        addAttachment("Результат:", "text/plain", vinAlgorithm.second)

        assertEquals(baseMatrix, vinAlgorithm.first)
    }

    @Test
    fun `call startOptimVinAlgorithm with size 401*401 and 401*401`() {
        val optimVinAlgorithm = LabWork.startOptimVinAlgorithm()

        addAttachment("Результат:", "text/plain", optimVinAlgorithm.second)

        assertEquals(baseMatrix, optimVinAlgorithm.first)
    }

    companion object {
        private const val SIZE_401x401 = "401*401"
        private var baseMatrix: Matrix? = null

        @BeforeAll
        @JvmStatic
        fun setup() {
            LabWork.read(SIZE_401x401, SIZE_401x401)
            baseMatrix = LabWork.startBaseAlgorithm(TypeAlgorithm.START).first
        }
    }
}