package me.emakeeva.dsp.methods

import org.bytedeco.opencv.opencv_core.Mat

abstract class Method {
    abstract fun apply(image: Mat): Mat
}