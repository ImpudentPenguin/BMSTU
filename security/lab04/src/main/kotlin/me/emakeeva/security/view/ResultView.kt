package me.emakeeva.security.view

import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import javafx.scene.text.TextFlow
import me.emakeeva.security.MainController
import me.emakeeva.security.models.ResultModel
import tornadofx.*
import kotlin.random.Random

class ResultView(private val result: ResultModel) : View("Результат") {

    private val controller: MainController by inject()

    override val root = vbox {
        val directoryView = OutputView()

        style {
            font = Font.font("Verdana")
            fontSize = Dimension(10.0, Dimension.LinearUnits.pt)
            paddingAll = 24
        }

        val coloredText = TextFlow()
        result.encryptedMessage.forEach {
            val text = Text(it)
            val r = Random.nextDouble()
            val g = Random.nextDouble()
            val b = Random.nextDouble()
            val color = Color(r, g, b, 1.0)
            text.fill = color
            coloredText.add(text)
        }
        directoryView.data.addAll(result.directory.map { "${it.key}: ${it.value}" })

        label("Результат шифрования:") {
            style {
                paddingVertical = 20.0
                fontWeight = FontWeight.BOLD
            }
        }
        add(coloredText)

        label("Справочник:") {
            style {
                paddingVertical = 20.0
                fontWeight = FontWeight.BOLD
            }
        }
        add(directoryView)

        borderpane {
            style {
                paddingTop = 20
            }

            center = button("Дешифровать") {
                action {
                    val result = controller.decrypt(result.encryptedMessage.joinToString(""), result.directory)
                    AlertView(
                        "Результат дешифрования",
                        "Ваше сообщение: $result"
                    ).openModal()
                }
            }
        }
    }
}