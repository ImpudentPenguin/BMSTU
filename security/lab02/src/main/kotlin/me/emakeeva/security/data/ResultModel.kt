package me.emakeeva.security.data

import java.math.BigInteger

data class ResultModel(
    val encryptedArray: List<BigInteger>,
    val decryptedMessage: String,
) {
    override fun toString(): String {
        return "Зашифрованное сообщение:\n" +
                "${encryptedArray.joinToString("\n") { "\t$it" }}\n" +
                "Дешифрованное сообщение: $decryptedMessage"
    }
}