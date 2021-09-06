package me.elenamakeeva.portMobiApps.networkmodels

data class ErrorModel(
    override var result: Boolean,
    val message: String
): IResponse