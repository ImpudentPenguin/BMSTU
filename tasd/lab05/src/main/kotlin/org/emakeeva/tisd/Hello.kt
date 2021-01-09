package org.emakeeva.tisd

import org.emakeeva.tisd.workers.MainWorker
import org.emakeeva.tisd.workers.ReadWorker
import java.io.File

fun main(args: Array<String>) {
    val mainWorker = MainWorker()
    val readWorker = ReadWorker()

    val originalFile = File("src/main/resources/test.txt")
    val textFile = readWorker.readText(originalFile)

    if (textFile.isNotEmpty()) {
        val binFile = File("src/main/resources/binFile")
        val dictionary = File("src/main/resources/dictionary.txt")
        val resultFile = File("src/main/resources/resultFile.txt")

        println("======================= Compression start =======================")
        val tree = mainWorker.getTree(textFile)
        mainWorker.writeText(dictionary, tree.toString())
        println("Codes in ${dictionary.path}")
        mainWorker.getCompressedString(textFile, tree.codes)?.let {
            mainWorker.writeBytes(it, binFile)
            println("Compressed text in ${binFile.path}")
        }
        println("====================== Compression complete ======================\n")

        println("======================= Start extraction =======================")
        val newTree = readWorker.readDictionary(dictionary)
        val compressedText = readWorker.readText(binFile)
        val result = mainWorker.extract(compressedText, newTree.codes)
        mainWorker.writeText(resultFile, result)
        println("Result in ${resultFile.path}")
        println("====================== Extraction complete ======================")
    } else
        println("File is empty")
}