package me.emakeeva.math_modeling.views

import javafx.geometry.Pos
import javafx.scene.text.Font
import me.emakeeva.math_modeling.MainController
import me.emakeeva.math_modeling.ResultModel
import tornadofx.*

class MainView : View("Лабораторная работа №3") {

    private val controller: MainController by inject()
    private val inputData = mutableListOf(
        ResultModel(x = 1.0, y = 0.571),
        ResultModel(x = 2.0, y = 0.889),
        ResultModel(x = 3.0, y = 1.091),
        ResultModel(x = 4.0, y = 1.231),
        ResultModel(x = 5.0, y = 1.333),
        ResultModel(x = 6.0, y = 1.412),
    )

    override val root = vbox {
        val resultView = OutputView()

        style {
            font = Font.font("Verdana")
            fontSize = Dimension(10.0, Dimension.LinearUnits.pt)
            paddingAll = 20
            alignment = Pos.TOP_CENTER
        }

        add(resultView)
        resultView.data.addAll(inputData)

        spacer {
            paddingBottom = 10
        }

        button("Рассчитать") {
            action {
                resultView.data.clear()

                val result = controller.getResult(inputData)

                resultView.data.addAll(result)
            }
        }
    }
}