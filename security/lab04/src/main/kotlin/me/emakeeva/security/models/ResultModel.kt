package me.emakeeva.security.models

data class ResultModel(
    val directory: Map<Char, String>,
    val encryptedMessage: List<String>
)