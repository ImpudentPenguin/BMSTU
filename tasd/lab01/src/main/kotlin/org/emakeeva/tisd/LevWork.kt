package org.emakeeva.tisd

import java.lang.Integer.min

typealias FirstWord = String
typealias SecondWord = String

/**
 * Методы алгоритмов
 */
class LevWork {

    var matrix: Matrix? = null

    /**
     * Расстояние Левенштейна рекурсивно
     * @param words пара слов
     * @param firstSize длина первого слова
     * @param secondSize длина второго слова
     */
    fun recursionLev(words: Pair<FirstWord, SecondWord>, firstSize: Int, secondSize: Int): Int {
        if (firstSize == 0) {
            return secondSize
        }

        if (secondSize == 0) {
            return firstSize
        }

        val tmp = min(
                recursionLev(words, firstSize, secondSize - 1),
                recursionLev(words, firstSize - 1, secondSize)
        ) + 1
        return min(
                tmp,
                recursionLev(words, firstSize - 1, secondSize - 1) + getFine(
                        words.first[firstSize - 1],
                        words.second[secondSize - 1]
                )
        )
    }

    /**
     * Расстояние Левенштейна матрично
     * @param words пара слов
     */
    fun matrixLev(words: Pair<FirstWord, SecondWord>): Int {
        val firstSize = words.first.length
        val secondSize = words.second.length

        if (firstSize == 0) {
            return secondSize
        }

        if (secondSize == 0) {
            return firstSize
        }

        val matrix = createMatrix(firstSize + 1, secondSize + 1)

        for (i in 0..firstSize) {
            for (j in 0..secondSize) {
                if (i == 0)
                    matrix[i, j] = j
                if (j == 0)
                    matrix[i, j] = i
            }
        }

        var fine: Int // штраф
        for (i in 1..firstSize) {
            for (j in 1..secondSize) {
                fine = if (words.first[i - 1] == words.second[j - 1])
                    0
                else
                    1
                matrix[i, j] = min(min(matrix[i - 1, j] + 1, matrix[i, j - 1] + 1), matrix[i - 1, j - 1] + fine)
            }
        }

        this.matrix = matrix
        return matrix[firstSize, secondSize]
    }

    /**
     * Расстояние Дамерау - Левенштейна матрично
     * @param words пара слов
     */
    fun matrixDM(words: Pair<FirstWord, SecondWord>): Int {
        val firstSize = words.first.length
        val secondSize = words.second.length

        if (firstSize == 0) {
            return secondSize
        }

        if (secondSize == 0) {
            return firstSize
        }

        val matrix = createMatrix(firstSize + 1, secondSize + 1)

        for (i in 0..firstSize) {
            for (j in 0..secondSize) {
                if (i == 0)
                    matrix[i, j] = j
                if (j == 0)
                    matrix[i, j] = i
            }
        }

        var fine: Int // штраф

        for (i in 1..firstSize) {
            for (j in 1..secondSize) {
                fine = if (words.first[i - 1] == words.second[j - 1])
                    0
                else
                    1
                matrix[i, j] = minOf(matrix[i - 1, j] + 1, matrix[i, j - 1] + 1, matrix[i - 1, j - 1] + fine)
                if (i > 1 && j > 1 && words.first[i - 1] == words.second[j - 2] && words.first[i - 2] == words.second[j - 1]) {
                    matrix[i, j] = min(matrix[i, j], matrix[i - 2, j - 2] + fine)
                }
            }
        }

        this.matrix = matrix
        return matrix[firstSize, secondSize]
    }

    /**
     * Создание матрицы
     * @param height высота матрицы
     * @param width ширина матрицы
     */
    private fun createMatrix(height: Int, width: Int): Matrix = Matrix(height, width)

    /**
     * Получение штрафа
     * @param a первый символ сравнения
     * @param b второй символ сравнения
     */
    private fun getFine(a: Char, b: Char): Int {
        return if (a != b)
            1
        else
            0
    }
}