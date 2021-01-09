package org.emakeeva.tisd.workers

import org.emakeeva.tisd.models.Graph
import org.emakeeva.tisd.models.Path
import org.emakeeva.tisd.utils.*

class BruteForceWorker {
    private var bestPath = Path(Double.MAX_VALUE)

    fun getPath(graph: Graph): Path {
        val distanceMatrix = graph.adjacencyMatrix
        validateGraph(graph)

        solveTSP(graph.numberOfVertices, distanceMatrix)
        return bestPath
    }

    private fun solveTSP(totalCities: Int, distanceMatrix: List<List<Double>>) {
        bestPath = Path(Double.MAX_VALUE)
        val cities = ArrayList<Int>()
        for (i in 0 until totalCities) {
            cities.add(i)
        }
        val startCity = cities[0]
        val currentDistance = 0.0
        bruteForceSearch(distanceMatrix, cities, startCity, currentDistance)
    }

    private fun bruteForceSearch(
            distanceMatrix: List<List<Double>>,
            cities: ArrayList<Int>,
            startCity: Int,
            currentDistance: Double
    ) {
        if (startCity < cities.size - 1) {
            for (i in startCity until cities.size) {
                var tempCity = cities[i]
                cities[i] = cities[startCity]
                cities[startCity] = tempCity
                val current = computeDistance(cities, distanceMatrix)
                bruteForceSearch(distanceMatrix, cities, startCity + 1, current)
                tempCity = cities[i]
                cities[i] = cities[startCity]
                cities[startCity] = tempCity
            }
        } else {
            if (bestPath.distance > currentDistance) {
                bestPath.distance = currentDistance
                bestPath.route = ArrayList(cities)
            }
        }
    }

    private fun computeDistance(cities: ArrayList<Int>, distanceMatrix: List<List<Double>>): Double {
        var distance = 0.0
        for (i in 0 until cities.size - 1) {
            distance += distanceMatrix[cities[i]][cities[i + 1]]
        }
        distance += distanceMatrix[cities[cities.size - 1]][cities[0]]
        return distance
    }

    private fun validateGraph(graph: Graph) {
        when {
            graph.adjacencyMatrix.size != graph.adjacencyMatrix.firstOrNull()?.size ?: 0 -> throw GraphIncorrectDimensionException()
            graph.numberOfVertices <= 0 -> throw GraphHasntVerticesException()
            graph.numberOfVertices == 1 -> throw GraphHasOneVertexException()
            graph.adjacencyMatrix.isEmpty() -> throw GraphHasntEdgesException()
            graph.adjacencyMatrix.toString().contains(Regex("-\\d")) -> throw GraphNegativeDistanceException()
            //checkMatrixSymmetry(graph) -> throw MatrixIsNotSymmetricException()
            checkMatrixForZeroDistance(graph) -> throw MatrixZerosDistanceException()
        }
    }

    private fun checkMatrixForZeroDistance(graph: Graph): Boolean {
        var isZeroDistance = true
        graph.adjacencyMatrix.forEach {
            it.forEach { distance ->
                if (distance != 0.0)
                    isZeroDistance = false
            }
        }

        return isZeroDistance
    }
}