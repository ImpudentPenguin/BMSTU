package me.emakeeva.math_modeling.views

import javafx.scene.control.TextField
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import me.emakeeva.math_modeling.MainController
import tornadofx.*
import kotlin.math.roundToInt

class MainView : View("Лабораторная работа №2") {

    private val controller: MainController by inject()
    private var firstMethod: (((Double, Double?) -> Double, Int, Double, Double, Double?) -> Double) =
        controller::simpsonMethod
    private var secondMethod: (((Double, Double?) -> Double, Int, Double, Double, Double?) -> Double) =
        controller::gaussMethod

    override val root = vbox {
        val toggleGroupFirst = ToggleGroup()
        val toggleGroupSecond = ToggleGroup()
        var paramTau: TextField by singleAssign()
        var paramRange: TextField by singleAssign()
        var paramN: TextField by singleAssign()
        var paramM: TextField by singleAssign()

        val resultView = OutputView()
        val resultRangeView = GraphView()

        style {
            font = Font.font("Verdana")
            fontSize = Dimension(10.0, Dimension.LinearUnits.pt)
            paddingLeft = 10
            paddingRight = 10
        }

        form {
            fieldset().vbox {
                hbox {
                    field("Введите tau:") { paramTau = textfield("1") }
                    label("ИЛИ") {
                        style {
                            paddingAll = 10
                        }
                    }
                    field("Введите диапазон:") { paramRange = textfield("0.05 - 10") }
                }
                field("Введите N:") { paramN = textfield("3") }
                field("Введите M:") { paramM = textfield("3") }
            }
        }

        drawToggleGroup(
            context = this,
            title = "внешнего",
            toggleGroup = toggleGroupFirst,
            firstAction = { firstMethod = controller::gaussMethod },
            secondAction = { firstMethod = controller::simpsonMethod },
            isFirstSelected = false
        )

        drawToggleGroup(
            context = this,
            title = "внутреннего",
            toggleGroup = toggleGroupSecond,
            firstAction = { secondMethod = controller::gaussMethod },
            secondAction = { secondMethod = controller::simpsonMethod }
        )

        button("Рассчитать") {
            action {
                resultView.data.clear()

                val result = controller.getResult(
                    n = paramN.text.toInt(),
                    m = paramM.text.toInt(),
                    taoList = listOf(paramTau.text.toDouble()),
                    firstMethod = firstMethod,
                    secondMethod = secondMethod
                ).data.last().yValue

                resultView.data.add(result.toString())

                val range = paramRange.text.split("-").mapNotNull { it.toDoubleOrNull() }
                val seq = generateSequence(0.05) { value ->
                    (((value + 0.05) * 100.0).roundToInt() / 100.0).takeIf { value < range.last() }
                }.toList()

                resultRangeView.update(
                    controller.getResult(
                        n = paramN.text.toInt(),
                        m = paramM.text.toInt(),
                        taoList = seq,
                        firstMethod = firstMethod,
                        secondMethod = secondMethod
                    )
                )
            }
        }

        drawResultForm(
            context = this,
            title = "Результат вычисления по фиксированному значению параметра tao:",
            view = resultView
        )

        drawResultForm(
            context = this,
            title = "Результаты вычисления по введенному диапазону:",
            view = resultRangeView,
            topPadding = 10
        )
    }

    private fun drawResultForm(
        context: VBox,
        title: String,
        view: View,
        topPadding: Int = 20,
        bottomPadding: Int = 2
    ) {
        context.apply {
            label(title) {
                style {
                    paddingTop = topPadding
                    paddingBottom = bottomPadding
                }
            }
            add(view)
        }
    }

    private fun drawToggleGroup(
        context: VBox,
        title: String,
        toggleGroup: ToggleGroup,
        firstAction: () -> Unit,
        secondAction: () -> Unit,
        isFirstSelected: Boolean = true
    ) {
        context.apply {
            label("Выберите метод интегрирования для $title интеграла:")
            hbox {
                style {
                    paddingBottom = 10
                    paddingTop = 10
                }

                radiobutton("Метод Гаусса", toggleGroup) {
                    style {
                        paddingRight = 10
                    }

                    action(firstAction)
                }.isSelected = isFirstSelected

                radiobutton("Метод Симпсона", toggleGroup) {
                    action(secondAction)
                }.isSelected = !isFirstSelected
            }
        }
    }
}