package me.emakeeva.security.view

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.*

class OutputView<T> : View() {

    val data: ObservableList<T> = FXCollections.observableArrayList()

    override val root = listview(data) {
        minWidth = 500.0
        cellFormat {
            graphic = label(it.toString())
        }
    }
}