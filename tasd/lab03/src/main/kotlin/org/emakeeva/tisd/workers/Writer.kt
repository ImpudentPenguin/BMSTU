package org.emakeeva.tisd.workers

import java.io.BufferedWriter
import java.io.FileWriter
import java.io.IOException

object Writer {
    private var bufferedWriter: BufferedWriter? = null

    fun openStream(filePath: String) {
        try {
            bufferedWriter = BufferedWriter(FileWriter(filePath))
        } catch (e: IOException) {
            e.stackTrace
        }
    }

    fun write(label: String, value: String) {
        try {
            if (label.isNotEmpty()) {
                bufferedWriter?.write("$label: $value")
                bufferedWriter?.newLine()
            } else {
                bufferedWriter?.write(value)
                bufferedWriter?.newLine()
            }
        } catch (e: IOException) {
            e.stackTrace
        }
    }

    fun writeLine(line: String? = null) {
        try {
            line?.let {
                bufferedWriter?.write(it)
                bufferedWriter?.newLine()
            } ?: bufferedWriter?.newLine()
        } catch (e: IOException) {
            e.stackTrace
        }
    }

    fun close() {
        try {
            bufferedWriter?.close()
        } catch (e: IOException) {
            e.stackTrace
        }
    }
}