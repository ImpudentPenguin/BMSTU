package org.emakeeva.tisd

/**
 * Хранение координат определенной ячейки матрицы
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс матрицы
 */
interface IMatrix<E> {
    val height: Int
    val width: Int

    operator fun get(row: Int, column: Int): E
    operator fun get(cell: Cell): E = get(cell.row, cell.column)

    operator fun set(row: Int, column: Int, value: E)
    operator fun set(cell: Cell, value: E) = set(cell.row, cell.column, value)
}