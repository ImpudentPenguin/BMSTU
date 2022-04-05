package me.emakeeva.security.view

import javafx.collections.FXCollections
import javafx.collections.ObservableList
//import me.emakeeva.security.data.ResultModel
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