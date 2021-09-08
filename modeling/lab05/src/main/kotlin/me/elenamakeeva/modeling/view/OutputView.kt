package me.elenamakeeva.modeling.view

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.*

class OutputView : View() {

    val data: ObservableList<String> = FXCollections.observableArrayList()

    override val root = listview(data) {
        minWidth = 500.0
        cellFormat {
            graphic = label(it)
        }
    }
}