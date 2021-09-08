package me.elenamakeeva.modeling.view

import javafx.scene.control.TextField
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import me.elenamakeeva.modeling.MainController
import me.elenamakeeva.modeling.format
import me.elenamakeeva.modeling.models.Computer
import me.elenamakeeva.modeling.models.RequestGenerator
import me.elenamakeeva.modeling.models.Operator
import me.elenamakeeva.modeling.toDouble
import me.elenamakeeva.modeling.toInt
import tornadofx.*

class MainView : View("Лабораторная работа №5 Информационный центр") {

    private var clientTime: TextField by singleAssign()
    private var clientDeltaTime: TextField by singleAssign()

    private var firstOperatorTime: TextField by singleAssign()
    private var firstOperatorDeltaTime: TextField by singleAssign()

    private var secondOperatorTime: TextField by singleAssign()
    private var secondOperatorDeltaTime: TextField by singleAssign()

    private var thirdOperatorTime: TextField by singleAssign()
    private var thirdOperatorDeltaTime: TextField by singleAssign()

    private var firstComp: TextField by singleAssign()
    private var secondComp: TextField by singleAssign()

    private var count: TextField by singleAssign()
    private var systemTime: TextField by singleAssign()
    private val controller: MainController by inject()
    private val resultView: OutputView by inject()

    override val root = vbox {
        style {
            font = Font.font("Verdana")
            fontSize = Dimension(10.0, Dimension.LinearUnits.pt)
        }

        hbox {
            vbox {
                form().fieldset("Интервал времени прихода клиентов").hbox(20) {
                    field("мин") { clientTime = textfield("10") }
                    field("+-") { clientDeltaTime = textfield("2") }
                }
                form().fieldset("Время работы компьютеров").vbox {
                    field("1й компьютер") { firstComp = textfield("15") }
                    field("2й компьютер") { secondComp = textfield("30") }
                }
            }

            form().fieldset("Время работы операторов").vbox {
                label("1й оператор") {
                    style {
                        fontWeight = FontWeight.BOLD
                    }
                }
                hbox(20) {
                    field("мин") { firstOperatorTime = textfield("20") }
                    field("+-") { firstOperatorDeltaTime = textfield("5") }
                }

                label("2й оператор") {
                    style {
                        fontWeight = FontWeight.BOLD
                    }
                }
                hbox(20) {
                    field("мин") { secondOperatorTime = textfield("40") }
                    field("+-") { secondOperatorDeltaTime = textfield("10") }
                }

                label("3й оператор") {
                    style {
                        fontWeight = FontWeight.BOLD
                    }
                }
                hbox(20) {
                    field("мин") { thirdOperatorTime = textfield("40") }
                    field("+-") { thirdOperatorDeltaTime = textfield("20") }
                }
            }
        }

        form().fieldset().vbox {
            field("Количество обработанных заявок") { count = textfield("300") }
            field("Единица системного времени") { systemTime = textfield("0.01") }
        }

        borderpane {
            center = button("Подтвердить") {
                action {
                    init()
                }
            }
        }

        label("Результат:") {
            style {
                paddingLeft = 10
                fontWeight = FontWeight.BOLD
            }
        }

        add(resultView)
    }

    private fun init() {
        val systemTime = systemTime.toDouble()
        val requestsGenerator = RequestGenerator(
            clientTime.toDouble() - clientDeltaTime.toDouble(),
            clientTime.toDouble() + clientDeltaTime.toDouble(),
            systemTime
        )

        val firstComputer = Computer(1, firstComp.toDouble(), firstComp.toDouble(), systemTime)
        val secondComputer = Computer(2, secondComp.toDouble(), secondComp.toDouble(), systemTime)

        val firstOperator = Operator(
            1,
            firstOperatorTime.toDouble() - firstOperatorDeltaTime.toDouble(),
            firstOperatorTime.toDouble() + firstOperatorDeltaTime.toDouble(),
            systemTime,
            computer = firstComputer
        )

        val secondOperator = Operator(
            2,
            secondOperatorTime.toDouble() - secondOperatorDeltaTime.toDouble(),
            secondOperatorTime.toDouble() + secondOperatorDeltaTime.toDouble(),
            systemTime,
            computer = firstComputer
        )

        val thirdOperator = Operator(
            3,
            thirdOperatorTime.toDouble() - thirdOperatorDeltaTime.toDouble(),
            thirdOperatorTime.toDouble() + thirdOperatorDeltaTime.toDouble(),
            systemTime,
            computer = secondComputer
        )

        val result = controller.start(
            count.toInt(),
            requestsGenerator,
            listOf(firstOperator, secondOperator, thirdOperator),
            listOf(firstComputer, secondComputer)
        )

        resultView.data.add(
            "Сгенерировано: ${result.generateRequest}\n" +
            "Обработано: ${result.processedRequest}\n" +
                    "Отказано: ${result.failRequest}\n" +
                    "Вероятность отказа: ${result.probabilityFail.format(2)}%"
        )
    }
}
