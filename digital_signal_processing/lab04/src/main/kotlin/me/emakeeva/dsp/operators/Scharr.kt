package me.emakeeva.dsp.operators

import me.emakeeva.dsp.Convolution
import me.emakeeva.dsp.convertToBufferedImage
import org.bytedeco.opencv.opencv_core.Mat
import java.awt.image.BufferedImage

class Scharr: Operator() {

    override fun apply(image: Mat): BufferedImage {
        val kernelX = listOf(
            listOf(-3.0, 0.0, 3.0),
            listOf(-10.0, 0.0, 10.0),
            listOf(-3.0, 0.0, 3.0)
        )

        val kernelY = listOf(
            listOf(-3.0, -10.0, -3.0),
            listOf(0.0, 0.0, 0.0),
            listOf(3.0, 10.0, 3.0)
        )
        return Convolution.apply(image.convertToBufferedImage(), kernelX, kernelY)
    }
}