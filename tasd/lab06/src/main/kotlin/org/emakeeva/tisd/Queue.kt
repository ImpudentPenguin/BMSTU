package org.emakeeva.tisd

data class Queue<T>(
    private var NMAX: Int = 0, // максимальное количество элементов в очереди
    private var count: Int = 0, // количество элементов в очереди
    private var pool: MutableList<T> = mutableListOf() // хранилище
) {

    /**
     * Проверка полноты очереди
     */
    fun isFull(): Boolean {
        return pool.size == count
    }

    /**
     * Проверка пустоты очереди
     */
    fun isEmpty(): Boolean {
        return pool.isEmpty()
    }

    /**
     * Просмотр верхнего элемента
     */
    fun topElement(): T {
        return pool.first()
    }

    /**
     * Добавление элемента в вершину очереди
     */
    fun addElement(element: T) {
        count++
        pool.add(element)
    }

    /**
     * Извлечение элемента из вершины очереди
     */
    fun getTopElement(): T {
        val element = pool.first()
        pool.remove(element)
        count--
        return element
    }
}