package me.emakeeva.dsp.view

import javafx.application.Platform
import javafx.beans.property.SimpleDoubleProperty
import javafx.scene.control.TextField
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import me.emakeeva.dsp.MainController
import me.emakeeva.dsp.methods.*
import tornadofx.*

class MainView : View("Лабораторная работа №3 Математическая морфология") {

    private val controller: MainController by inject()
    private val captchaImg = "https://myrouble.ru/wp-content/uploads/2021/07/kapcha-1.1.jpg"
    private val squareImg = "https://i.ibb.co/Gdwf46m/22785-original.jpg"
    private var image = captchaImg
    private var method = Methods.Dilate

    override val root = vbox {
        var imageField: TextField by singleAssign()
        val toggleGroup = ToggleGroup()
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
                    imageField = textfield(image)
                }
            }
        }

        label("Метод:") {
            paddingLeft = 10
            paddingBottom = 10
        }

        vbox {
            style {
                paddingLeft = 15
            }

            addRadioButton(Methods.Dilate.title, toggleGroup, isSelected = true) {
                imageField.text = captchaImg
                method = Methods.Dilate
            }

            addRadioButton(Methods.Erode.title, toggleGroup) {
                imageField.text = captchaImg
                method = Methods.Erode
            }

            addRadioButton(Methods.Closing.title, toggleGroup) {
                imageField.text = captchaImg
                method = Methods.Closing
            }

            addRadioButton(Methods.Opening.title, toggleGroup) {
                imageField.text = captchaImg
                method =Methods.Opening
            }

            addRadioButton(Methods.Condition_Dilate.title, toggleGroup) {
                imageField.text = captchaImg
                method = Methods.Condition_Dilate
            }

            addRadioButton(Methods.Skeletoning.title, toggleGroup) {
                imageField.text = squareImg
                method = Methods.Skeletoning
            }
        }

        borderpane {
            style {
                paddingTop = 10
            }
            center = button("Подтвердить") {
                action {
                    if (!imageField.text.isNullOrEmpty()) {
                        controller.convert(imageField.text, method, prop)
                    } else
                        AlertView(
                            "Ошибка",
                            "Кажется, вы ввели неверные данные. Пожалуйста, проверьте и повторите попытку."
                        ).openModal()
                }
            }

            bottom = progressindicator(prop) {
                style {
                    paddingTop = 20.0
                }
                visibleWhen { progressProperty().greaterThan(0) }
            }
        }

        controller.resultObservable.addListener { _, _, result ->
            Platform.runLater {
                ResultView(result).openWindow()
            }
        }
    }

    private fun VBox.addRadioButton(
        title: String,
        toggleGroup: ToggleGroup,
        isSelected: Boolean = false,
        callback: () -> Unit
    ) {
        radiobutton(title, toggleGroup) {
            this.isSelected = isSelected
            action(callback)
        }
    }
}