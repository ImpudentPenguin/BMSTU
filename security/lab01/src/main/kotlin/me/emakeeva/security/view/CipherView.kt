package me.emakeeva.security.view

import javafx.geometry.Insets
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.VBox
import javafx.scene.text.FontWeight
import me.emakeeva.security.data.Language
import tornadofx.*

class CipherView<T>(
    private val parameter: String,
    private val defaultParam: String,
    private val method: (Language, String, String) -> T
) : View() {

    private var message: TextArea by singleAssign()
    private var key: TextField by singleAssign()
    private val outputView = OutputView<T>()
    private val toggleGroup = ToggleGroup()
    private var language = Language.Russian

    override val root = vbox {
        stackpaneConstraints {
            margin = Insets(24.0)
        }

        label("Параметры:") {
            style {
                paddingLeft = 10
                fontWeight = FontWeight.BOLD
            }
        }

        languagesButtons()

        form {
            fieldset().vbox {
                field("Введите $parameter:") { key = textfield(defaultParam) }
                field("Введите сообщение:") { message = textarea() }
            }

            borderpane {
                style {
                    paddingBottom = 10
                }

                center = button("Подтвердить") {
                    action {
                        outputView.data.clear()

                        if (!message.text.isNullOrEmpty() && !key.text.isNullOrEmpty()) {
                            val result = method.invoke(language, message.text, key.text)
                            outputView.data.add(result)
                        } else
                            AlertView().openWindow()
                    }
                }
            }

            add(outputView)
        }
    }

    private fun VBox.languagesButtons() {
        label("Язык:") {
            paddingLeft = 10
            paddingBottom = 10
        }

        hbox {
            style {
                paddingLeft = 10
            }

            radiobutton("Русский", toggleGroup) {
                isSelected = true
                action {
                    language = Language.Russian
                }

                style {
                    paddingRight = 5
                }
            }

            radiobutton("Английский", toggleGroup) {
                action {
                    language = Language.English
                }
            }
        }
    }
}