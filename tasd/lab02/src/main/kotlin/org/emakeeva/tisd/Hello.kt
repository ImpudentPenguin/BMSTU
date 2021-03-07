package org.emakeeva.tisd

fun main(args: Array<String>) {
    println("Введите высоту и ширину первой матрицы в формате M*N:")
    val first = Reader.read()
    println("Введите высоту и ширину второй матрицы в формате N*Q:")
    val second = Reader.read()

    LabWork.apply {
        read(first, second)
        startBaseAlgorithm()
        startVinAlgorithm()
        startOptimVinAlgorithm()
    }
}

