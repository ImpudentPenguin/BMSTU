package me.emakeeva.security.view

import javafx.geometry.Pos
import javafx.scene.text.Font
import tornadofx.*

class AlertView(header: String, private val msg: String): View(header) {

    override val root = vbox {
        style {
            setMinWidth(300.0)
            font = Font.font("Verdana")
            fontSize = Dimension(10.0, Dimension.LinearUnits.pt)
            paddingAll = 24
            alignment = Pos.CENTER
        }
        label(msg) {
            style {
                paddingBottom = 10
            }
        }
        button("Закрыть") {
            action {
                this@AlertView.close()
            }
        }
    }
}