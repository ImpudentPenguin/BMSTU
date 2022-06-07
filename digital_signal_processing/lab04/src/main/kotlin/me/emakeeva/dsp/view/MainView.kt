package me.emakeeva.dsp.view

import javafx.application.Platform
import javafx.beans.property.SimpleDoubleProperty
import javafx.scene.control.TextField
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import me.emakeeva.dsp.MainController
import me.emakeeva.dsp.operators.*
import tornadofx.*

class MainView : View("Лабораторная работа №4 Поиск границ") {

    private val controller: MainController by inject()
    private val IMAGE = "https://i.ibb.co/Bf5jf6X/Zunge-raus.jpg"
    private var operator = Operators.Canny

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
                    imageField = textfield(IMAGE)
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

            addRadioButton(Operators.Canny.title, toggleGroup, isSelected = true) {
                operator = Operators.Canny
            }

            addRadioButton(Operators.Roberts.title, toggleGroup) {
                operator = Operators.Roberts
            }

            addRadioButton(Operators.Prewitt.title, toggleGroup) {
                operator = Operators.Prewitt
            }

            addRadioButton(Operators.Sobel.title, toggleGroup) {
                operator =Operators.Sobel
            }

            addRadioButton(Operators.Scharr.title, toggleGroup) {
                operator = Operators.Scharr
            }

            addRadioButton(Operators.Laplace.title, toggleGroup) {
                operator = Operators.Laplace
            }
        }

        borderpane {
            style {
                paddingTop = 10
            }
            center = button("Подтвердить") {
                action {
                    if (!imageField.text.isNullOrEmpty()) {
                        controller.convert(imageField.text, operator, prop)
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