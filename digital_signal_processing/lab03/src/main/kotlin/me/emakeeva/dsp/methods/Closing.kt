package me.emakeeva.dsp.methods

import org.bytedeco.opencv.opencv_core.Mat
import me.emakeeva.dsp.methods.Methods.Erode
import me.emakeeva.dsp.methods.Methods.Dilate

class Closing: Method() {

    override fun apply(image: Mat): Mat {
        return Erode.callback.invoke(Dilate.callback.invoke(image))
    }
}