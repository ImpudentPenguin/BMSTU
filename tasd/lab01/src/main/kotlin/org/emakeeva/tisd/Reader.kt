package org.emakeeva.tisd

import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Чтение из потока
 */
class Reader {
   companion object {
       fun read(): String {
           val bufferedReader = BufferedReader(InputStreamReader(System.`in`))
           return bufferedReader.readLine()
       }
   }
}