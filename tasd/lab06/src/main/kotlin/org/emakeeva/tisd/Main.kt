package org.emakeeva.tisd

fun main() {
    val visited = mutableListOf<Char>()
    // первый граф
//    val chars = mutableListOf(
//        mutableListOf('A', 'B', 'C', 'D', '0', '0', '0', '0', '0', '0', '0', '0'),
//        mutableListOf('0', 'B', '0', '0', 'E', 'F', 'G', '0', '0', '0', '0', '0'),
//        mutableListOf('0', '0', 'C', '0', '0', '0', '0', 'I', 'K', '0', '0', '0'),
//        mutableListOf('0', '0', '0', 'D', '0', '0', '0', '0', '0', 'L', '0', '0'),
//        mutableListOf('0', '0', '0', '0', 'E', '0', '0', '0', '0', '0', '0', '0'),
//        mutableListOf('0', '0', '0', '0', '0', 'F', '0', '0', '0', '0', '0', '0'),
//        mutableListOf('0', '0', '0', '0', '0', '0', 'G', '0', '0', '0', '0', '0'),
//        mutableListOf('0', '0', '0', '0', '0', '0', '0', 'I', '0', '0', 'M', 'N'),
//        mutableListOf('0', '0', '0', '0', '0', '0', '0', '0', 'K', '0', '0', '0'),
//        mutableListOf('0', '0', '0', '0', '0', '0', '0', '0', '0', 'L', '0', '0'),
//        mutableListOf('0', '0', '0', '0', '0', '0', '0', '0', '0', '0', 'M', '0'),
//        mutableListOf('0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', 'N')
//    )

    // второй граф, пример из лекции
    val chars = listOf(
        listOf('А', 'Б', '0', 'Г', '0', '0', '0', '0', '0', '0'),
        listOf('0', 'Б', 'В', '0', 'Д', '0', '0', '0', '0', '0'),
        listOf('0', '0', 'В', '0', '0', '0', '0', '0', 'З', 'И'),
        listOf('0', '0', '0', 'Г', '0', 'Е', '0', 'Ж', '0', '0'),
        listOf('0', '0', '0', '0', 'Д', '0', '0', '0', '0', '0'),
        listOf('0', '0', '0', '0', '0', 'Е', '0', '0', '0', '0'),
        listOf('0', '0', '0', '0', '0', '0', 'Ё', '0', '0', '0'),
        listOf('0', '0', '0', '0', '0', '0', '0', 'Ж', '0', '0'),
        listOf('0', '0', '0', '0', '0', '0', '0', '0', 'З', '0'),
        listOf('0', '0', '0', '0', '0', '0', '0', '0', '0', 'И')
    )

    // третий граф
//    val chars = mutableListOf(
//        mutableListOf('1', '2', '0', '4', '0', '0', '0', '0'),
//        mutableListOf('0', '2', '3', '0', '0', '0', '0', '0'),
//        mutableListOf('0', '0', '3', '4', '5', '6', '0', '0'),
//        mutableListOf('0', '0', '0', '4', '0', '0', '0', '0'),
//        mutableListOf('0', '0', '0', '0', '5', '0', '7', '0'),
//        mutableListOf('0', '0', '0', '0', '0', '6', '7', '0'),
//        mutableListOf('0', '0', '0', '0', '0', '0', '7', '8'),
//        mutableListOf('0', '0', '0', '0', '0', '0', '0', '8')
//    )

    // обход в ширину
    val top = chars[0][0]
    val queue = Queue<Char>(NMAX = chars.size * chars.first().size, count = 0)
    queue.addElement(top)
    while (!queue.isEmpty() || !queue.isFull()) {
        val first = queue.topElement()
        if (!visited.contains(first)) {
            visited.add(queue.getTopElement())

            chars.last {
                it.contains(first)
            }.forEach { ch ->
                if (ch != first && ch != '0') {
                    queue.addElement(ch)
                }
            }
        } else {
            queue.getTopElement()
        }
    }

    println("========= Исходный граф =========")
    println(Utils.getMatrixString(chars))
    println("========= Обход в ширину =========")
    println(visited)

    // обход в глубину
    visited.clear()
    val topDepth = chars[0][0]
    val stack = Stack<Char>(NMAX = chars.size * chars.first().size, count = 0)
    stack.addElement(topDepth)
    while (!stack.isEmpty() || !stack.isFull()) {
        val first = stack.topElement()
        if (!visited.contains(first)) {
            visited.add(stack.getTopElement())

            chars.last {
                it.contains(first)
            }.sortedDescending().forEach { ch ->
                if (ch != first && ch != '0') {
                    stack.addElement(ch)
                }
            }
        } else {
            stack.getTopElement()
        }
    }

    println("========= Обход в глубину =========")
    println(visited)
}