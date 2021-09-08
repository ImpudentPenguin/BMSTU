package me.elenamakeeva.modeling.views

import javafx.geometry.Pos
import javafx.scene.control.TextField
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import me.elenamakeeva.modeling.MainController
import tornadofx.*

class MainView : View("Лабораторная работа №4 Имитатор") {

    private var a: TextField by singleAssign()
    private var b: TextField by singleAssign()
    private var mu: TextField by singleAssign()
    private var sigma: TextField by singleAssign()
    private var percent: TextField by singleAssign()
    private var count: TextField by singleAssign()
    private var deltaTime: TextField by singleAssign()
    private val controller: MainController by inject()
    private lateinit var deltaTResult: OutputView
    private lateinit var eventsResult: OutputView

    override val root = vbox {
        deltaTResult = OutputView()
        eventsResult = OutputView()

        style {
            font = Font.font("Verdana")
            fontSize = Dimension(10.0, Dimension.LinearUnits.pt)
        }

        form().fieldset("Параметры генератора событий").hbox(20) {
            field("Введите 'a'") { a = textfield("1") }
            field("Введите 'b'") { b = textfield("15") }
        }

        form().fieldset("Параметры обслуживающего аппарата") {
            hbox(20) {
                field("Введите 'μ'") { mu = textfield("0") }
                field("Введите 'σ'") { sigma = textfield("1") }
            }
        }

        form().fieldset("Введите параметры").vbox {
            hbox {
                field("Введите количество событий") { count = textfield("1000") }
                field("Введите deltaTime") {
                    paddingLeft = 10
                    deltaTime = textfield("0.01")
                }
            }
            field("Введите процент возвращенных событий") { percent = textfield("0.0") }
        }

        borderpane {
            center = button("Подтвердить") {
                action {
                    val a = a.text.toDouble()
                    val b = b.text.toDouble()
                    val mu = mu.text.toDouble()
                    val sigma = sigma.text.toDouble()
                    val count = count.text.toInt()
                    val percent = percent.text.toDouble()
                    val deltaT = deltaTime.text.toDouble()

                    // TODO validate data

                    deltaTResult.data.add(controller.getMinTimePrincipleDeltaT(a, b, mu, sigma, count, percent, deltaT))
                    eventsResult.data.add(controller.getMinTimePrincipleEvent(a, b, mu, sigma, count, percent))
                }
            }
        }

        hbox {
            style {
                paddingTop = 10
                alignment = Pos.CENTER
            }

            vbox {
                label("Принцип delta T:") {
                    style {
                        fontWeight = FontWeight.BOLD
                        paddingAll = 10
                    }
                }

                add(deltaTResult)
            }

            vbox {
                label("Событийный принцип:") {
                    style {
                        fontWeight = FontWeight.BOLD
                        paddingAll = 10
                    }
                }

                add(eventsResult)
            }
        }
    }
}