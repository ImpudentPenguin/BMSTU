package me.emakeeva.dsp

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleObjectProperty
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.emakeeva.dsp.methods.Methods
import tornadofx.Controller
import java.awt.image.*
import java.net.HttpURLConnection
import java.net.URL
import javax.imageio.ImageIO

class MainController : Controller() {

    var resultObservable = SimpleObjectProperty<ResultModel>()

    fun convert(link: String, method: Methods, prop: SimpleDoubleProperty) {
        launch(prop) {
            val image = urlToBytes(link)
            val mat = image.binary()
            val res = method.callback.invoke(mat.second).convertToBufferedImage().toByteArr()

            ResultModel(
                title = method.title,
                original = image.toByteArr(),
                result = res
            )
        }
    }

    private fun launch(prop: SimpleDoubleProperty, callback: () -> ResultModel) {
        GlobalScope.launch {
            val job = launch {
                for (i in 1..100) {
                    prop.value = i / 100.0
                    delay(50)
                }
            }

            val result = callback.invoke()

            job.cancel()
            prop.value = 100.0
            resultObservable.value = result

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