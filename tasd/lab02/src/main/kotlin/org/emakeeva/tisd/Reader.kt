package org.emakeeva.tisd

import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Чтение из потока
 */
class Reader {
    companion object {
        fun read(): String {
            return BufferedReader(InputStreamReader(System.`in`)).readLine()
        }
    }
}