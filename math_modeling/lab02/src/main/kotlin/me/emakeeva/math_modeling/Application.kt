package me.emakeeva.math_modeling

import javafx.stage.Stage
import me.emakeeva.math_modeling.views.MainView
import tornadofx.App

class Application: App(MainView::class) {
    override fun start(stage: Stage) {
        with(stage) {
            minWidth = 800.0
            minHeight = 650.0
            super.start(this)
        }
    }
}