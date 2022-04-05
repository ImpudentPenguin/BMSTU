package me.emakeeva.math_modeling.views

import javafx.geometry.Insets
import javafx.scene.control.TextField
import javafx.scene.text.FontWeight
import me.emakeeva.math_modeling.MainController
import tornadofx.*
import kotlin.math.roundToInt

class CellMethodView : View() {
    private val controller: MainController by inject()

    override val root = vbox {
        var paramN: TextField by singleAssign()
        var paramM: TextField by singleAssign()
        var paramRange: TextField by singleAssign()
        val graphView = GraphView()

        stackpaneConstraints {
            margin = Insets(24.0)
        }

        label("Вычисление двойного интеграла методом ячеек") {
            paddingLeft = 10
            paddingBottom = 16
        }
        label("Параметры:") {
            paddingLeft = 10

            style {
                fontWeight = FontWeight.BOLD
            }
        }

        form {
            fieldset().vbox {
                field("Введите количество разбиений по горизонтали (N)") { paramN = textfield("1000") }
                field("Введите количество разбиений по вертикали (M)") { paramM = textfield("1000") }
                field("Введите диапазон tao:") { paramRange = textfield("0.05 - 10") }
            }

            borderpane {
                center = button("Подтвердить") {
                    action {
                        val range = paramRange.text.split("-").mapNotNull { it.toDoubleOrNull() }
                        val seqTao = generateSequence(range.first()) { value ->
                            (((value + 0.05) * 100.0).roundToInt() / 100.0).takeIf { value < range.lastOrNull() ?: range.first() }
                        }.toList()

                        graphView.update(controller.getCellMethodResult(
                            n = paramN.text.toIntOrNull() ?: 1,
                            m = paramM.text.toIntOrNull() ?: 1,
                            taoList = seqTao
                        ))
                    }
                }
            }
        }

        add(graphView)
    }
}