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

    override val root = hbox {
        form {
            val inputThreadCount = object {
                var threadCount = 1
                val actionList = mutableListOf<Runnable>()
            }
            fieldset {
                field("Enter thread count:") {
                    val text = textfield().characters
                    val addButton = button("ENTER")
                    addButton.action {
                        when (val count = text.toString().toIntOrNull()) {
                            null -> alert(Alert.AlertType.ERROR, "Incorrect number")
                            else -> {
                                when {
                                    count > 1000 -> alert(Alert.AlertType.WARNING, "Number must be less than 1000")
                                    count < 1 -> alert(Alert.AlertType.WARNING, "Number must be positive")
                                    else -> {
                                        inputThreadCount.threadCount = count
                                        inputThreadCount.actionList[0].run()
                                    }
                                }
                            }
                        }
                    }
                }
            }
            linechart("Merge Sort", NumberAxis(), NumberAxis()) {
                fun addSeries(number: Int) {
                    series("$number threads") {
                        getStatistics(number).forEach { item -> data(item.first, item.second) }
                    }
                }
                inputThreadCount.actionList.add { addSeries(inputThreadCount.threadCount) }
            }
            label("OX - elements in thousands\nOY - time in seconds") {
            }
        }
    }
}

class GraphApp : App(Graph::class)
