package org.emakeeva.tisd.workers

import org.emakeeva.tisd.models.Tree
import java.io.*
import java.lang.Exception

/**
 * Контроллер по работе чтения файлов
 */
class ReadWorker {

    @Throws(FileNotFoundException::class, IOException::class)
    fun readText(file: File): String {
        val builder = StringBuilder()
        val fileReader = BufferedReader(InputStreamReader(FileInputStream(file)))
        var str = fileReader.readLine()

        while (str != null) {
            builder.append(str)
            str = fileReader.readLine()
            if (str != null)
                builder.appendln()
        }

        fileReader.close()
        return builder.toString()
    }

    @Throws(FileNotFoundException::class, IOException::class)
    fun readDictionary(file: File): Tree {
        val codes: MutableMap<Char, String> = HashMap()
        val fileReader = BufferedReader(InputStreamReader(FileInputStream(file)))
        var str = fileReader.readLine()

        while (str != null) {
            val pattern = Regex("(.+):(.+)")
            val secondPattern = Regex(":(.+)")
            when {
                pattern.matches(str) -> {
                    pattern.matchEntire(str)?.groupValues?.let {
                        val ch = it[1].toCharArray().first()
                        codes[ch] = it[2]
                    }
                }
                secondPattern.matches(str) -> {
                    secondPattern.matchEntire(str)?.groupValues?.let {
                        val ch = '\n'
                        codes[ch] = it[1]
                    }
                }
                else -> {
                    if (str.isNotEmpty())
                        throw Exception("Error parsing")
                }
            }

            str = fileReader.readLine()
        }

        fileReader.close()
        return Tree(codes)
    }
}