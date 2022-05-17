package me.emakeeva.dsp.methods

import me.emakeeva.dsp.toByteArr
import java.awt.Color
import java.awt.image.BufferedImage

class Bradley {

    fun threshold(bufferedImage: BufferedImage): ByteArray {
        val width = bufferedImage.width
        val height = bufferedImage.height
        val result = BufferedImage(width, height, bufferedImage.type)
        val brightness = MutableList(width) { MutableList(height) { 0 } }
        val integral = MutableList(width) { MutableList(height) { 0 } }
        val s = width / 8
        var sum: Int

        for (i in 0 until width) {
            sum = 0

            for (j in 0 until height) {
                val color = Color(bufferedImage.getRGB(i, j))
                brightness[i][j] = (color.red + color.green + color.blue) / 3
                sum += brightness[i][j]

                if (i == 0) {
                    integral[i][j] = sum
                } else {
                    integral[i][j] = integral[i - 1][j] + sum
                }
            }
        }

        for (i in 0 until width) {
            for (j in 0 until height) {
                var x1 = i - s / 2
                var x2 = i + s / 2
                var y1 = j - s / 2
                var y2 = j + s / 2

                if (x1 < 1)
                    x1 = 1
                if (x2 >= width)
                    x2 = width - 1
                if (y1 < 1)
                    y1 = 1
                if (y2 >= height)
                    y2 = height - 1

                val count = (x2 - x1) * (y2 - y1)
                sum = integral[x2][y2] - integral[x2][y1 - 1] - integral[x1 - 1][y2] + integral[x1 - 1][y1 - 1]
                if (brightness[i][j] * count <= sum * (100 - 0.15) / 100) {
                    result.setRGB(i, j, Color.BLACK.rgb)
                } else {
                    result.setRGB(i, j, Color.WHITE.rgb)
                }
            }
        }

        return result.toByteArr()
    }
}