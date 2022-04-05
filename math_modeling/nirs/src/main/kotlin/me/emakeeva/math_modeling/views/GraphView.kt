package me.emakeeva.math_modeling.views

import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import tornadofx.*

class GraphView : View() {

    private lateinit var values: XYChart.Series<Number, Number>

    fun update(data: XYChart.Series<Number, Number>) {
        values.data.clear()
        values.data = data.data
    }

    override val root = borderpane {
        center = linechart("", NumberAxis(), NumberAxis()) {
            xAxis.label = "tao"
            yAxis.label = "Result"

            series("График зависимости") {
                values = this
            }
        }
    }
}