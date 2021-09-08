package org.emakeeva.lab01

import org.emakeeva.lab01.AlgorithmWorker.nextDigit
import java.awt.*
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import javax.swing.*
import javax.swing.border.EtchedBorder
import javax.swing.table.DefaultTableCellRenderer
import javax.swing.table.DefaultTableModel
import org.emakeeva.lab01.AlgorithmWorker.rateRandom
import kotlin.random.Random

class GUIWorker : JFrame() {

    private lateinit var table: JTable
    private val result = mutableListOf("Коэффициент")
    private val firstTableArr = mutableListOf<Int>()
    private val secondTableArr = mutableListOf<Int>()
    private val thirdTableArr = mutableListOf<Int>()
    private val firstAlgArr = mutableListOf<Int>()
    private val secondAlgArr = mutableListOf<Int>()
    private val thirdAlgArr = mutableListOf<Int>()

    init {
        createUI()

        initArrays()
        initRows(0, mutableListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9))
        initRows(1, firstTableArr)
        initRows(2, secondTableArr)
        initRows(3, thirdTableArr)

        checkRate(findResult(firstTableArr))
        checkRate(findResult(secondTableArr))
        checkRate(findResult(thirdTableArr))

        firstAlgArr.generateAlgArr(Random.nextInt(0, 10))
        secondAlgArr.generateAlgArr(Random.nextInt(10, 100))
        thirdAlgArr.generateAlgArr(Random.nextInt(100, 1000))

        initRows(4, firstAlgArr)
        initRows(5, secondAlgArr)
        initRows(6, thirdAlgArr)

        checkRate(findResult(firstAlgArr))
        checkRate(findResult(secondAlgArr))
        checkRate(findResult(thirdAlgArr))

        initColumn(10, result)
    }

    /**
     * Создание UI
     */
    private fun createUI() {
        title = "Исследование последовательности псевдослучайных чисел"
        layout = BoxLayout(contentPane, BoxLayout.Y_AXIS)

        createTable()
        createField()

        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(800, 400)
        setLocationRelativeTo(null)
    }

    /**
     * Добавление содержимого окна
     */
    private fun createField() {
        val panel = JPanel()
        val info = JTextPane()
        val label = JLabel("Введите свою последовательность через пробел, запятую или точку запятой:")
        val textField = JTextField(45)
        val send = JButton("Подтвердить")

        send.addActionListener {
            if (!textField.text.isNullOrBlank())
                JOptionPane.showMessageDialog(
                        null,
                        checkSequence(textField.text),
                        "Результат",
                        JOptionPane.PLAIN_MESSAGE
                )
            else JOptionPane.showMessageDialog(
                    null,
                    "Вы ничего не ввели.",
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE
            )
        }

        info.contentType = "text/html"
        info.text = "<p><strong>Критерий случайности:</strong></p>" +
                "<p>если вычисленное в ходе теста значение вероятности 0 &lt; p &lt; 0.1, " +
                "то данная последовательность является истинно случайной. В противном случае она не носит случайный характер.</p>"
        info.preferredSize = Dimension(790, 80)
        info.background = null

        panel.add(info, BorderLayout.CENTER)
        panel.add(label)
        panel.add(textField)
        panel.add(send)

        add(panel)
    }

    /**
     * Создание UI таблицы
     */
    private fun createTable() {
        val model = DefaultTableModel(
                arrayOf(
                        "№",
                        "Табл. 1р",
                        "Табл. 2р",
                        "Табл. 3р",
                        "Алгоритм. 1р",
                        "Алгоритм. 2р",
                        "Алгоритм. 3р"
                ), 11
        )

        table = JTable(model)
        (table.tableHeader.defaultRenderer as DefaultTableCellRenderer).horizontalAlignment = SwingConstants.CENTER
        val centerRender = DefaultTableCellRenderer()
        centerRender.horizontalAlignment = JLabel.CENTER

        for (i in 0 until 7)
            table.columnModel.getColumn(i).cellRenderer = centerRender

        table.border = EtchedBorder(EtchedBorder.RAISED)
        table.gridColor = Color.LIGHT_GRAY
        table.rowSelectionAllowed = false
        table.columnSelectionAllowed = false
        table.tableHeader.reorderingAllowed = false

        add(table.tableHeader)
        add(table)
    }

    /**
     * Расширение для генерации последовательности
     */
    private fun MutableList<Int>.generateAlgArr(start: Int) {
        add(start)

        when (start) {
            in 0..9 -> for (i in 1 until 1000)
                add(nextDigit(get(i - 1), 0, 10))
            in 10..99 -> for (i in 1 until 1000)
                add(nextDigit(get(i - 1), 10, 90))
            in 100..999 -> for (i in 1 until 1000)
                add(nextDigit(get(i - 1), 100, 900))
        }
    }

    /**
     * Инициализация массивов из таблицы
     */
    private fun initArrays() {
        val fileReader = BufferedReader(InputStreamReader(FileInputStream(File("src/main/resources/digits.txt"))))

        while (firstTableArr.size < 1000 || secondTableArr.size < 1000 || thirdTableArr.size < 1000) {
            val line = fileReader.readLine()

            if (line.isNullOrEmpty())
                break

            val nums = (line.split(" ") as MutableList).mapNotNull {
                if (it.isNotBlank()) {
                    it.toInt()
                } else null
            }

            for (i in nums.indices) {
                val num = nums[i]

                when {
                    num in 0..9 && firstTableArr.size < 1000 -> {
                        firstTableArr.add(num)
                    }
                    num in 10..99 && secondTableArr.size < 1000 -> {
                        secondTableArr.add(num)
                    }
                    num in 100..999 && thirdTableArr.size < 1000 -> {
                        thirdTableArr.add(num)
                    }
                }
            }
        }

        fileReader.close()
    }

    /**
     * Подсчет коэффициента
     */
    private fun findResult(values: List<Int>): Double {
        var count = 0
        values.sortedWith(Comparator { first, second ->
            if (second > first) {
                count++
                1
            } else 0
        })

        if (count in 0..1)
            return Double.POSITIVE_INFINITY

        val valuesMap = mutableMapOf<Int, Int>()

        values.forEach {
            if (valuesMap.contains(it))
                valuesMap[it] = valuesMap[it]?.plus(1) ?: 1
            else
                valuesMap[it] = 1
        }

        var res = 0.0
        valuesMap.forEach { (key, value) ->
            res += rateRandom(key, value, valuesMap.size)
        }

        return res
    }

    /**
     * Заполнение по строкам
     */
    private fun <T> initRows(column: Int, values: List<T>) {
        if (values.isEmpty())
            return

        val model = table.model as DefaultTableModel

        for (i in 0 until 10)
            model.setValueAt(values[i], i, column)
    }

    /**
     * Заполнение по столбцам
     */
    private fun <T> initColumn(row: Int, values: List<T>) {
        if (values.isEmpty())
            return

        val model = table.model as DefaultTableModel

        for (i in values.indices)
            model.setValueAt(values[i], row, i)
    }

    /**
     * Проверка полученного коэффициента
     */
    private fun checkRate(res: Double) {
        if (res == Double.POSITIVE_INFINITY)
            result.add("Последовательность не случайна")
        else result.add("%.2f".format(res))
    }

    /**
     * Проверка введенной последовательности
     */
    private fun checkSequence(value: String): String {
        val values = value.split(Regex("\\s|,|;")).mapNotNull {
            it.toIntOrNull()
        }

        val rate = findResult(values)
        return if (rate == Double.POSITIVE_INFINITY)
            "Ваша последовательность не случайна"
        else String.format("Результат вычисления вашей последовательности по критерию случайности: %.2f", findResult(values))
    }
}