package me.emakeeva.dsp.view

import javafx.application.Platform
import javafx.beans.property.SimpleDoubleProperty
import javafx.scene.control.TextField
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import me.emakeeva.dsp.MainController
import tornadofx.*

class MainView : View("Лабораторная работа №1 Фильтр Гаусса") {

    private val controller: MainController by inject()
    private val IMAGE = "https://i.ibb.co/ZXBDcQ0/imgonline-com-ua-Black-White-NEQx-Aebp-Oar-UEY.png"

    override val root = vbox {
        var image: TextField by singleAssign()
        val prop = SimpleDoubleProperty()

        style {
            font = Font.font("Verdana")
            fontSize = Dimension(10.0, Dimension.LinearUnits.pt)
            paddingAll = 14.0
        }

        label("Параметры:") {
            style {
                paddingLeft = 10
                fontWeight = FontWeight.BOLD
            }
        }

        form {
            fieldset().vbox {
                field("Введите ссылку с изображением:") {
                    image = textfield(IMAGE)
                }
            }
        }

        borderpane {
            center = button("Подтвердить") {
                action {
                    if (!image.text.isNullOrEmpty()) {
                        controller.convert(image.text, prop)
                    } else
                        AlertView(
                            "Ошибка",
                            "'Ссылка на изображение'"
                        ).openModal()
                }
            }
        }

        progressindicator(prop) {
            style {
               paddingTop = 20.0
            }
            visibleWhen { progressProperty().greaterThan(0) }
        }

        controller.resultObservable.addListener { _, _, result ->
            Platform.runLater {
                ResultView(result).openWindow()
            }
        }
    }
}