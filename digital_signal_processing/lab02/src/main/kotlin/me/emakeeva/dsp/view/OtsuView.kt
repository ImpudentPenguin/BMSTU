package me.emakeeva.dsp.view

import javafx.application.Platform
import javafx.beans.property.SimpleDoubleProperty
import javafx.scene.control.TextField
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import me.emakeeva.dsp.MainController
import tornadofx.*

class OtsuView : View() {
    private val controller: MainController by inject()
    private val IMAGE = "https://i.ibb.co/b5XsgJ0/Akkn7br-MPyf-Dq-Jk-Sa-NLkbg.jpg"
    private val MIN_VALUE = 0
    private val MAX_VALUE = 255

    override val root = vbox {
        var image: TextField by singleAssign()
        var min: TextField by singleAssign()
        var max: TextField by singleAssign()
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
                field("Введите минимальное пороговое значение > 0:") {
                    min = textfield(MIN_VALUE.toString())
                }
                field("Введите максимальное пороговое значение < 256:") {
                    max = textfield(MAX_VALUE.toString())
                }
            }
        }

        borderpane {
            center = button("Подтвердить") {
                action {
                    val minValue = min.text.toIntOrNull() ?: -1
                    val maxValue = max.text.toIntOrNull() ?: Int.MAX_VALUE
                    if (checkInputValue(image.text, minValue, maxValue)) {
                        controller.otsuConvert(image.text, minValue, maxValue, prop)
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

    private fun checkInputValue(imageUrl: String?, min: Int, max: Int): Boolean {
        return !imageUrl.isNullOrEmpty() && min >= MIN_VALUE && max <= MAX_VALUE
    }
}