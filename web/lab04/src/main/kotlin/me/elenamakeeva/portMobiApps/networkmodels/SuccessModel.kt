package me.elenamakeeva.portMobiApps.networkmodels

data class SuccessModel<T>(
    override var result: Boolean,
    val data: T
): IResponse