package me.emakeeva.security.view

import javafx.geometry.Pos
import javafx.scene.text.Font
import tornadofx.*

class AlertView : View("Ошибка") {

    override val root = vbox {
        style {
            font = Font.font("Verdana")
            fontSize = Dimension(10.0, Dimension.LinearUnits.pt)
            paddingAll = 24
            alignment = Pos.CENTER
        }
        label("Заполните поля 'Сообщение' и 'Ключ'") {
            style {
                paddingBottom = 10
            }
        }
        button("Закрыть") {
            action {
                this@AlertView.close()
            }
        }
    }
}