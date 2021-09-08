package me.elenamakeeva.modeling

import me.elenamakeeva.modeling.models.*
import tornadofx.Controller

class MainController : Controller() {

    fun start(
        totalTasks: Int,
        requestGenerator: RequestGenerator,
        operators: List<Operator>,
        computers: List<Computer>
    ): ResultInfo {
        val info = ResultInfo()
        var generatedTasks = 0

        while (info.processedRequest < totalTasks) {
            if (info.processedRequest < totalTasks) {
                val request = requestGenerator.getRequest(generatedTasks)
                request?.let {
                    generatedTasks++
                    operators.firstOrNull {
                        it.isFree
                    }?.acceptRequest(request) ?: run {
                        info.failRequest++
                    }
                }
            }

            operators.forEach {
                it.updateState()
            }
            computers.forEach {
                val result = it.updateState()
                if (result == RequestState.FINISH)
                    info.processedRequest++
            }
        }

        if (info.failRequest + info.processedRequest < generatedTasks)
            info.failRequest += generatedTasks - (info.failRequest + info.processedRequest)

        info.generateRequest = generatedTasks
        info.probabilityFail = getProbabilityOfFailure(info.failRequest, info.failRequest + info.processedRequest)

        return info
    }

    private fun getProbabilityOfFailure(countFail: Int, count: Int): Float {
        return (countFail.toFloat() / count.toFloat()) * 100f
    }
}