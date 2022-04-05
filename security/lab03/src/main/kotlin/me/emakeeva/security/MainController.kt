package me.emakeeva.security

import tornadofx.Controller
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.math.BigInteger
import java.net.HttpURLConnection
import java.net.URL
import javax.imageio.ImageIO

class MainController : Controller() {

    private val END_MESSAGE_COSTANT = "#!@"
    private val START_MESSAGE_COSTANT = "@!#"
    private val BINARY_SIZE = 8

    fun encrypt(link: String, message: String): ResultModel {
        val image = urlToBytes(link)
        val origin = image.toByteArr()
        val binaryMessage = mutableListOf<Char>()
        "$START_MESSAGE_COSTANT$message$END_MESSAGE_COSTANT".toCharArray().forEach {
            binaryMessage.addAll(it.code.toString(2).padStart(BINARY_SIZE, '0').toList())
        }

        for (w in binaryMessage.indices) {
            for (h in 0 until image.height) {
                if (binaryMessage.isEmpty())
                    break

                val color = Color(image.getRGB(w, h))
                val blue = color.blue.toByte()
                val result = convertNumber(blue, "${binaryMessage.removeAt(1)}${binaryMessage.removeAt(0)}".reversed())
                val newColor = Color(color.red, color.green, result.toInt())
                image.setRGB(w, h, newColor.rgb)
            }

            if (binaryMessage.isEmpty())
                break
        }

        return ResultModel(original = origin, encrypted = image.toByteArr())
    }

    fun decrypt(img: ByteArray): String {
        val image = img.toBufferedImage()
        val symbolBuilder = StringBuilder()
        val message = StringBuilder()

        for (w in 0 until image.width) {
            for (h in 0 until image.height) {
                val color = Color(image.getRGB(w, h))
                val blue = color.blue.toString(2)
                symbolBuilder.append(blue.substring(blue.length - 2, blue.length))

                if (symbolBuilder.toString().length == BINARY_SIZE) {
                    message.append(BigInteger(symbolBuilder.toString(), 2).toInt().toChar())
                    symbolBuilder.clear()
                }

                if (message.contains(END_MESSAGE_COSTANT))
                    return message
                        .replace(START_MESSAGE_COSTANT.toRegex(), "")
                        .replace(END_MESSAGE_COSTANT.toRegex(), "")
            }
        }

        return message.toString()
    }

    private fun convertNumber(byte: Byte, messageByte: String): Byte {
        val convert = byte.toString(2).padStart(BINARY_SIZE, '0')
        return BigInteger(convert.replaceRange(convert.length - 2, convert.length, messageByte), 2)
            .toByte()
    }

    private fun ByteArray.toBufferedImage(): BufferedImage {
        val stream = ByteArrayInputStream(this)
        val image = ImageIO.read(stream)
        stream.close()

        return image
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

    private fun BufferedImage.toByteArr(): ByteArray {
        val stream = ByteArrayOutputStream()
        ImageIO.write(this, "png", stream)
        stream.close()
        return stream.toByteArray()
    }
}