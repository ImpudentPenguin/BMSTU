package me.emakeeva.dsp.operators

import org.bytedeco.opencv.opencv_core.Mat
import java.awt.image.BufferedImage

abstract class Operator {
    abstract fun apply(image: Mat): BufferedImage
}