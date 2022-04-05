package me.emakeeva.security

import me.emakeeva.security.data.CaesarResultModel
import me.emakeeva.security.data.Language
import me.emakeeva.security.data.VigenereResultModel
import tornadofx.Controller

class MainController : Controller() {

    /**
     * Шифр Цезаря
     * @param language выбранный пользователем язык
     * @param message сообщение
     * @param key сдвиг для шифра
     * @return модель с результатом шифрования и дешифрования
     */
    fun encryptCaesarCipher(language: Language, message: String, key: String): CaesarResultModel {
        // формирование матрицы шифра для представления
        val shift = key.toIntOrNull() ?: 0
        val cipher = language.alphabet.associateWith { ch ->
            val index = (language.alphabet.indexOf(ch) + shift) % language.alphabet.count()
            language.alphabet[index]
        }

        val result = StringBuilder()
        message.toCharArray().forEach { ch ->
            result.append(cipher[ch])
        }

        return CaesarResultModel(
            alphabet = cipher,
            key = shift.toString(),
            message = message,
            encryptedMessage = result.toString(),
            decryptedMessage = decryptCaesarCipher(language, result.toString(), shift)
        )
    }

    /**
     * Дешифрование с помощью шифра Цезаря
     * @param language выбранный пользователем язык
     * @param encryptedMessage зашифрованное предложение
     * @param shift сдвиг для шифра
     * @return результат дешифрования
     */
    private fun decryptCaesarCipher(language: Language, encryptedMessage: String, shift: Int): String {
        val result = StringBuilder()
        encryptedMessage.toCharArray().forEach { ch ->
            val index = (language.alphabet.indexOf(ch) - shift) % language.alphabet.count()
            val res = language.alphabet.getOrNull(if (index < 0) language.alphabet.count() + index else index) ?: ""
            result.append(res)
        }

        return result.toString()
    }

    /**
     * Шифр Виженера
     * @param language выбранный пользователем язык
     * @param message сообщение
     * @param key ключ для шифрования
     * @return модель с результатом шифрования и дешифрования
     */
    fun encryptVigenereCipher(language: Language, message: String, key: String): VigenereResultModel {
        // формирование матрицы шифра для представления
        val cipher = mutableListOf<MutableList<Char>>()
        for (i in 0 until language.alphabet.count()) {
            cipher.add(i, mutableListOf())

            for (j in 0 until language.alphabet.count()) {
                val index = if (i + j >= language.alphabet.count()) (i + j) - language.alphabet.count() else i + j
                cipher[i].add(j, language.alphabet[index])
            }
        }

        val repeatKey = key.repeat(message.length / key.length + 1)
        val result = StringBuilder()
        for (i in message.indices) {
            val index = (language.alphabet.indexOf(message[i]) + language.alphabet.indexOf(repeatKey[i])) %
                    language.alphabet.count()
            result.append(language.alphabet[index])
        }

        return VigenereResultModel(
            alphabet = cipher,
            key = repeatKey,
            message = message,
            encryptedMessage = result.toString(),
            decryptedMessage = decryptVigenereCipher(language, result.toString(), key)
        )
    }

    /**
     * Дешифрование с помощью шифра Виженера
     * @param language выбранный пользователем язык
     * @param encryptedMessage зашифрованное предложение
     * @param key ключ для шифрования
     * @return результат дешифрования
     */
    private fun decryptVigenereCipher(language: Language, encryptedMessage: String, key: String): String {
        val repeatKey = key.repeat(encryptedMessage.length / key.length + 1)
        val result = StringBuilder()
        for (i in encryptedMessage.indices) {
            val index = (language.alphabet.indexOf(encryptedMessage[i]) - language.alphabet.indexOf(repeatKey[i])) %
                    language.alphabet.count()
            result.append(
                language.alphabet.getOrNull(if (index < 0) language.alphabet.count() + index else index) ?: ""
            )
        }

        return result.toString()
    }
}