package me.emakeeva.dsp.methods

import me.emakeeva.dsp.convert
import org.bytedeco.opencv.opencv_core.Mat

enum class Methods(val title: String, val callback: (Mat) -> Mat) {
    Dilate("Дилатация", { image -> image.convert(Dilate()) }),
    Erode("Эрозия", { image -> image.convert(Erode()) }),
    Closing("Замыкание", { image -> image.convert(Closing()) }),
    Opening("Размыкание", { image -> image.convert(Opening()) }),
    Condition_Dilate("Условная дилатация", { image -> image.convert(ConditionDilate()) }),
    Skeletoning("Морфологический скелет", { image -> image.convert(Skeletoning()) })
}