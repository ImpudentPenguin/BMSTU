package org.emakeeva.tisd

/**
 * Перечисление алгоритмов
 */
enum class Method(private val title: String) {
    LEV_REC("расстояние Левенштейна рекурсивно"),
    LEV_MATRIX("расстояние Левенштейна матрично"),
    DM_MATRIX("расстояние Дамерау - Левенштейна матрично");

    override fun toString(): String = title
}