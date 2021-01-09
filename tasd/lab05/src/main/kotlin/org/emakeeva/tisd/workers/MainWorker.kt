package org.emakeeva.tisd.workers

import org.emakeeva.tisd.models.Node
import org.emakeeva.tisd.models.Tree
import java.io.File
import java.nio.charset.Charset
import java.util.*

/**
 * Основной контроллер по работе с сжатием по Хаффману
 */
class MainWorker {

    private val writeWorker = WriteWorker()
    private val tree = mutableListOf<Node>()

    /**
     * Получение дерева
     */
    fun getTree(str: String): Tree {
        val frequency: MutableMap<Char, Int> = HashMap()

        for (i in str.indices) {
            if (!frequency.containsKey(str[i])) {
                frequency[str[i]] = 0
            }
            frequency[str[i]] = (frequency[str[i]] ?: 0) + 1
        }

        frequency.forEach { (key, value) ->
            Node().apply {
                letter = key
                leftChild = null
                rightChild = null
                this.frequency = value
                tree.add(this)
            }
        }

        if (tree.isEmpty()) {
            throw NullPointerException()
        }

        var count = tree.size

        while (count > 1) {
            buildTree()

            count = 0
            tree.forEach { node ->
                if (node.flag)
                    count++
            }
        }

        return Tree(getEncodingArray())
    }

    /**
     * Получение справочника
     */
    private fun getEncodingArray(): MutableMap<Char, String> {
        val codes: MutableMap<Char, String> = HashMap()

        for (num in 0 until tree.size) {
            val builder = StringBuilder()
            tree[num].letter?.let { letter ->
                var parent = tree.firstOrNull {
                    when {
                        it.leftChild?.letter == letter -> {
                            builder.insert(0, '0')
                            true
                        }
                        it.rightChild?.letter == letter -> {
                            builder.insert(0, '1')
                            true
                        }
                        else -> false
                    }
                }

                while (parent != null) {
                    parent = tree.firstOrNull {
                        when {
                            it.leftChild == parent -> {
                                builder.insert(0, '0')
                                true
                            }
                            it.rightChild == parent -> {
                                builder.insert(0, '1')
                                true
                            }
                            else -> false
                        }
                    }
                }

                codes[letter] = builder.toString()
            }
        }
        return codes
    }

    /**
     * Построение дерева
     */
    private fun buildTree() {
        var imax = -1
        var jmax = -1

        for (i in 0 until tree.size) {
            if (imax != -1)
                break

            for (j in i + 1 until tree.size) {
                if (imax != -1)
                    break

                if (tree[i].flag && tree[j].flag) {
                    imax = i
                    jmax = j
                }
            }
        }
        if (imax != -1) {
            for (i in imax until tree.size)
                for (j in i + 1 until tree.size)
                    if (tree[i].flag && tree[j].flag && (tree[i].frequency + tree[j].frequency < tree[imax].frequency + tree[jmax].frequency)) {
                        imax = i
                        jmax = j
                    }

            tree.add(
                    Node().apply {
                        letter = null
                        leftChild = tree[imax]
                        rightChild = tree[jmax]
                        flag = true
                        this.frequency = tree[imax].frequency + tree[jmax].frequency
                        tree[imax].flag = false
                        tree[jmax].flag = false
                    }
            )
        }
    }

    /**
     * Запись байтов в файл
     */
    fun writeBytes(compressedString: String, file: File) {
        getByteArray(compressedString)?.let { bytes ->
            writeWorker.writeByres(file, bytes)
        } ?: throw Exception("Ошибка инициализации массива байтов")
    }

    /**
     * Запись справочника в файл
     */
    fun writeText(file: File, str: String) {
        writeWorker.writeText(file, str)
    }

    /**
     * Получение сжатой строки
     */
    fun getCompressedString(str: String, encodingArray: MutableMap<Char, String>): String? {
        var temp = ""

        for (element in str) {
            temp += encodingArray[element]
        }

        var counter = 0
        val length = temp.length
        val delta = 8 - length % 8
        while (counter < delta) {
            temp += "0"
            counter++
        }

        return String.format("%8s", Integer.toBinaryString(counter and 0xff)).replace(" ", "0") + temp
    }

    /**
     * Получение массива байтов сжатой строки
     */
    private fun getByteArray(compressedString: String): ByteArray? {
        return compressedString.byteInputStream(Charset.forName("UTF-8")).readBytes()
    }

    /**
     * Извлечение сжатой строки
     */
    fun extract(compressedString: String, codes: MutableMap<Char, String>): String {
        var decompressed = ""
        var current = ""
        var delta = ""

        for (i in 0..7) delta += compressedString[i]
        val addedZeros: Int = Integer.parseInt(delta, 2)

        var i = 8
        val l: Int = compressedString.length - addedZeros
        while (i < l) {
            current += compressedString[i]
            codes.forEach { (key, value) ->
                if (current == value) {
                    decompressed += key
                    current = ""
                }
            }
            i++
        }

        return decompressed
    }
}