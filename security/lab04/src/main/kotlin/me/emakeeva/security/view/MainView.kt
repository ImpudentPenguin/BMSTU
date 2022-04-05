package me.emakeeva.security.view

import javafx.scene.control.TextArea
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import me.emakeeva.security.MainController
import tornadofx.*

class MainView : View("Лабораторная работа №4 Код Хаффмана") {

    private val controller: MainController by inject()

    override val root = vbox {
        var message: TextArea by singleAssign()

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
                field("Введите сообщение:") { message = textarea() }
            }
        }

        borderpane {
            center = button("Подтвердить") {
                action {
                    if (!message.text.isNullOrEmpty()) {
                        val result = controller.encrypt(message.text)
                        ResultView(result).openWindow()
                    } else
                        AlertView(
                            "Ошибка",
                            "Заполните поля 'Сообщение'"
                        ).openModal()
                }
            }
        }
    }
}