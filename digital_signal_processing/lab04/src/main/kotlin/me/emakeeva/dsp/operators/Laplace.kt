package me.emakeeva.dsp.operators

import me.emakeeva.dsp.convertToBufferedImage
import org.bytedeco.opencv.global.opencv_imgproc.GaussianBlur
import org.bytedeco.opencv.opencv_core.Mat
import org.bytedeco.opencv.opencv_core.Size
import java.awt.Color
import java.awt.image.BufferedImage
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Laplace : Operator() {

    override fun apply(image: Mat): BufferedImage {
        val kernel = listOf(
            listOf(1.0, 1.0, 1.0),
            listOf(1.0, -8.0, 1.0),
            listOf(1.0, 1.0, 1.0)
        )

        GaussianBlur(image, image, Size(3, 3), 3.0)

        return measureContrast(image.convertToBufferedImage(), kernel)
    }

    private fun measureContrast(image: BufferedImage, kernel: List<List<Double>>): BufferedImage {
        val result = BufferedImage(image.width, image.height, image.type)
        val temp = BufferedImage(image.width, image.height, image.type)

        for (w in 1 until image.width - 1) {
            for (h in 1 until image.height - 1) {
                var red = 0.0
                var green = 0.0
                var blue = 0.0

                var total = 0
                for (i in kernel.indices) {
                    for (j in 0 until kernel.first().size) {
                        val rgb = image.getRGB(w + i - 1, h + j - 1)
                        val color = Color(rgb)
                        red += (color.red * kernel[i][j])
                        blue += (color.blue * kernel[i][j])
                        green += (color.green * kernel[i][j])
                    }
                }

                val newRed = min(max(red.toInt(), 0), 255)
                val newGreen = min(max(green.toInt(), 0), 255)
                val newBlue = min(max(blue.toInt(), 0), 255)
                val rgb = Color(newRed, newGreen, newBlue).rgb

                total += rgb
                temp.setRGB(w, h, rgb)

                val thresh = total * 4 / 100
                var laplaceCount = 0
                if (isEdge(temp.getRGB(w -1, h), temp.getRGB(w + 1, h), thresh))
                    laplaceCount += 1

                if (isEdge(temp.getRGB(w, h + 1), temp.getRGB(w, h - 1), thresh))
                    laplaceCount += 1

                if (isEdge(temp.getRGB(w + 1, h + 1), temp.getRGB(w - 1, h - 1), thresh))
                    laplaceCount += 1

                if (isEdge(temp.getRGB(w + 1, h - 1), temp.getRGB(w - 1, h + 1), thresh))
                    laplaceCount += 1

                val color = if (laplaceCount >= 2) Color.WHITE else Color.BLACK
                result.setRGB(w, h, color.rgb)
            }
        }

        return result
    }

    private fun isEdge(a: Int, b: Int, thresh: Int): Boolean {
        return (a * b) < 0 && abs(a - b) > thresh
    }
}