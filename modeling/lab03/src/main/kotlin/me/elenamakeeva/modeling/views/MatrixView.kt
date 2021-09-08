package me.elenamakeeva.modeling.views

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.TextField
import javafx.scene.layout.GridPane
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

class MatrixView(private val size: Int) : View() {

    private var matrix: MutableList<MutableList<TextField?>> = mutableListOf()
    private var pane: GridPane? = null

    fun update(count: Int) {
        pane?.clear()
        pane?.let { initMatrix(it, count) }
    }

    fun getMatrix(): List<List<Double>> {
        return matrix.map { list ->
            list.map { field ->
                field?.text?.let { text ->
                    text.replace(",", ".").toDoubleOrNull() ?: 0.0
                } ?: 0.0
            }
        }
    }

    override val root = vbox {
        vboxConstraints {
            margin = Insets(24.0)
        }

        gridpane {
            pane = this
            initMatrix(this, size)
        }
    }

    private fun initMatrix(pane: GridPane, size: Int) {
        val data: MutableList<MutableList<TextField?>> = MutableList(size) { MutableList(size) { null } }

        pane.apply {
            for (row in 0..size)
                for (column in 0..size)
                    if (row == 0 || column == 0) {
                        when {
                            row == column -> setLabel("ИЗ \\ В", column, row)
                            column > row -> setLabel("S$column", column, row)
                            column < row -> setLabel("S$row", column, row)
                        }
                    } else {
                        val field = textfield("1.0") {
                            gridpaneConstraints {
                                columnRowIndex(column, row)
                            }
                            style {
                                borderColor += box(Color.GRAY)
                            }
                            stackpaneConstraints {
                                paddingAll = 4.0
                            }
                        }

                        data[column - 1][row - 1] = field
                    }
        }

        matrix = data
    }

    private fun GridPane.setLabel(text: String, column: Int, row: Int) {
        label(text) {
            gridpaneConstraints {
                columnRowIndex(column, row)
            }
            style {
                borderColor += box(Color.GRAY)
                fontWeight = FontWeight.BOLD
                useMaxWidth = true
                alignment = Pos.CENTER
            }
            stackpaneConstraints {
                paddingAll = 4.0
            }
        }
    }
}