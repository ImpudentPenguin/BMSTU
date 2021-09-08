package me.elenamakeeva.modeling

import javafx.stage.Stage
import me.elenamakeeva.modeling.views.MainView
import tornadofx.*

class Application: App(MainView::class) {
    override fun start(stage: Stage) {
        with(stage) {
            minWidth = 1000.0
            minHeight = 600.0
            super.start(this)
        }
    }
}