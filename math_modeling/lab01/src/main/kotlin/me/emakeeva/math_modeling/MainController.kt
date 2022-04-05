package me.emakeeva.math_modeling

import tornadofx.Controller
import kotlin.math.abs

class MainController : Controller() {

    private val selectionX = mutableListOf<Float>()
    private val result = mutableListOf<MutableList<Float>>()

    fun calcPolinomy(paramX: Float, paramN: Int, nodes: MutableMap<Float, Float>): Pair<MutableList<MutableList<Float>>, Float> {
        selectionX.clear()
        result.clear()
        result.add(mutableListOf())

        val xList = nodes.keys.toMutableList()
        var delta = Float.MAX_VALUE
        var index: Int = -1

        xList.forEachIndexed { i, x ->
            val d = abs(x - paramX)
            if (d < delta) {
                delta = d
                index = i
            }
        }

        selectionX.add(xList[index])

        var isAbs = false
        var diff = -1

        when (paramN) {
            1 -> {
                if (index + 1 >= xList.size && index - 1 > -1)
                    selectionX.add(xList[index - 1])
                else selectionX.add(xList[index + 1])
            }
            else -> {
                while (selectionX.size != paramN + 1) {
                    if (isAbs)
                        selectionX.add(xList[index + abs(diff)])
                    else selectionX.add(xList[index + diff])

                    isAbs = !isAbs

                    if (!isAbs)
                        diff -= 1
                }
            }
        }

        selectionX.sort()
        
        selectionX.forEachIndexed { ind, x ->
            if (ind + 1 == selectionX.size)
                return@forEachIndexed

            result[0].add(getDelimiter1Separator(nodes[x]!!, nodes[selectionX[ind + 1]]!!, x, selectionX[ind + 1]))
        }

        var step = 2

        while (result.last().size != 1 && paramN != 0) {
            val tempList = mutableListOf<Float>()

            for (i in 0 until selectionX.size) {
                if (i + step >= selectionX.size)
                    break

                val res = result.last().subList(i, i + 2).reduce(::difference)
                val znam = selectionX[i] - selectionX[i + step]
                tempList.add(res / znam)
            }

            step++
            result.add(tempList)
        }

        return result to findResult(nodes, paramX)
    }

    private fun findResult(nodes: MutableMap<Float, Float>, paramX: Float): Float {
        var res = nodes[selectionX[0]]!!

        result.forEachIndexed { index, list ->

            var temp = 1f
            for (i in 0..index)
                temp *= (paramX - selectionX[i])

            res += temp * (list.firstOrNull() ?: 0f)
        }

        return res
    }

    private fun getDelimiter1Separator(y0: Float, y1: Float, x0: Float, x1: Float): Float = (y0 - y1) / (x0 - x1)

    private fun difference(a: Float, b: Float): Float = a - b
}