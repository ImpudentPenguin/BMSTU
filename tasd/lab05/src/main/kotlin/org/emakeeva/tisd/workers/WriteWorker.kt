package org.emakeeva.tisd.workers

import java.io.*

/**
 * Контроллер по работе записи в файл
 */
class WriteWorker {

    @Throws(FileNotFoundException::class, IOException::class)
    fun writeByres(file: File, bytes: ByteArray) {
        val fileOutputStream = FileOutputStream(file)
        fileOutputStream.write(bytes)
        fileOutputStream.close()
    }

    @Throws(FileNotFoundException::class, IOException::class)
    fun writeText(file: File, str: String) {
        val fileWriter = FileWriter(file)
        fileWriter.write(str)
        fileWriter.close()
    }
}