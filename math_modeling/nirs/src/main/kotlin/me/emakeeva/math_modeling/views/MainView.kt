package me.emakeeva.math_modeling.views

import javafx.scene.text.Font
import tornadofx.*

class MainView : View("Моделирование НИРС") {

    override val root = tabpane {
        tab("Метод ячеек") {
            isClosable = false
            add(CellMethodView())
        }
        tab("Последовательное интегрирование") {
            isClosable = false
            add(SequentialIntegrationView())
        }

        style {
            font = Font.font("Verdana")
            fontSize = Dimension(10.0, Dimension.LinearUnits.pt)
        }
    }
}