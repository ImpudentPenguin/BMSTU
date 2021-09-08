package me.elenamakeeva.modeling.view

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.TextField
import me.elenamakeeva.modeling.MainController
import me.elenamakeeva.modeling.Styles
import me.elenamakeeva.modeling.models.*
import tornadofx.*

class MainView : View("Домашнее задание №1") {

    private var count: TextField by singleAssign()
    private var lambda: TextField by singleAssign()
    private var mu: TextField by singleAssign()
    private var hours: TextField by singleAssign()
    private val controller: MainController by inject()
    private val data: ObservableList<String> = FXCollections.observableArrayList()
    private var events: MutableList<Event> = mutableListOf()
    private var channels = mutableListOf<Channel>()

    override val root = vbox {
        minWidth = 1000.0
        minHeight = 600.0

        label(
            "Трекханальная СМО обслуживает пуассоновский поток заявок.\n" +
                    "Время между заявками является случайной величиной распределения по показательному закону.\n" +
                    "Интенсивность потока заявок - 10 заявок в минуту.\n" +
                    "Время обслуживания одной заявки также распределено по показательному закону.\n" +
                    "Интенсивность потока обслуживания - 4 заявки в минуту.\n" +
                    "Если заявка приходит в момент времени, когда все каналы заняты - она получает отказ в обслуживании.\n" +
                    "Необходимо провести имитационное моделирование в течение 100 часов и определить параметры СМО:\n" +
                    "1) Вероятность отказа в обслуживании\n" +
                    "2) Вероятность того, что заявка будет обслуживаться\n" +
                    "3) Среднее число заявок обслуживания в единицу времени (абсолютная пропускная способность)\n" +
                    "4) Среднее число занятых каналов"
        ) {
            addClass(Styles.heading)
        }

        form().fieldset("Параметры").vbox(20) {
            field("Количество каналов") {
                count = textfield("3")
            }
            field("Введите интенсивность потока заявок в минуту") {
                lambda = textfield("10")
            }
            field("Введите интенсивность потока обслуживания в минуту") {
                mu = textfield("4")
            }
            field("Количество часов имитационного моделирования") {
                hours = textfield("100")
            }
        }

        borderpane {
            center = button("Подтвердить") {
                action {
                    start()
                }
            }
        }
    }

    private fun start() {
        val count = count.text.toInt()
        val lambda = lambda.text.toFloat()
        val mu = mu.text.toFloat()
        val min = hours.text.toFloat() * 60
        val generator = Generator(lambda)
        val apparatus = ServiceApparatus(mu)
        var currentQueueLength = 0
        var maxQueueLength = 0
        var processFlag = false
        events = mutableListOf(Event(EventType.GENERATOR, generator.getTime()))

        for (i in 0 until count) {
            channels.add(Channel(i))
        }

        val systemTime = System.currentTimeMillis()
        var channel: Channel? = null

        while (((System.currentTimeMillis() - systemTime) / 1000 / 60) < min) {
            val event = events.removeAt(0)

            when (event.type) {
                EventType.GENERATOR -> {
                    currentQueueLength++
                    if (currentQueueLength > maxQueueLength)
                        maxQueueLength = currentQueueLength
                    event.value += generator.getTime()
                    addEvent(event)
                    channel = channels.firstOrNull {
                        it.isFree
                    }

                    if (channel != null)
                        processFlag = true
                }
                EventType.APPARATUS -> {
                    channel?.isFree = true
                    processFlag = true
                }
            }

            if (processFlag) {
                if (currentQueueLength > 0) {
                    channel?.isFree = false
                    currentQueueLength--
                    addEvent(Event(EventType.APPARATUS, event.value + apparatus.getTime()))
                } else {
                    channels.forEach {
                        it.isFree = true
                    }
                }

                processFlag = false
            }
        }

        data.add(String.format("Длина очереди: %d", maxQueueLength))

        data.add(
            String.format(
                "Вероятность отказа в обслуживании: %f",
                controller.getProbabilityOfFailure(lambda, mu, count)
            )
        )

        data.add(
            String.format(
                "Вероятность того, что заявка будет обслужена: %f",
                controller.getProbabilityApplicationWillBeServed(lambda, mu, count)
            )
        )

        data.add(
            String.format(
                "Среднее число заявок обслуживания в единицу времени: %f",
                controller.getAverageNumberServiceRequests(lambda, mu, count)
            )
        )

        data.add(
            String.format(
                "Среднее число занятых каналов: %f",
                controller.getAverageNumberBusyChannels(lambda, mu, count)
            )
        )

        OutputView(data).openWindow()
    }

    private fun addEvent(event: Event) {
        var i = 0

        while (i < events.count() && events[i].value < event.value)
            i++

        if (0 < i && i < events.count())
            events.add(i - 1, event)
        else events.add(i, event)
    }
}
