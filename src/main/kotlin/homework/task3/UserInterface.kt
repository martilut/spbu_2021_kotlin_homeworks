package homework.task3

import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import library.scanNumber
import java.io.File

const val FILEPATH = "src/main/kotlin/homework/task3/"
const val FILENAME = "inputFile.json"

interface Option {
    fun performOption(performedCommandStorage: PerformedCommandStorage)
}

class InsertToStartOption : Option {
    override fun performOption(performedCommandStorage: PerformedCommandStorage) {
        val userValue = scanNumber("Enter your value: ")
        performedCommandStorage.makeAction(InsertToStart(userValue))
    }
}

class InsertToEndOption : Option {
    override fun performOption(performedCommandStorage: PerformedCommandStorage) {
        val userValue = scanNumber("Enter your value: ")
        performedCommandStorage.makeAction(InsertToEnd(userValue))
    }
}

class MoveElementOption : Option {
    override fun performOption(performedCommandStorage: PerformedCommandStorage) {
        val startPosition = scanNumber("Enter the start position: ")
        val endPosition = scanNumber("Enter the final position: ")
        try {
            performedCommandStorage.makeAction(MoveElement(startPosition, endPosition))
        } catch (e: IllegalArgumentException) {
            println("${e.message}")
        }
    }
}

class OutputListOption : Option {
    override fun performOption(performedCommandStorage: PerformedCommandStorage) {
        val elements: MutableList<Int> = performedCommandStorage.elements
        if (elements.size != 0) {
            for (element in elements) print("$element ")
            print("\n")
        } else {
            println("List is empty")
        }
    }
}

class CancelLastAction : Option {
    override fun performOption(performedCommandStorage: PerformedCommandStorage) {
        if (performedCommandStorage.performedActions.size != 0) {
            performedCommandStorage.cancelLastAction()
        } else {
            println("No actions had been performed yet")
        }
    }
}

fun makeAllActions(actionList: MutableList<Action>, elements: MutableList<Int>) {
    for (action in actionList) {
        action.makeAction(elements)
    }
}

class JsonOperations(private val jsonFile: File) : Option {
    private val module = SerializersModule {
        polymorphic(Action::class) {
            subclass(InsertToStart::class)
            subclass(InsertToEnd::class)
            subclass(MoveElement::class)
        }
    }
    private val format = Json { serializersModule = module }

    private fun loadFromJson(performedCommandStorage: PerformedCommandStorage) {
        val jsonText = jsonFile.readText()
        val actionList: MutableList<Action> = format.decodeFromString(jsonText)
        makeAllActions(actionList, performedCommandStorage.elements)
    }

    private fun saveToJson(performedCommandStorage: PerformedCommandStorage) {
        val jsonText = format.encodeToString(performedCommandStorage.performedActions)
        jsonFile.writeText(jsonText)
    }

    override fun performOption(performedCommandStorage: PerformedCommandStorage) {
        when (scanNumber("Type 1 to load from Json\nType 2 to save to Json\nEnter here: ")) {
            1 -> {
                loadFromJson(performedCommandStorage)
            }
            2 -> {
                saveToJson(performedCommandStorage)
            }
            else -> {
                print("Your input is incorrect\n")
            }
        }
    }
}

fun getListOfOptions(jsonFile: File): MutableList<Option> {
    val optionList = mutableListOf<Option>()
    optionList.add(InsertToStartOption())
    optionList.add(InsertToEndOption())
    optionList.add(MoveElementOption())
    optionList.add(OutputListOption())
    optionList.add(CancelLastAction())
    optionList.add(JsonOperations(jsonFile))
    return optionList
}

fun showUserInterface() {
    val jsonFile = File(FILEPATH + FILENAME)
    if (!jsonFile.createNewFile()) jsonFile.writeText("[]")

    val scan = java.util.Scanner(System.`in`)
    val performedCommandStorage = PerformedCommandStorage()
    val optionList: MutableList<Option> = getListOfOptions(jsonFile)
    println("Have a look at the commands I can perform...")
    println("Press 0 exit\nPress 1 to insert the element to the start\n" +
            "Press 2 to insert the element to the end\nPress 3 to move the element (first position is 0)\n" +
            "Press 4 to see the list of elements\nPress 5 to cancel last action\n" +
            "Press 6 to operate with JSON file\n")
    var chosenOption = -1
    while (chosenOption != 0) {
        print("Type option here: ")
        chosenOption = scan.nextInt()
        if (chosenOption == 0) {
            break
        }
        if (chosenOption in 1..optionList.size) {
            optionList[chosenOption - 1].performOption(performedCommandStorage)
        } else {
            println("Your option number is incorrect")
        }
    }
    println("You exit the app")
}
