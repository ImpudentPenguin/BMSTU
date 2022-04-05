package me.emakeeva.security.view

import javafx.geometry.Pos
import javafx.scene.image.Image
import javafx.scene.layout.HBox
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import me.emakeeva.security.MainController
import me.emakeeva.security.ResultModel
import tornadofx.*
import java.io.ByteArrayInputStream
import java.io.FileInputStream

class ResultView(private val result: ResultModel) : View("Результат") {

    private val controller: MainController by inject()

    override val root = vbox {
        style {
            font = Font.font("Verdana")
            fontSize = Dimension(10.0, Dimension.LinearUnits.pt)
            paddingAll = 24
            alignment = Pos.CENTER
        }

        hbox {
            imgView("Исходная картинка", result.original)
            imgView("Зашифрованная картинка", result.encrypted)
        }

        borderpane {
            style {
                paddingTop = 20
            }

            center = button("Дешифровать") {
                action {
                    val result = controller.decrypt(result.encrypted)
                    AlertView(
                        "Результат дешифрования",
                        "Ваше сообщение: $result"
                    ).openModal()
                }
            }
        }
    }

    private fun HBox.imgView(title: String, bytes: ByteArray) {
        vbox(alignment = Pos.CENTER) {
            label(title) {
                style {
                    fontWeight = FontWeight.BOLD
                    paddingBottom = 8
                }
            }

            imageview {
                image = Image(
                    ByteArrayInputStream(bytes),
                    400.0,
                    400.0,
                    true,
                    true
                )
            }
        }
    }
}