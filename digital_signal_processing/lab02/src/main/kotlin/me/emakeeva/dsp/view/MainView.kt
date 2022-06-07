package me.emakeeva.dsp.view

import javafx.scene.text.Font
import tornadofx.*

class MainView : View("Лабораторная работа №2 Бинаризация") {

    override val root = tabpane {
        tab("Метод Оцу") {
            isClosable = false
            add(OtsuView())
        }
        tab("Метод Брэдли") {
            isClosable = false
            add(BradleyView())
        }

        style {
            font = Font.font("Verdana")
            fontSize = Dimension(10.0, Dimension.LinearUnits.pt)
        }
    }
}