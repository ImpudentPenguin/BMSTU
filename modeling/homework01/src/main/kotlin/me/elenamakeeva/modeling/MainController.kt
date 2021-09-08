package me.elenamakeeva.modeling

import org.apache.commons.math3.util.CombinatoricsUtils.factorial
import tornadofx.Controller
import kotlin.math.pow

class MainController : Controller() {

    /**
     * Получение вероятности отказа в обслуживании
     */
    fun getProbabilityOfFailure(lambda: Float, mu: Float, count: Int): Float {
        return getProbabilityNoOrdersInSystem(lambda, mu, count) * (get(lambda, mu).pow(count) / factorial(count))
    }

    /**
     * Получение вероятности того, что в системе нет заявок (p0)
     */
    private fun getProbabilityNoOrdersInSystem(lambda: Float, mu: Float, count: Int): Float {
        var res = get(lambda, mu) / factorial(1)

        if (count > 1)
            for (i in 2..count) {
                res += get(lambda, mu).pow(2) / factorial(i)
            }

        return 1f / res
    }

    /**
     * Получение вероятности того, что заявка будет обслуживаться
     */
    fun getProbabilityApplicationWillBeServed(lambda: Float, mu: Float, count: Int): Float {
        return 1 - getProbabilityOfFailure(lambda, mu, count)
    }

    /**
     * Получение среднего числа заявок обслуживания в единицу времени (абсолютная пропускная способность)
     */
    fun getAverageNumberServiceRequests(lambda: Float, mu: Float, count: Int): Float {
        return mu * getProbabilityApplicationWillBeServed(lambda, mu, count)
    }

    /**
     * Получение среднего числа занятых каналов
     */
    fun getAverageNumberBusyChannels(lambda: Float, mu: Float, count: Int): Float {
        return getAverageNumberServiceRequests(lambda, mu, count) / mu
    }

    fun get(lambda: Float, mu: Float): Float = lambda / mu
}