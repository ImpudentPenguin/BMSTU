package me.emakeeva.dsp

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleObjectProperty
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.emakeeva.dsp.methods.Bradley
import me.emakeeva.dsp.methods.Otsu
import org.bytedeco.javacv.Java2DFrameConverter
import org.bytedeco.javacv.OpenCVFrameConverter
import org.bytedeco.opencv.global.opencv_core.*
import org.bytedeco.opencv.global.opencv_imgproc.*
import org.bytedeco.opencv.opencv_core.Mat
import tornadofx.Controller
import java.awt.Color
import java.awt.image.*
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.imageio.ImageIO
import kotlin.math.pow


class MainController : Controller() {

    private val otsu = Otsu()
    private val bradley = Bradley()
    var resultObservable = SimpleObjectProperty<ResultModel>()

    fun otsuConvert(link: String, min: Int, max: Int, prop: SimpleDoubleProperty) {
        GlobalScope.launch {
            val job = launch {
                for (i in 1..100) {
                    prop.value = i / 100.0
                    delay(50)
                }
            }

            val image = urlToBytes(link)
            val resultOpenCv = otsu.thresholdOpenCV(image, min, max)
            val result = otsu.threshold(image)

            job.cancel()
            prop.value = 100.0
            resultObservable.value = ResultModel(
                original = image.toByteArr(),
                libResult = resultOpenCv,
                result = result
            )

            delay(50)
            prop.value = 0.0
        }
    }

    fun bradleyConvert(link: String, prop: SimpleDoubleProperty) {
        GlobalScope.launch {
            val job = launch {
                for (i in 1..100) {
                    prop.value = i / 100.0
                    delay(50)
                }
            }

            val image = urlToBytes(link)
            val result = bradley.threshold(image)

            job.cancel()
            prop.value = 100.0
            resultObservable.value = ResultModel(
                original = image.toByteArr(),
                libResult = null,
                result = result
            )

            delay(50)
            prop.value = 0.0
        }
    }

    private fun urlToBytes(link: String): BufferedImage {
        val url = URL(link)
        val connection = url.openConnection() as HttpURLConnection
        connection.connect()

        val inputStream = connection.inputStream
        val bufferedImage = ImageIO.read(inputStream)
        connection.disconnect()
        inputStream.close()

        return bufferedImage
    }
}