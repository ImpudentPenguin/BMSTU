package org.emakeeva.tisd

data class Stack<T>(
    private var NMAX: Int = 0, // максимальное количество элементов в стеке
    private var count: Int = 0, // количество элементов в стеке
    private var pool: MutableList<T> = mutableListOf() // хранилище
) {

    /**
     * Проверка полноты стека
     */
    fun isFull(): Boolean {
        return pool.size == count
    }

    /**
     * Проверка пустоты стека
     */
    fun isEmpty(): Boolean {
        return pool.isEmpty()
    }

    /**
     * Просмотр верхнего элемента
     */
    fun topElement(): T {
        return pool.last()
    }

    /**
     * Добавление элемента в вершину стека
     */
    fun addElement(element: T) {
        count++
        pool.add(element)
    }

    /**
     * Извлечение элемента из вершины стека
     */
    fun getTopElement(): T {
        val element = pool.last()
        pool.remove(element)
        count--
        return element
    }
}