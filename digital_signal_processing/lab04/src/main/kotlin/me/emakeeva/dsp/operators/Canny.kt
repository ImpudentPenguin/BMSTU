package me.emakeeva.dsp.operators

import me.emakeeva.dsp.convertToBufferedImage
import org.bytedeco.opencv.global.opencv_imgproc as cv
import org.bytedeco.opencv.opencv_core.Mat
import java.awt.image.BufferedImage

class Canny: Operator() {

    override fun apply(image: Mat): BufferedImage {
        val edges = Mat(image.cols(), image.rows())
        cv.Canny(image, edges, 50.0, 150.0)

        return edges.convertToBufferedImage()
    }
}