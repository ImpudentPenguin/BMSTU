package org.emakeeva.tisd

import org.emakeeva.tisd.Data.filePath
import org.emakeeva.tisd.Data.graph10
import org.emakeeva.tisd.Data.graph6
import org.emakeeva.tisd.Data.graph8
import org.emakeeva.tisd.Data.iteration
import org.emakeeva.tisd.models.Graph
import org.emakeeva.tisd.workers.MainWorker.printGraph
import org.emakeeva.tisd.workers.MainWorker.printTable
import org.emakeeva.tisd.workers.MainWorker.runAntAlgorithm
import org.emakeeva.tisd.workers.MainWorker.runBFAlgorithm
import org.emakeeva.tisd.workers.Writer.close
import org.emakeeva.tisd.workers.Writer.openStream
import org.emakeeva.tisd.workers.Writer.write
import java.io.File

fun main(args: Array<String>) {
    val resultAnt6 = mutableListOf<Double>()
    val resultAnt10 = mutableListOf<Double>()

    openStream(filePath.absolutePath)
    write("Входные данные", "")
    printGraph(graph6)
    printGraph(graph8)
    printGraph(graph10)

    write("Метод", "Полный перебор")
    val resultBF6 = runBFAlgorithm(graph6)
    runBFAlgorithm(graph8)
    val resultBF10 = runBFAlgorithm(graph10)

    write("Метод", "Муравьиный алгоритм")
    val distanceG6 = runAntAlgorithm(graph6, iteration)
    runAntAlgorithm(graph8, iteration)
    val distanceG10 = runAntAlgorithm(graph10, iteration)

    for (i in distanceG6.first().indices) {
        resultAnt6.add(minOf(distanceG6[0][i], distanceG6[1][i], distanceG6[2][i]))
    }

    for (i in distanceG10.first().indices) {
        resultAnt10.add(minOf(distanceG10[0][i], distanceG10[1][i], distanceG10[2][i]))
    }

    printTable(Pair(resultBF6, resultBF10), Pair(resultAnt6, resultAnt10))

    close()
}

object Data {
    const val iteration = 3
    val filePath = File("lab03.md")

    val graph6 = Graph(6, listOf(
            listOf(0.0, 14.4, 31.2, 98.6, 38.1, 12.2),
            listOf(14.4, 0.0, 59.9, 43.8, 19.0, 69.6),
            listOf(31.2, 59.9, 0.0, 89.9, 97.7, 1.4),
            listOf(98.6, 43.8, 89.9, 0.0, 55.2, 88.0),
            listOf(38.1, 19.0, 97.7, 55.2, 0.0, 79.8),
            listOf(12.2, 69.6, 1.4, 88.0, 79.8, 0.0)
    ))

    val graph8 = Graph(8, listOf(
            listOf(0.0, 27.6, 10.5, 35.5, 63.4, 37.2, 87.4, 84.5),
            listOf(27.6, 0.0, 17.5, 73.1, 78.6, 21.0, 13.4, 83.2),
            listOf(10.5, 17.5, 0.0, 1.7, 6.2, 3.4, 20.0, 51.4),
            listOf(35.5, 73.1, 1.7, 0.0, 49.6, 56.9, 22.3, 49.0),
            listOf(63.4, 78.6, 6.2, 49.6, 0.0, 34.4, 44.1, 26.1),
            listOf(37.2, 21.0, 3.4, 56.9, 34.4, 0.0, 8.2, 56.9),
            listOf(87.4, 13.4, 20.0, 22.3, 44.1, 8.2, 0.0, 90.8),
            listOf(84.5, 83.2, 51.4, 49.0, 26.1, 56.9, 90.8, 0.0)
    ))

    val graph10 = Graph(10, listOf(
            listOf(0.0, 8.5, 47.1, 87.0, 99.3, 57.3, 3.0, 36.1, 78.8, 54.1),
            listOf(8.5, 0.0, 88.8, 35.6, 37.9, 83.2, 20.4, 56.7, 13.2, 78.9),
            listOf(47.1, 88.8, 0.0, 33.5, 22.9, 9.9, 22.4, 54.3, 17.4, 16.4),
            listOf(87.0, 35.6, 33.5, 0.0, 73.8, 61.1, 78.2, 51.4, 43.5, 28.6),
            listOf(99.3, 37.9, 22.9, 73.8, 0.0, 45.2, 95.3, 92.5, 53.2, 99.5),
            listOf(57.3, 83.2, 9.9, 61.1, 45.2, 0.0, 88.5, 73.7, 95.1, 70.8),
            listOf(3.0, 20.4, 22.4, 78.2, 95.3, 88.5, 0.0, 65.7, 85.1, 29.1),
            listOf(36.1, 56.7, 54.3, 51.4, 92.5, 73.7, 65.7, 0.0, 44.2, 13.6),
            listOf(78.8, 13.2, 17.4, 43.5, 53.2, 95.1, 85.1, 44.2, 0.0, 75.6),
            listOf(54.1, 78.9, 16.4, 28.6, 99.5, 70.8, 29.1, 13.6, 75.6, 0.0)
    ))

    val params = listOf(0.001, 0.25, 0.5, 0.75, 1.0)
    val tmax = listOf(50, 100, 150, 200, 500)
}