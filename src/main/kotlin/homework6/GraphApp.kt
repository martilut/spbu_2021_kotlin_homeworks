package homework6

import javafx.scene.chart.NumberAxis
import javafx.scene.control.Alert
import tornadofx.View
import tornadofx.hbox
import tornadofx.form
import tornadofx.App
import tornadofx.field
import tornadofx.fieldset
import tornadofx.textfield
import tornadofx.button
import tornadofx.action
import tornadofx.alert
import tornadofx.linechart
import tornadofx.series
import tornadofx.data
import tornadofx.label

class Graph : View() {

    companion object {
        const val maxThreadValue = 1000
        const val maxElementValue = 1000000
        const val minValue = 1
    }

    data class InputData(val threadValue: Int, val countValue: Int, val stepValue: Int)

    private fun checkUserInput(data: String, dataName: String, maxValue: Int): Int {
        return when (val count = data.toIntOrNull()) {
            null -> throw IllegalArgumentException("Incorrect input")
            else -> {
                when {
                    count > maxValue || count < minValue ->
                        throw IllegalArgumentException(
                            "Minimal $dataName count is $minValue\nMaximal $dataName count is $maxValue"
                        )
                    else -> count
                }
            }
        }
    }

    private fun getInputData(threadValue: String, countValue: String, stepValue: String): InputData {
        val threadCount: Int = checkUserInput(threadValue, "thread", maxThreadValue)
        val elementCount: Int = checkUserInput(countValue, "element", maxElementValue)
        val stepCount: Int = checkUserInput(stepValue, "step", elementCount)
        return InputData(threadCount, elementCount, stepCount)
    }

    override val root = hbox {
        form {
            lateinit var changeAction: (InputData) -> Unit

            fieldset {
                field {
                    label("Thread\ncount")
                    val threadValue = textfield().characters
                    label("Element\ncount")
                    val countValue = textfield().characters
                    label("Step")
                    val stepValue = textfield().characters

                    val addButton = button("ENTER")
                    addButton.action {
                        try {
                            val inputData = getInputData(
                                threadValue.toString(),
                                countValue.toString(),
                                stepValue.toString()
                            )
                            changeAction(inputData)
                        } catch (e: IllegalArgumentException) {
                            alert(Alert.AlertType.ERROR, "${e.message}")
                        }
                    }
                }
            }

            linechart("Merge Sort", NumberAxis(), NumberAxis()) {
                changeAction = {
                    series("Threads: ${it.threadValue}\n" +
                            "Elements: ${it.countValue}\n" +
                            "Step: ${it.stepValue}") {
                        SortingStatistics().getStatistics(it.threadValue, it.countValue, it.stepValue)
                            .forEach { item -> data(item.count, item.time) }
                    }
                }
            }
        }
    }
}

class GraphApp : App(Graph::class)
