package me.elenamakeeva.modeling

import me.elenamakeeva.modeling.models.Event
import me.elenamakeeva.modeling.models.EventType
import me.elenamakeeva.modeling.models.Generator
import me.elenamakeeva.modeling.models.ServiceApparatus
import tornadofx.Controller
import kotlin.random.Random

class MainController : Controller() {

    private var events: MutableList<Event> = mutableListOf()

    /**
     * Получение минимального времени по принципу DeltaT
     */
    fun getMinTimePrincipleDeltaT(
        a: Double,
        b: Double,
        mu: Double,
        sigma: Double,
        totalTasks: Int,
        percent: Double,
        deltaT: Double
    ): String {
        val generator = Generator(a, b)
        val apparatus = ServiceApparatus(mu, sigma)
        var processedTasks = 0
        var currentTime = deltaT
        var generatorTime = generator.getTime()
        var prevGeneratorTime = 0.0
        var apparatusTime = 0.0
        var currentQueueLength = 0f
        var maxQueueLength = 0f
        var isFree = true
        var wasFree: Boolean

        while (processedTasks < totalTasks) {
            if (currentTime > generatorTime) {
                currentQueueLength++
                if (currentQueueLength > maxQueueLength)
                    maxQueueLength = currentQueueLength

                prevGeneratorTime = generatorTime
                generatorTime += generator.getTime()
            }

            if (currentTime > apparatusTime) {
                if (currentQueueLength > 0) {
                    wasFree = isFree
                    if (isFree)
                        isFree = false
                    else {
                        processedTasks++
                        if (Random.nextDouble(0.0, 1.0) <= percent)
                            currentQueueLength++
                    }
                    currentQueueLength -= 1
                    if (wasFree)
                        apparatusTime = prevGeneratorTime + apparatus.getTime()
                    else apparatusTime += apparatus.getTime()
                } else
                    isFree = true
            }

            currentTime += deltaT
        }

        return "Результат: длина - $maxQueueLength, % возврата - $percent"
    }

    /**
     * Получение минимального времени по событийному принципу
     */
    fun getMinTimePrincipleEvent(
        a: Double,
        b: Double,
        mu: Double,
        sigma: Double,
        totalTasks: Int,
        percent: Double
    ): String {
        val generator = Generator(a, b)
        val apparatus = ServiceApparatus(mu, sigma)
        var processedTasks = 0
        var currentQueueLength = 0
        var maxQueueLength = 0
        var isFree = true
        var processFlag = false
        events = mutableListOf(Event(EventType.GENERATOR, generator.getTime()))

        while (processedTasks < totalTasks) {
            val event = events.removeAt(0)

            when (event.type) {
                EventType.GENERATOR -> {
                    currentQueueLength++
                    if (currentQueueLength > maxQueueLength)
                        maxQueueLength = currentQueueLength
                    event.value += generator.getTime()
                    addEvent(event)
                    if (isFree)
                        processFlag = true
                }
                EventType.APPARATUS -> {
                    processedTasks++
                    if (Random.nextDouble(0.0, 1.0) <= percent)
                        currentQueueLength++
                    processFlag = true
                }
            }

            if (processFlag) {
                isFree = if (currentQueueLength > 0) {
                    currentQueueLength--
                    addEvent(Event(EventType.APPARATUS, event.value + apparatus.getTime()))
                    false
                } else true

                processFlag = false
            }
        }

        return "Результат: длина - $maxQueueLength, % возврата - $percent"
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