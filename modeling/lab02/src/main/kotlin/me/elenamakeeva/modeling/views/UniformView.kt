package me.elenamakeeva.modeling.views

import javafx.geometry.Insets
import javafx.scene.control.TextField
import me.elenamakeeva.modeling.MainController
import tornadofx.*

class UniformView : View() {
    private val controller: MainController by inject()
    private var paramA: TextField by singleAssign()
    private var paramB: TextField by singleAssign()
    private var graph: GraphView? = null

    override val root = vbox {
        stackpaneConstraints {
            margin = Insets(24.0)
        }

        label("Равномерное распределение - распределение случайной величины, принимающей значения, принадлежащие некоторому " +
                "промежутку конечной длины, характеризующееся тем,\nчто плотность вероятности на этом промежутке всюда постоянна.\n\n")
        label("Параметры:")
        form {
            fieldset().hbox(20) {
                field("Введите 'a'") { paramA = textfield("0") }
                field("Введите 'b'") { paramB = textfield("5") }
            }

            borderpane {
                center = button("Подтвердить") {
                    action {
                        val updateData = controller.getUniformValues(
                            paramA.text.replace(",", ".").toFloatOrNull() ?: 0f,
                            paramB.text.replace(",", ".").toFloatOrNull() ?: 0f
                        )

                        graph?.update(updateData)
                    }
                }
            }

            val data = controller.getUniformValues(
                paramA.text.replace(",", ".").toFloatOrNull() ?: 0f,
                paramB.text.replace(",", ".").toFloatOrNull() ?: 0f
            )

            graph = GraphView(data)
            graph?.let {
                add(it)
            }
        }
    }
}