package org.emakeeva.tisd.utils

class GraphHasntVerticesException : Exception("Graph hasn't vertices") // без вершин
class GraphHasOneVertexException : Exception("Graph has one vertex") // тривиальный
class GraphHasntEdgesException : Exception("Graph hasn't edges") // нулевой граф (без ребер)
class MatrixInitException(message: String) : Exception(message) // ошибка инициализации матрицы
class MatrixZerosDistanceException : Exception("Adjacency matrix consists of zeros") // матрица смежности состоит из нулей
class GraphNegativeDistanceException : Exception("Graph edges contain negative distance") // содержит отрицательные значения
class MatrixIsNotSymmetricException : Exception("Matrix is not symmetric") // матрица не симметрична
class GraphIncorrectDimensionException : Exception("Adjacency matrix is not square") // матрица не квадратная