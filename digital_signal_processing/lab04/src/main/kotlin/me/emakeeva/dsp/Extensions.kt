package me.emakeeva.dsp

import me.emakeeva.dsp.operators.Operator
import org.bytedeco.javacv.Java2DFrameConverter
import org.bytedeco.javacv.OpenCVFrameConverter
import org.bytedeco.opencv.global.opencv_core
import org.bytedeco.opencv.global.opencv_imgproc as cv
import org.bytedeco.opencv.opencv_core.Mat
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

fun BufferedImage.toByteArr(): ByteArray {
    val stream = ByteArrayOutputStream()
    ImageIO.write(this, "png", stream)
    stream.close()
    return stream.toByteArray()
}

fun Mat.convertToBufferedImage(): BufferedImage {
    return Java2DFrameConverter().convert(OpenCVFrameConverter.ToMat().convert(this))
}

fun BufferedImage.toGray(): Mat {
    val image = OpenCVFrameConverter.ToMat().convert(Java2DFrameConverter().convert(this))
    val gray = Mat(image.rows(), image.cols(), opencv_core.CV_8UC1)
    cv.cvtColor(image, gray, cv.CV_BGR2GRAY)

    return gray
}

fun BufferedImage.binary(): Mat {
    val image = OpenCVFrameConverter.ToMat().convert(Java2DFrameConverter().convert(this))
    val bin = Mat()
    cv.threshold(image, bin, 5.0, 255.0, cv.THRESH_BINARY)

    return bin
}

fun Mat.convert(operator: Operator): BufferedImage {
    return operator.apply(this)
}