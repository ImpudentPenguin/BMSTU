package me.emakeeva.dsp.methods

import me.emakeeva.dsp.convertToBufferedImage
import me.emakeeva.dsp.toByteArr
import org.bytedeco.javacv.Java2DFrameConverter
import org.bytedeco.javacv.OpenCVFrameConverter
import org.bytedeco.opencv.global.opencv_core
import org.bytedeco.opencv.global.opencv_imgproc
import org.bytedeco.opencv.opencv_core.Mat
import java.awt.Color
import java.awt.image.BufferedImage
import kotlin.math.pow

class Otsu {

     fun thresholdOpenCV(bufferedImage: BufferedImage, min: Int, max: Int): ByteArray {
        val image = OpenCVFrameConverter.ToMat().convert(Java2DFrameConverter().convert(bufferedImage))
        val gray = Mat(image.rows(), image.cols(), opencv_core.CV_8UC1)
        val bin = Mat()
        opencv_imgproc.cvtColor(image, gray, opencv_imgproc.CV_BGR2GRAY)

        val T = opencv_imgproc.threshold(gray, bin, min.toDouble(), max.toDouble(), opencv_imgproc.THRESH_OTSU)

        println("T in openCV = $T")

        return bin.convertToBufferedImage().toByteArr()
    }

    fun threshold(bufferedImage: BufferedImage): ByteArray {
        val result = BufferedImage(bufferedImage.width, bufferedImage.height, bufferedImage.type)
        val histogram = generateHistogram(bufferedImage)
        var sum = 0
        histogram.forEachIndexed { index, i ->
            sum += i * index
        }

        var T = 0
        var Smax = -1.0
        val N = bufferedImage.height
        val M = bufferedImage.width
        val SM = (N * M).toDouble()
        var c1 = 0.0
        var c2 = SM
        var SI1 = 0.0
        var SI2 = sum.toDouble()

        for (t in 0 until histogram.size - 1) {
            val newbin = histogram[t]
            c1 += newbin
            c2 -= newbin

            val w1 = c1 / SM
            val w2 = 1 - w1

            SI1 += newbin * t
            SI2 -= newbin * t

            val mu1 = SI1 / c1
            val mu2 = SI2 / c2

            val s = w1 * w2 * (mu1 - mu2).pow(2)

            if (s > Smax) {
                Smax = s
                T = t
            }
        }

        for (i in 0 until bufferedImage.width) {
            for (j in 0 until bufferedImage.height) {
                val color = Color(bufferedImage.getRGB(i, j))
                val red = color.red
                var newPixel = if (red > T) 255 else 0
                newPixel = colorToRGB(color.alpha, newPixel, newPixel, newPixel)
                result.setRGB(i, j, newPixel)
            }
        }

        println("T in own implementation = $T")

        return result.toByteArr()
    }

    private fun generateHistogram(bufferedImage: BufferedImage): MutableList<Int> {
        val histogram = MutableList(256) { 0 }
        for (i in 0 until bufferedImage.width) {
            for (j in 0 until bufferedImage.height) {
                val red = Color(bufferedImage.getRGB(i, j)).red
                histogram[red]++
            }
        }

        return histogram
    }

    private fun colorToRGB(alpha: Int, red: Int, green: Int, blue: Int): Int {
        var newPixel = 0
        newPixel += alpha
        newPixel = newPixel shl 8
        newPixel += red
        newPixel = newPixel shl 8
        newPixel += green
        newPixel = newPixel shl 8
        newPixel += blue
        return newPixel
    }
}