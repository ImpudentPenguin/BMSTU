package me.elenamakeeva.modeling.views

import javafx.scene.text.Font
import tornadofx.*

class AlertView : View() {

    override val root = vbox {
        style {
            font = Font.font("Verdana")
            fontSize = Dimension(10.0, Dimension.LinearUnits.pt)
            paddingAll = 24
        }
        label("Введите значение больше 0, так как коэффициент 'σ' должен быть строго положительный.")
        button("Закрыть") {
            action {
                this@AlertView.close()
            }
        }
    }
}