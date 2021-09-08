package me.elenamakeeva.modeling.models

data class RequestGenerator(
    val min: Double,
    val max: Double,
    val systemTime: Double
): BaseGenerator(min, max, systemTime) {

    fun getRequest(id: Int): Request? {
        updateTime()

        if (time <= 0.0) {
            time = getNewTime()
            return Request(id)
        }

        return null
    }
}