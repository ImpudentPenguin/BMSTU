package me.emakeeva.dsp.methods

import org.bytedeco.opencv.global.opencv_imgproc as cv
import org.bytedeco.opencv.global.opencv_imgproc.MORPH_DILATE
import org.bytedeco.opencv.opencv_core.Mat
import org.bytedeco.opencv.opencv_core.Size

class Dilate: Method() {

    override fun apply(image: Mat): Mat {
        val kernel = cv.getStructuringElement(MORPH_DILATE, Size(3, 3))
        cv.dilate(image, image, kernel)

        return image
    }
}