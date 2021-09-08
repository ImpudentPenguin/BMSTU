package me.elenamakeeva.modeling.views

import javafx.scene.text.Font
import tornadofx.*

class MainView : View("Лабораторная работа №2 Вариант 2") {

    override val root = tabpane {
        tab("Равномерное распределение") {
            isClosable = false
            add(UniformView())
        }
        tab("Нормальное распределение") {
            isClosable = false
            add(NormView())
        }

        style {
            font = Font.font("Verdana")
            fontSize = Dimension(10.0, Dimension.LinearUnits.pt)
        }
    }
}