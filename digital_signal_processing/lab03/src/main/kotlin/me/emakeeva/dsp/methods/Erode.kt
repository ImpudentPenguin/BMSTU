package me.emakeeva.dsp.methods

import org.bytedeco.opencv.opencv_core.Mat
import org.bytedeco.opencv.global.opencv_imgproc as cv
import org.bytedeco.opencv.opencv_core.Size

class Erode: Method() {

    override fun apply(image: Mat): Mat {
        val kernel = cv.getStructuringElement(cv.MORPH_ERODE, Size(3, 3))
        cv.erode(image, image, kernel)

        return image
    }
}