package me.elenamakeeva.modeling

import javafx.stage.Stage
import me.elenamakeeva.modeling.view.MainView
import tornadofx.App

class Application: App(MainView::class, Styles::class) {

    override fun start(stage: Stage) {
        with(stage) {
            minWidth = 1000.0
            minHeight = 600.0
            super.start(this)
        }
    }
}