package org.emakeeva.tisd.workers

import org.emakeeva.tisd.Data.params
import org.emakeeva.tisd.Data.tmax
import org.emakeeva.tisd.models.Graph
import org.emakeeva.tisd.utils.Utils
import org.emakeeva.tisd.utils.Utils.getRouteString
import org.emakeeva.tisd.workers.Writer.write
import org.emakeeva.tisd.workers.Writer.writeLine
import kotlin.math.abs

typealias GRAPH6 = Double
typealias GRAPH10 = Double

object MainWorker {
    private val antAlgorithmWorker = AntAlgorithmWorker()
    private val bruteForceWorker = BruteForceWorker()

    fun printGraph(graph: Graph) {
        write("Количество вершин графа", graph.numberOfVertices.toString())
        write("Матрица смежности (дистанция)", "")
        write("", Utils.getMatrixString(graph.adjacencyMatrix))
        writeLine()
    }

    fun printTable(resultBF: Pair<GRAPH6, GRAPH10>, resultAnt: Pair<List<GRAPH6>, List<GRAPH10>>) {
        writeLine("==============================================")
        writeLine(String.format("%5s %15s %10s %20s %28s %20s %30s",
                "[ALPHA]", "[ RO ]", "[TMAX]", "[ GRAPH6 L_best ]",
                "[ GRAPH6 L_best - L_etalon ]", "[ GRAPH10 L_best ]", "[ GRAPH10 L_best - L_etalon ]"))
        for (i in params.indices) {
            writeLine(String.format("%5s %15s %10s %20s %28s %20s %30s",
                    params[i], params[i], tmax[i],
                    String.format("%.2f", resultAnt.first[i]), String.format("%.2f", abs(resultAnt.first[i] - resultBF.first)),
                    String.format("%.2f", resultAnt.second[i]),String.format("%.2f",  abs(resultAnt.second[i] - resultBF.second))))
        }
        writeLine("==============================================")
    }

    fun runBFAlgorithm(graph: Graph): Double {
        val path = bruteForceWorker.getPath(graph)
        write("Граф с размерностью", graph.numberOfVertices.toString())
        write("Кратчайший путь", getRouteString(path.route))
        write("Расстояние", String.format("%.2f", path.distance))
        writeLine()
        return path.distance
    }

    fun runAntAlgorithm(graph: Graph, iteration: Int): List<List<Double>> {
        val result = MutableList(iteration) { MutableList(params.size) { 0.0 } }
        val temp = mutableListOf<Double>()
        write("Граф с размерностью", graph.numberOfVertices.toString())

        for (i in 1..iteration) {
            writeLine()
            write("Итерация", i.toString())
            temp.clear()
            for (index in params.indices) {
                temp.add(iterationAntAlgorithm(graph, alpha = params[index], ro = params[index], tmax = tmax[index]))
            }
            result[i - 1] = temp
        }

        writeLine()
        write("Матрица результатов", "")
        for (list in result) {
            write("", list.toString())
        }
        writeLine()
        return result
    }

    private fun iterationAntAlgorithm(graph: Graph, alpha: Double, ro: Double, tmax: Int): Double {
        antAlgorithmWorker.changeParams(alpha, ro, tmax)
        val path = antAlgorithmWorker.getPath(graph)
        writeLine()
        write("Оптимальный маршрут", getRouteString(path.route))
        write("Расстояние", String.format("%.2f", path.distance))
        write("Время жизни колонии", "$tmax суток")
        write("Испарение феромона", ro.toString())
        write("Влияние феромона", alpha.toString())
        return path.distance
    }
}