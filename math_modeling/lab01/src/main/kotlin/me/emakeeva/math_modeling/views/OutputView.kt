package me.emakeeva.math_modeling.views

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.geometry.Orientation
import tornadofx.*

class OutputView : View() {

    val data: ObservableList<String> = FXCollections.observableArrayList()

    override val root = listview(data) {
        orientation = Orientation.HORIZONTAL
        minWidth = 300.0
        maxHeight = 150.0
        cellFormat {
            graphic = label(it)
        }
    }
}