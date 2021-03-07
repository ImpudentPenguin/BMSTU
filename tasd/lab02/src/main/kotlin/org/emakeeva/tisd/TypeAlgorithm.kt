package org.emakeeva.tisd

enum class TypeAlgorithm(private val title: String) {
    START("Стартовый тип"),
    BASE("Базовый алгоритм"),
    VIN("Алгоритм Винограда"),
    OPTIM("Алгоритм Винограда оптимизированный");

    fun getTitle(): String = title
}