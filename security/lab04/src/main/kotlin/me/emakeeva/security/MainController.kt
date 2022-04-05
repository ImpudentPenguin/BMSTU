package me.emakeeva.security

import me.emakeeva.security.models.NodeModel
import me.emakeeva.security.models.ResultModel
import me.emakeeva.security.models.TreeModel
import tornadofx.Controller

class MainController : Controller() {

    fun encrypt(message: String): ResultModel {
        val tree = generateTree(message)
        val directory = generateDirectory(tree)
        val encryptedMessage = message.map { directory[it].toString() }

        return ResultModel(directory, encryptedMessage)
    }

    fun decrypt(encryptedMessage: String, directory: Map<Char, String>): String {
        val decryptedMessage = StringBuilder()
        var message = encryptedMessage

        while (message.isNotEmpty()) {
            directory.forEach { (ch, code) ->
                if (message.startsWith(code)) {
                    decryptedMessage.append(ch)
                    message = message.removeRange(0, code.length)
                    return@forEach
                }
            }
        }

        return decryptedMessage.toString()
    }

    private fun generateDirectory(treeModel: TreeModel): Map<Char, String> {
        val directory = mutableMapOf<Char, String>()
        treeModel.freq.forEach {
            val res = getCode(it.key, "", treeModel.head)
            if (res != null)
                directory[res.first] = res.second
        }

        return directory
    }

    private fun getCode(letter: Char? = null, body: String? = null, nodeModel: NodeModel? = null): Pair<Char, String>? {
        if (nodeModel == null)
            return null

        if (nodeModel.letter == letter && letter != null)
            return letter to body.toString()

        val left = getCode(letter, "${body}0", nodeModel.leftChild)
        if (left != null)
            return left

        val right = getCode(letter, "${body}1", nodeModel.rightChild)
        if (right != null)
            return right

        return null
    }

    private fun generateTree(message: String): TreeModel {
        val frequency = mutableMapOf<Char, Int>()
        message.forEach { ch ->
            frequency[ch] = frequency[ch]?.plus(1) ?: 1
        }

        val nodes: MutableList<NodeModel> = frequency.map {
            NodeModel(
                letter = it.key,
                frequency = it.value
            )
        }.sortedBy { it.frequency }.toMutableList()

        while (nodes.size != 1) {
            val second = nodes.removeAt(1)
            val first = nodes.removeAt(0)
            val node = NodeModel(
                frequency = first.frequency + second.frequency,
                leftChild = first,
                rightChild = second
            )

            nodes.add(0, node)
            nodes.sortBy { it.frequency }
        }

        return TreeModel(frequency, nodes.first())
    }
}