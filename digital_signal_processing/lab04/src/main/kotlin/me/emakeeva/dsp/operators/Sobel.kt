package me.emakeeva.dsp.operators

import me.emakeeva.dsp.Convolution
import me.emakeeva.dsp.convertToBufferedImage
import org.bytedeco.opencv.opencv_core.Mat
import java.awt.image.BufferedImage

class Sobel: Operator() {

    override fun apply(image: Mat): BufferedImage {
        val kernelX = listOf(
            listOf(-1.0, 0.0, 1.0),
            listOf(-2.0, 0.0, 2.0),
            listOf(-1.0, 0.0, 1.0)
        )

        val kernelY = listOf(
            listOf(-1.0, -2.0, -1.0),
            listOf(0.0, 0.0, 0.0),
            listOf(1.0, 2.0, 1.0)
        )
        return Convolution.apply(image.convertToBufferedImage(), kernelX, kernelY)
    }
}