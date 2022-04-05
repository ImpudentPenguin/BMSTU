package me.emakeeva.security.view

import javafx.scene.text.Font
import me.emakeeva.security.MainController
import tornadofx.*

class MainView : View("Лабораторная работа №1 Шифрование") {

    private val controller: MainController by inject()

    override val root = tabpane {
        tab("Шифр Цезаря") {
            isClosable = false
            add(CipherView(
                parameter = "сдвиг",
                defaultParam = "3",
                controller::encryptCaesarCipher
            ))
        }
        tab("Шифр Виженера") {
            isClosable = false
            add(CipherView(
                parameter = "ключ",
                defaultParam = "лимон",
                controller::encryptVigenereCipher
            ))
        }

        style {
            font = Font.font("Verdana")
            fontSize = Dimension(10.0, Dimension.LinearUnits.pt)
        }
    }
}