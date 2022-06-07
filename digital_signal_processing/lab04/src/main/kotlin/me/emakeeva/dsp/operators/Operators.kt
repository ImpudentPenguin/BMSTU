package me.emakeeva.dsp.operators

import me.emakeeva.dsp.convert
import org.bytedeco.opencv.opencv_core.Mat
import java.awt.image.BufferedImage

enum class Operators(val title: String, val callback: (Mat) -> BufferedImage) {
    Canny("Кенни", { image -> image.convert(Canny()) }),
    Roberts("Робертс", { image -> image.convert(Roberts()) }),
    Prewitt("Прюитт", { image -> image.convert(Prewitt()) }),
    Sobel("Собель", { image ->  image.convert(Sobel())}),
    Scharr("Шарр", { image -> image.convert(Scharr()) }),
    Laplace("Лаплас", { image -> image.convert(Laplace()) })
}