package me.emakeeva.security.data

data class CaesarResultModel(
    val alphabet: Map<Char, Char>,
    val key: String,
    val message: String,
    val encryptedMessage: String,
    val decryptedMessage: String,
) {
    override fun toString(): String {
        return "Сдвиг: $key\n" +
                "Сообщение: $message\n" +
                "Зашифрованное сообщение: $encryptedMessage\n" +
                "Дешифрованное сообщение: $decryptedMessage\n" +
                "Шифр:\n${alphabet.keys}\n${alphabet.values}"
    }
}

