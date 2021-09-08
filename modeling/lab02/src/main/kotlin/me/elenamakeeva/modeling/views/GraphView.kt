package me.elenamakeeva.modeling.views

import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import tornadofx.*

class GraphView(pairData: Pair<XYChart.Series<Number, Number>, XYChart.Series<Number, Number>>) : View() {

    private lateinit var pmf: XYChart.Series<Number, Number>
    private lateinit var cdf: XYChart.Series<Number, Number>

    fun update(data: Pair<XYChart.Series<Number, Number>, XYChart.Series<Number, Number>>) {
        cdf.data.clear()
        pmf.data.clear()

        cdf.data = data.first.data
        pmf.data = data.second.data
    }

    override val root = borderpane {
        center = linechart("", NumberAxis(), NumberAxis()) {
            xAxis.label = "x"
            yAxis.label = "F(x)"

            series("Функция распределения") {
                cdf = this
                data = pairData.first.data
            }
            series("Функция плотности") {
                pmf = this
                data = pairData.second.data
            }
        }
    }
}