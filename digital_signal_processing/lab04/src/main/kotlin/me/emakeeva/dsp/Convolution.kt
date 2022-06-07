package me.emakeeva.dsp

import java.awt.Color
import java.awt.image.BufferedImage
import kotlin.math.max
import kotlin.math.min

object Convolution {

    fun apply(
        image: BufferedImage,
        matrixX: List<List<Double>>,
        matrixY: List<List<Double>>? = null
    ): BufferedImage {
        if (matrixY != null && (matrixX.size != matrixY.size || matrixX.first().size != matrixY.first().size))
            throw Exception("Размеры ядер не совпадают")

        val result = BufferedImage(image.width - 1, image.height - 1, image.type)

        for (w in 1 until image.width - 1) {
            for (h in 1 until image.height - 1) {
                var red = 0.0
                var green = 0.0
                var blue = 0.0

                for (i in matrixX.indices) {
                    for (j in 0 until matrixX.first().size) {
                        val rgb = image.getRGB(w + i - 1, h + j - 1)
                        val color = Color(rgb)
                        red += (color.red * matrixX[i][j] * (matrixY?.get(i)?.get(j) ?: 1.0))
                        blue += (color.blue * matrixX[i][j] * (matrixY?.get(i)?.get(j) ?: 1.0))
                        green += (color.green * matrixX[i][j] * (matrixY?.get(i)?.get(j) ?: 1.0))
                    }
                }

                val newRed = min(max(red.toInt(), 0), 255)
                val newGreen = min(max(green.toInt(), 0), 255)
                val newBlue = min(max(blue.toInt(), 0), 255)
                result.setRGB(w, h, Color(newRed, newGreen, newBlue).rgb)
            }
        }

        return result.binary().convertToBufferedImage()
    }
}