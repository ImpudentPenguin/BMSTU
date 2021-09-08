package me.elenamakeeva.modeling.views

import javafx.geometry.Insets
import javafx.scene.control.TextField
import me.elenamakeeva.modeling.MainController
import tornadofx.*

class NormView : View() {
    private val controller: MainController by inject()
    private var paramMu: TextField by singleAssign()
    private var paramSigma: TextField by singleAssign()
    private var graph: GraphView? = null

    override val root = vbox {
        stackpaneConstraints {
            margin = Insets(24.0)
        }

        label("Нормальное распределение - распределение вероятностей, которое в одномерном случае задаётся функцией плотности вероятности, совпадающей с функцией Гаусса.\n\n")
        label("Параметры:")
        form {
            fieldset().hbox(20) {
                field("Введите 'μ'") { paramMu = textfield("0") }
                field("Введите 'σ'") { paramSigma = textfield("1") }
            }

            borderpane {
                center = button("Подтвердить") {
                    action {
                        if (paramSigma.text.replace(",", ".").toDoubleOrNull() ?: 0.0 > 0) {
                            val updatedData = controller.getNormValues(
                                paramMu.text.replace(",", ".").toDoubleOrNull() ?: 0.0,
                                paramSigma.text.replace(",", ".").toDoubleOrNull() ?: 0.0
                            )
                            graph?.update(updatedData)
                        } else
                            AlertView().openWindow()
                    }
                }
            }

            val data = controller.getNormValues(
                paramMu.text.replace(",", ".").toDoubleOrNull() ?: 0.0,
                paramSigma.text.replace(",", ".").toDoubleOrNull() ?: 0.0
            )

            graph = GraphView(data)
            graph?.let {
                add(it)
            }
        }
    }
}