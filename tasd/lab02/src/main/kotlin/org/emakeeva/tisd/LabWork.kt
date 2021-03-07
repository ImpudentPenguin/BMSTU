package org.emakeeva.tisd

import java.lang.StringBuilder

/**
 * Методы работы алгоритмов
 */

object LabWork {

    private val work = MatrixWork()
    private val logTime = LogTime()
    private var firstMatrix: Matrix? = null
    private var secondMatrix: Matrix? = null

    private var startCpu: Long = 0
    private var startTime: Long = 0
    private var time = 0.0
    private var cpuTime = 0.0

    const val COUNT = 100

    /**
     * Запуск базового алгоритма
     */
    fun startBaseAlgorithm(typeAlgorithm: TypeAlgorithm = TypeAlgorithm.BASE): Pair<Matrix?, String> {
        return base(typeAlgorithm) {
            work.baseMultiplyMatrix(it)
        }
    }

    /**
     * Запуска алгоритма Винограда
     */
    fun startVinAlgorithm(): Pair<Matrix?, String> {
        return base(TypeAlgorithm.VIN) {
            work.vinMultiplyMatrix(it)
        }
    }

    /**
     * Запуск оптимизированного алгоритма Винограда
     */
    fun startOptimVinAlgorithm(): Pair<Matrix?, String> {
        return base(TypeAlgorithm.OPTIM) {
            work.optimVinMultiplyMatrix(it)
        }
    }

    /**
     * Чтение размерности матриц
     */
    fun read(firstSize: String, secondSize: String) {
        println("=== WARNING ===")
        println("Элементы матрицы заполняются рандомными значениями")
        println("===============\n")
        val first = firstSize.split("*")
        firstMatrix = work.createMatrix(Integer.parseInt(first[0]), Integer.parseInt(first[1]))
        val second = secondSize.split("*")
        secondMatrix = work.createMatrix(Integer.parseInt(second[0]), Integer.parseInt(second[1]))
    }

    private fun base(
        typeAlgorithm: TypeAlgorithm,
        algorithm: (Pair<FirstMatrix, SecondMatrix>) -> Matrix?
    ): Pair<Matrix?, String> {
        logTime.cpuTimeEnable(true)
        var result: Matrix? = null
        time = 0.0
        cpuTime = 0.0

        for (i in 1..COUNT) {
            startTime = logTime.getStartTime()
            startCpu = logTime.getCpuTime()

            firstMatrix?.let { firstMatrix ->
                secondMatrix?.let { secondMatrix ->
                    algorithm(Pair(firstMatrix, secondMatrix))?.let { matrix ->
                        result = matrix
                    }
                }
            } ?: println("Что-то пошло не так. Входные данные не были инициализированы.")

            finishIteration()
        }

        return Pair(result, getResult(typeAlgorithm))
    }

    /**
     * Завершение итерации
     */
    private fun finishIteration() {
        val timeNano = logTime.getTime(startTime)
        val cpuTimeNano = logTime.getCpuTime() - startCpu
        cpuTime += cpuTimeNano
        time += timeNano
    }

    private fun printResult(matrix: Matrix?, typeAlgorithm: TypeAlgorithm) {
        println("${typeAlgorithm.getTitle()}\n")
        println("=== Входные данные ===")
        println("Первая матрица:")
        println(firstMatrix)
        println("Вторая матрица:")
        println(secondMatrix)
        println("=== Результат ===")
        matrix?.let {
            println(it)
        } ?: println("Матрицы не совместимы.")

        println(
            String.format(
                "Среднее общее время одной итерации в наносекундах: %s",
                (time / COUNT).format(2)
            )
        )
        println(
            String.format(
                "Среднее процессорное время одной итерации в наносекундах: %s\n",
                (cpuTime / COUNT).format(2)
            )
        )
    }

    private fun getResult(typeAlgorithm: TypeAlgorithm): String {
        val builder = StringBuilder()
        builder.append(typeAlgorithm.getTitle() + "\n\n")
        builder.append("Среднее общее время одной итерации в наносекундах: ${ (time / COUNT).format(2)}\n")
        builder.append("Среднее процессорное время одной итерации в наносекундах: ${ (cpuTime / COUNT).format(2)}")

        return builder.toString()
    }
}


