package me.elenamakeeva.modeling.models

data class ResultInfo(
    var generateRequest: Int = 0,
    var failRequest: Int = 0,
    var processedRequest: Int = 0,
    var probabilityFail: Float = 0f
)