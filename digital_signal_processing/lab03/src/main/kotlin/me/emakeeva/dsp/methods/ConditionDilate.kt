package me.emakeeva.dsp.methods

import org.bytedeco.opencv.global.opencv_core
import org.bytedeco.opencv.global.opencv_core.countNonZero
import org.bytedeco.opencv.global.opencv_imgproc as cv
import org.bytedeco.opencv.opencv_core.Mat
import org.bytedeco.opencv.opencv_core.Size

class ConditionDilate : Method() {

    override fun apply(image: Mat): Mat {
        val firstKernel = cv.getStructuringElement(cv.MORPH_RECT, Size(3, 3))
        val secondKernel = cv.getStructuringElement(cv.MORPH_RECT, Size(5, 5))
        var conditionalErosion = Mat()
        val result = Mat(image.rows(), image.cols(), 0)
        var temp = Mat(image.rows(), image.cols(), 0)
        val temp2 = Mat(image.rows(), image.cols(), 0)

        cv.erode(image, conditionalErosion, firstKernel)

        while (true) {
            cv.dilate(conditionalErosion, conditionalErosion, secondKernel)
            opencv_core.bitwise_and(image, conditionalErosion, result)
            opencv_core.subtract(result, temp, temp2)

            conditionalErosion = result.clone()

            if (countNonZero(temp2) == 0) {
                break
            }

            temp = result.clone()
        }

        return result
    }
}