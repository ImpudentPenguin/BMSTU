package me.emakeeva.dsp

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleObjectProperty
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.bytedeco.javacv.Java2DFrameConverter
import org.bytedeco.javacv.OpenCVFrameConverter
import org.bytedeco.opencv.global.opencv_core.CV_8UC1
import org.bytedeco.opencv.global.opencv_imgproc.*
import org.bytedeco.opencv.opencv_core.Mat
import org.bytedeco.opencv.opencv_core.Size
import tornadofx.Controller
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.imageio.ImageIO

class MainController : Controller() {

    var resultObservable = SimpleObjectProperty<ResultModel>()

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

    fun convert(link: String, prop: SimpleDoubleProperty) {
        GlobalScope.launch {
            val job = launch {
                for (i in 1..100) {
                    prop.value = i / 100.0
                    delay(50)
                }
            }

            val image = urlToBytes(link)
            val resultOpenCv = convertWithOpenCV(image)
            val result = convertWithConvolution(image)

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

    private fun convertWithOpenCV(bufferedImage: BufferedImage): ByteArray {
        val image = OpenCVFrameConverter.ToMat().convert(Java2DFrameConverter().convert(bufferedImage))
        val gray = Mat(image.rows(), image.cols(), CV_8UC1)
        cvtColor(image, gray, CV_BGR2GRAY)
        GaussianBlur(gray, gray, Size(3, 3), 3.0)
        return Java2DFrameConverter().convert(OpenCVFrameConverter.ToMat().convert(gray)).toByteArr()
    }

    private fun convertWithConvolution(bufferedImage: BufferedImage): ByteArray {
        val matrix = listOf(
            listOf(0.5, 0.75, 0.5),
            listOf(0.75, 1.0, 0.75),
            listOf(0.5, 0.75, 0.5),
        ).map { it.map { value -> value * 1 / 6 } }

        val result = BufferedImage(bufferedImage.width, bufferedImage.height, bufferedImage.type)

        for (w in 1 until bufferedImage.width - 1) {
            for (h in 1 until bufferedImage.height - 1) {
                var red = 0.0
                var blue = 0.0
                var green = 0.0

                for (i in matrix.indices) {
                    for (j in matrix.first().indices) {
                        val rgb = bufferedImage.getRGB(w + i - 1, h + j - 1)
                        val color = Color(rgb)
                        red += ((color.red ) * (matrix[i][j]))
                        blue += ((color.blue ) * (matrix[i][j]))
                        green += ((color.green ) * (matrix[i][j]))
                    }
                }

                result.setRGB(w, h, Color(red.toInt(), green.toInt(), blue.toInt()).rgb)
            }
        }

        return result.toByteArr()
    }

    private fun BufferedImage.toByteArr(): ByteArray {
        val stream = ByteArrayOutputStream()
        ImageIO.write(this, "png", stream)
        stream.close()
        return stream.toByteArray()
    }
}