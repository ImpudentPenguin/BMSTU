package me.emakeeva.math_modeling

import tornadofx.Controller
import kotlin.math.*

class MainController : Controller() {

    private val h = 1.0
    private val m = 2.0

    fun getResult(values: MutableList<ResultModel>): List<ResultModel> {
        val result = mutableListOf<ResultModel>()

        for (index in 0 until values.size) {
            val prevModel = if (index - 1 >= 0) values[index - 1] else null
            val currentModel = values[index]
            val nextModel = if (index + 1 < values.size) values[index + 1] else null
            val nextX2Model = if (index + 2 < values.size) values[index + 2] else null

            result.add(
                ResultModel(
                    x = currentModel.x,
                    y = currentModel.y,
                    first = nextModel?.y?.let { y -> right(currentModel.y, y, h) },
                    second = if (nextModel != null && prevModel != null)
                        center(prevModel.y, nextModel.y, h)
                    else null,
                    third = if (nextModel != null && nextX2Model != null) {
                        val f0 = right(currentModel.y, nextModel.y, h)
                        val f1 = right(currentModel.y, nextX2Model.y, m * h)

                        secondRunge(f0, f1)
                    } else null,
                    forth = if (nextModel != null)
                        alignVariables(currentModel.y, nextModel.y, currentModel.x, nextModel.x)
                    else null,
                    fifth = if (nextModel != null && prevModel != null)
                        secondFormula(currentModel.y, prevModel.y, nextModel.y, h)
                    else null,
                )
            )
        }

        return result
    }

    private fun right(y0: Double, y1: Double, h: Double) = ((y1 - y0) / h).round(3)

    private fun center(y1: Double, y2: Double, h: Double) = ((y2 - y1) / 2 * h).round(3)

    private fun secondFormula(y0: Double, y1: Double, y2: Double, h: Double) = ((y1 - 2 * y0 + y2) / h.pow(2)).round(3)

    private fun secondRunge(f0: Double, f1: Double): Double {
        val p = h
        return (f0 + (f0 - f1) / (m.pow(p) - 1)).round(3)
    }

    private fun alignVariables(y0: Double, y1: Double, x0: Double, x1: Double) =
        (((1 / y1 - 1 / y0) / (1 / x1 - 1 / x0)) * (y0.pow(2) / x0.pow(2))).round(3)

    private fun Double.round(n: Int): Double {
        val scale = (10.0).pow(n)
        return (this * scale).roundToInt() / scale
    }
}