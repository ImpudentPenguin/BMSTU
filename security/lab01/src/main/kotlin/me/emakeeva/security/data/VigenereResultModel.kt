package me.emakeeva.security.data

data class VigenereResultModel(
    val alphabet: MutableList<MutableList<Char>>,
    val key: String,
    val message: String,
    val encryptedMessage: String,
    val decryptedMessage: String,
) {
    override fun toString(): String {
        return "Ключ: $key\n" +
                "Сообщение: $message\n" +
                "Зашифрованное сообщение: $encryptedMessage\n" +
                "Дешифрованное сообщение: $decryptedMessage\n" +
                "Шифр:\n${alphabet.joinToString("\n")}"
    }
}