package me.emakeeva.security

import javafx.stage.Stage
import me.emakeeva.security.view.MainView
import tornadofx.App

class Application: App(MainView::class) {

    override fun start(stage: Stage) {
        with(stage) {
            minWidth = 1000.0
            minHeight = 600.0
            super.start(this)
        }
    }
}