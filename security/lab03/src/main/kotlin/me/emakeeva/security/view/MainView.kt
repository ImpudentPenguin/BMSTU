package me.emakeeva.security.view

import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import me.emakeeva.security.MainController
import tornadofx.*

class MainView : View("Лабораторная работа №3 Стеганография") {

    private val controller: MainController by inject()

    override val root = vbox {
        var image: TextField by singleAssign()
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
                field("Введите ссылку с изображением в формате png:") { image = textfield("https://i.pinimg.com/originals/e3/23/7f/e3237fbe89875d862975beede439176e.png") }
                field("Введите сообщение:") { message = textarea() }
            }
        }

        borderpane {
            center = button("Подтвердить") {
                action {
                    if (!image.text.isNullOrEmpty() && !message.text.isNullOrEmpty()) {
                        val result = controller.encrypt(image.text, message.text)
                        ResultView(result).openWindow()
                    } else
                        AlertView(
                            "Ошибка",
                            "Заполните поля 'Сообщение' и 'Ссылка на изображение'"
                        ).openModal()
                }
            }
        }
    }
}