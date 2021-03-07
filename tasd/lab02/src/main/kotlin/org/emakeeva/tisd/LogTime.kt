package org.emakeeva.tisd

import java.lang.management.ManagementFactory

typealias MainTime = Double
typealias CpuTime = Double

/**
 * Класс для работы со временем
 */
class LogTime {

    private val threadMXBean = ManagementFactory.getThreadMXBean()
    private val id = Thread.currentThread().id

    /**
     * Возвращение общего времени процессора для текущего потока в наносекундах
     */
    fun getCpuTime(): Long {
        return threadMXBean.getThreadCpuTime(id)
    }

    /**
     * Включение измерения процессорного времени
     */
    fun cpuTimeEnable(isEnabled: Boolean) {
        threadMXBean.isThreadCpuTimeEnabled = isEnabled
    }

    /**
     * Получение общего времени начала работы
     */
    fun getStartTime(): Long {
        return System.nanoTime()
    }

    /**
     * Получение общего времени завершения работы
     */
    fun getTime(start: Long): Long {
        return (System.nanoTime() - start)
    }
}