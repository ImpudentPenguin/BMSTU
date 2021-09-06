package me.elenamakeeva.portMobiApps.models

data class RegistrUserModel(
    var login: String,
    var email: String,
    val password: String,
    val repeatedPassword: String
)
