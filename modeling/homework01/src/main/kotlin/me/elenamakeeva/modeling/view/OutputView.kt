package me.elenamakeeva.modeling.view

import javafx.collections.ObservableList
import tornadofx.View
import tornadofx.label
import tornadofx.listview

class OutputView(data: ObservableList<String>) : View() {

    override val root = listview(data) {
        minWidth = 500.0
        minHeight = 400.0
        cellFormat {
            graphic = label(it)
        }
    }
}