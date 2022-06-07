package me.emakeeva.dsp.view

import javafx.application.Platform
import javafx.beans.property.SimpleDoubleProperty
import javafx.scene.control.TextField
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import me.emakeeva.dsp.MainController
import tornadofx.*

class BradleyView : View() {
    private val controller: MainController by inject()
    private val IMAGE = "https://i.ibb.co/b5XsgJ0/Akkn7br-MPyf-Dq-Jk-Sa-NLkbg.jpg"

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
                        controller.bradleyConvert(image.text, prop)
                    } else
                        AlertView(
                            "Ошибка",
                            "Кажется, вы ввели неверные данные. Пожалуйста, проверьте и повторите попытку."
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