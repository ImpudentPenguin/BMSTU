package me.emakeeva.dsp

import org.bytedeco.javacv.Java2DFrameConverter
import org.bytedeco.javacv.OpenCVFrameConverter
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