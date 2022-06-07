package me.emakeeva.dsp.operators

import me.emakeeva.dsp.Convolution
import me.emakeeva.dsp.convertToBufferedImage
import org.bytedeco.opencv.opencv_core.Mat
import java.awt.image.BufferedImage

class Roberts: Operator() {

    override fun apply(image: Mat): BufferedImage {
        val kernel1 = listOf(
            listOf(1.0, 0.0),
            listOf(0.0, -1.0)
        )

        val kernel2 = listOf(
            listOf(0.0, 1.0),
            listOf(-1.0, 0.0)
        )

        return Convolution.apply(image.convertToBufferedImage(), kernel1, kernel2)
    }
}