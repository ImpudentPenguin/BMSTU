package me.emakeeva.math_modeling.views

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.geometry.Pos
import javafx.scene.control.TableView
import me.emakeeva.math_modeling.ResultModel
import tornadofx.*
import kotlin.reflect.KProperty1

class OutputView : View() {

    val data: ObservableList<ResultModel> = FXCollections.observableArrayList()

    override val root = tableview(data) {
        createColumn(this, "x", ResultModel::x)
        createColumn(this, "y", ResultModel::y)
        createColumn(this, "1", ResultModel::first)
        createColumn(this, "2", ResultModel::second)
        createColumn(this, "3", ResultModel::third)
        createColumn(this, "4", ResultModel::forth)
        createColumn(this, "5", ResultModel::fifth)

        maxWidth = 562.0
    }

    private fun <T> createColumn(context: TableView<ResultModel>, title: String, prop: KProperty1<ResultModel, T>) {
        context.readonlyColumn(title, prop) {
            minWidth = 80.0
            maxWidth = 80.0

            style { alignment = Pos.CENTER }
        }
    }
}