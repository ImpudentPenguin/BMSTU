package me.emakeeva.math_modeling.views

import javafx.geometry.Pos
import javafx.scene.control.TextField
import javafx.scene.text.Font
import me.emakeeva.math_modeling.MainController
import tornadofx.*

class MainView : View("Лабораторная работа №1") {

    private val controller: MainController by inject()
    private var paramX: TextField by singleAssign()
    private var paramN: TextField by singleAssign()
    private lateinit var resultView: OutputView
    private lateinit var reverseResultView: OutputView

    private val nodes: MutableMap<Float, Float> = mutableMapOf(
        0f to 1f,
        0.15f to 0.838771f,
        0.3f to 0.655336f,
        0.45f to 0.450447f,
        0.6f to 0.225336f,
        0.75f to -0.01831f,
        0.9f to -0.27839f,
        1.05f to -0.55243f
    )

    private val reverseNodes: MutableMap<Float, Float> = mutableMapOf(
        1f to 0f,
        0.838771f to 0.15f,
        0.655336f to 0.3f,
        0.450447f to 0.45f,
        0.225336f to 0.6f,
        -0.01831f to 0.75f,
        -0.27839f to 0.9f,
        -0.55243f to 1.05f
    )

    override val root = vbox {
        resultView = OutputView()
        reverseResultView = OutputView()

        style {
            font = Font.font("Verdana")
            fontSize = Dimension(10.0, Dimension.LinearUnits.pt)
            paddingTop = 10
            paddingLeft = 10
            paddingRight = 10
        }

        form {
            fieldset().vbox {
                field("Введите X:") { paramX = textfield("0.620") }
                field("Введите степень полинома:") { paramN = textfield("4") }
            }
        }

        borderpane {
            center = hbox {
                style {
                    alignment = Pos.CENTER
                }

                button("Рассчитать") {
                    action {
                        resultView.data.clear()
                        reverseResultView.data.clear()

                        val result = controller.calcPolinomy(
                            paramX.text.toFloat(),
                            paramN.text.toInt(),
                            nodes
                        )

                        result.first.forEach {
                            resultView.data.add(it.joinToString("\n"))
                        }
                        resultView.data.add("Результат: ${result.second}")

                        val reverseResult = controller.calcPolinomy(
                            0f,
                            paramN.text.toInt(),
                            reverseNodes
                        )

                        reverseResult.first.forEach {
                            reverseResultView.data.add(it.joinToString("\n"))
                        }
                        reverseResultView.data.add("Результат: ${reverseResult.second}")
                    }
                }
            }
        }

        label("Значение y(x):")
        add(resultView)
        label("Обратная интерполяция:")
        add(reverseResultView)
    }
}