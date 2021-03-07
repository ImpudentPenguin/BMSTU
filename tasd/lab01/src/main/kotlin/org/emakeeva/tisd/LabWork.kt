package org.emakeeva.tisd

/**
 * Методы работы алгоритмов
 */
object LabWork {

    private lateinit var firstWord: String
    private lateinit var secondWord: String
    private val work = LevWork()
    private val logTime = LogTime()

    private var startCpu: Long = 0
    private var startTime: Long = 0
    private var time = 0.0
    private var cpuTime = 0.0

    fun startRecLev() {
        base(Method.LEV_REC) {
            work.recursionLev(Pair(firstWord, secondWord), firstWord.length, secondWord.length)
        }
    }

    fun startMatrixLev() {
        base(Method.LEV_MATRIX) {
            work.matrixLev(Pair(firstWord, secondWord))
        }
    }

    fun startMatrixDM() {
        base(Method.DM_MATRIX) {
            work.matrixDM(Pair(firstWord, secondWord))
        }
    }

    /**
     * Базовый метод работы алгоритмов
     */
    private fun base(method: Method, start: () -> Int) {
        logTime.cpuTimeEnable(true)
        var result = 0
        time = 0.0
        cpuTime = 0.0

        for (i in 1..100) {
            startTime = logTime.getStartTime()
            startCpu = logTime.getCpuTime()
            result = start()
            finishIteration(i)
        }

        printFinish(result, work.matrix, method)
    }

    /**
     * Запрос ввода данных
     */
    fun read() {
        println("Введите первое слово:")
        firstWord = Reader.read()
        println("Введите второе слово:")
        secondWord = Reader.read()
    }

    /**
     * Завершение итерации
     */
    private fun finishIteration(iteration: Int) {
        val timeNano = logTime.getTime(startTime)
        val cpuTimeNano = logTime.getCpuTime() - startCpu
        cpuTime += cpuTimeNano
        time += timeNano

        printResult(iteration, time = timeNano, cpuTime = cpuTimeNano)
    }

    /**
     * Вывод данных работы алгоритма
     */
    private fun printFinish(result: Int, matrix: Matrix?, method: Method) {
        println(String.format("Вход: %s, %s", firstWord, secondWord))
        println(String.format("Метод: %s", method))
        println(String.format("Расстояние: %d", result))
        println(String.format("Среднее общее время одной итерации в наносекундах: %s", (time / 100).format(2)))
        println(String.format("Среднее процессорное время одной итерации в наносекундах: %s\n", (cpuTime / 100).format(2)))
        matrix?.let {
            println("Матрица:")
            println(it.toString())
        }
    }

    /**
     * Вывод данных итерации
     */
    private fun printResult(iteration: Int, time: Long, cpuTime: Long) {
        print(String.format("Отчет итерации %d:\n", iteration))
        println(String.format("Общее время работы в наносекундах: %d", time))
        println(String.format("Процессорное время работы в наносекундах: %d", cpuTime))
        print("\n")
    }
}