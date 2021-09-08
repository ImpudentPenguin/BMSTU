package me.elenamakeeva.modeling.models

data class Operator(
    val id: Int,
    val min: Double,
    val max: Double,
    val systemTime: Double,
    var isFree: Boolean = true,
    val computer: Computer
): BaseGenerator(min, max, systemTime) {
    var request: Request? = null

    fun updateState(): RequestState {
        updateTime()

        if (!isFree && time <= 0.0) {
            finishRequest()
            return RequestState.FINISH
        }

        return RequestState.NONE
    }

    fun acceptRequest(req: Request) {
        isFree = false
        request = req
        time = getNewTime()
    }

    private fun finishRequest() {
        request?.let { computer.queue.add(it) }
        isFree = true
        request = null
    }
}