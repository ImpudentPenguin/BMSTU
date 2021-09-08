package org.emakeeva.lab01

import java.awt.EventQueue

fun main() {
    EventQueue.invokeLater(::createWindow)
}

private fun createWindow() {
    GUIWorker().also {
        it.isVisible = true
    }
}