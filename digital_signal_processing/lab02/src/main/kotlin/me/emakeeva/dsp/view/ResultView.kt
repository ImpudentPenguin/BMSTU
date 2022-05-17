package me.emakeeva.dsp.view

import javafx.geometry.Pos
import javafx.scene.image.Image
import javafx.scene.layout.HBox
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import me.emakeeva.dsp.ResultModel
import tornadofx.*
import java.io.ByteArrayInputStream

class ResultView(private val model: ResultModel) : View("Результат") {

    override val root = vbox {
        style {
            font = Font.font("Verdana")
            fontSize = Dimension(10.0, Dimension.LinearUnits.pt)
            paddingAll = 24
            alignment = Pos.CENTER
        }

        hbox {
            imgView("Исходная картинка", model.original)
            imgView("Собственное реализация", model.result)
            model.libResult?.let {
                imgView("Применение OpenCV", it)
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