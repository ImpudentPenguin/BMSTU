package me.emakeeva.dsp.methods

import org.bytedeco.opencv.global.opencv_core.*
import org.bytedeco.opencv.global.opencv_imgproc.MORPH_CROSS
import org.bytedeco.opencv.global.opencv_imgproc as cv
import org.bytedeco.opencv.opencv_core.Mat
import org.bytedeco.opencv.opencv_core.Size

class Skeletoning : Method() {

    override fun apply(image: Mat): Mat {
        val skeleton = Mat(image.rows(), image.cols(), 0)
        val temp = Mat(image.rows(), image.cols(), 0)
        val opening = Mat(image.rows(), image.cols(), 0)
        var srcCopy = Mat(image.rows(), image.cols(), 0)
        val eroded = Mat(image.rows(), image.cols(), 0)
        val kernel = cv.getStructuringElement(MORPH_CROSS, Size(3, 3))
        image.copyTo(srcCopy)

        while (true) {
            cv.erode(srcCopy, opening, kernel)
            cv.dilate(opening, opening, kernel)
            subtract(srcCopy, opening, temp)
            cv.erode(srcCopy, eroded, kernel)
            bitwise_or(skeleton, temp, skeleton)
            srcCopy = eroded.clone()

            if (countNonZero(srcCopy) == 0) {
                break
            }
        }

        return skeleton
    }
}