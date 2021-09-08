package me.elenamakeeva.modeling.models

data class Computer(
    val id: Int,
    val min: Double,
    val max: Double,
    val systemTime: Double,
    var isFree: Boolean = false
): BaseGenerator(min, max, systemTime) {
    val queue: MutableList<Request> = mutableListOf()

    fun updateState(): RequestState {
        updateTime()

        if (!isFree && time <= 0.0) {
            isFree = true
            return RequestState.FINISH
        }

        if (isFree && queue.count() != 0) {
            queue.removeFirst()
            time = getNewTime()
            isFree = false
            return RequestState.ACCEPT
        }

        return RequestState.NONE
    }
}
