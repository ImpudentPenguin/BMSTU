package me.emakeeva.dsp.methods

import org.bytedeco.opencv.opencv_core.Mat
import me.emakeeva.dsp.methods.Methods.Dilate
import me.emakeeva.dsp.methods.Methods.Erode

class Opening: Method() {

    override fun apply(image: Mat): Mat {
        return Dilate.callback.invoke(Erode.callback.invoke(image))
    }
}