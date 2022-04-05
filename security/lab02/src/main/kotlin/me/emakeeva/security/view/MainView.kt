package me.emakeeva.security.view

import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import me.emakeeva.security.MainController
import tornadofx.*

class MainView : View("Лабораторная работа №2 RSA") {

    private val controller: MainController by inject()
    private val outputView = OutputView()

    override val root = vbox {
        var message: TextArea by singleAssign()
        var size: TextField by singleAssign()

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
                field("Введите размерность:") { size = textfield("1024") }
                field("Введите сообщение:") { message = textarea() }
            }
        }

        borderpane {
            center = button("Подтвердить") {
                action {
                    val result = controller.encrypt(message.text, size.text.toInt())
                    outputView.data.clear()
                    outputView.data.add(result)
                }
            }
        }

        label("Результат:") {
            style {
                paddingLeft = 10
                paddingBottom = 10
                fontWeight = FontWeight.BOLD
            }
        }

        add(outputView)
    }
}