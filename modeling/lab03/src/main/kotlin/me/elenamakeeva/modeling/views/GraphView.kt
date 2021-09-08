package me.elenamakeeva.modeling.views

import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import javafx.scene.text.Font
import tornadofx.*

class GraphView(series: List<XYChart.Series<Number, Number>>, size: Int) : View("График вероятностей состояний") {

    override val root = borderpane {
        style {
            minWidth = Dimension(800.0, Dimension.LinearUnits.pt)
            minHeight = Dimension(550.0, Dimension.LinearUnits.pt)
            font = Font.font("Verdana")
            fontSize = Dimension(10.0, Dimension.LinearUnits.pt)
        }

        center = linechart("", NumberAxis(), NumberAxis()) {
            xAxis.label = "t"
            yAxis.label = "p"

            var indexPoint = 0
            series.forEachIndexed { index, series ->
                if (index >= size) {
                    series("Point $indexPoint") {
                        data = series.data
                    }
                    indexPoint++
                } else
                    series("$index") {
                        data = series.data
                    }
            }
        }
    }
}