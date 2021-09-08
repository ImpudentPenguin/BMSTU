package me.elenamakeeva.modeling.views

import javafx.geometry.Pos
import javafx.scene.chart.XYChart
import javafx.scene.control.TextField
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import me.elenamakeeva.modeling.MainController
import tornadofx.*

class MainView : View("Лабораторная работа №3 Уравнение Колмогорова") {

    private var count: TextField by singleAssign()
    private var matrixView: MatrixView? = null
    private lateinit var probabilitiesView: OutputView
    private lateinit var timeView: OutputView
    private val controller: MainController by inject()
    private var matrix = listOf<List<Double>>()

    override val root = vbox {
        probabilitiesView = OutputView()
        timeView = OutputView()

        style {
            font = Font.font("Verdana")
            fontSize = Dimension(10.0, Dimension.LinearUnits.pt)
        }

        form {
            fieldset().hbox(20) {
                field("Введите количество состояний:") { count = textfield("3") }
            }
        }
        borderpane {
            center = button("Подтвердить") {
                action {
                    when (val count = count.text.toInt()) {
                        in 1..10 -> matrixView?.update(count)
                        else -> AlertView().openWindow()
                    }
                }
            }
        }

        matrixView = MatrixView(count.text.toInt())
        matrixView?.let {
            add(it)
        }

        borderpane {
            center = hbox {
                style {
                    alignment = Pos.CENTER
                }

                button("Рассчитать") {
                    action {
                        init()
                    }
                }
            }
        }

        hbox {
            style {
                alignment = Pos.CENTER
            }

            vbox {
                label("Предельные вероятности:") {
                    style {
                        fontWeight = FontWeight.BOLD
                        paddingAll = 10
                    }
                }
                add(probabilitiesView)
            }
            vbox {
                label("Время:") {
                    style {
                        fontWeight = FontWeight.BOLD
                        paddingAll = 10
                    }
                }
                add(timeView)
            }
        }
    }

    private fun init() {
        matrix = matrixView?.getMatrix() ?: listOf()
        probabilitiesView.data.clear()
        timeView.data.clear()

        val probabilities = controller.getProbabilities(matrix)
        val stabilizationTime = controller.getTimes(matrix, probabilities)

        probabilities.forEach {
            probabilitiesView.data.add(String.format("%.3f", it))
        }

        stabilizationTime.forEach {
            timeView.data.add(String.format("%.3f", it))
        }

        if (probabilitiesView.data.isNotEmpty()) {
            val data: MutableList<XYChart.Series<Number, Number>> = mutableListOf()
            val dataForGraph = controller.getDataForGraph(matrix)

            for (i in dataForGraph.second.first().indices) {
                val tempList: XYChart.Series<Number, Number> = XYChart.Series(observableListOf())

                dataForGraph.second.forEachIndexed { index, _ ->
                    tempList.data.add(XYChart.Data(dataForGraph.first[index], dataForGraph.second[index][i]))
                }

                data.add(tempList)
            }

            for (index in dataForGraph.second.first().indices)
                data.add(XYChart.Series(observableListOf(XYChart.Data(stabilizationTime[index], probabilities[index]))))

            GraphView(data, matrix.size).openWindow()
        }
    }
}