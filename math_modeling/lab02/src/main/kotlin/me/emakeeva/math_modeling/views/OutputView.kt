package me.emakeeva.math_modeling.views

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.geometry.Orientation
import tornadofx.*

class OutputView : View() {

    val data: ObservableList<String> = FXCollections.observableArrayList()

    override val root = listview(data) {
        orientation = Orientation.HORIZONTAL
        maxHeight = 40.0
    }
}