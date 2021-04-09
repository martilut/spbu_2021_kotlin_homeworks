package homework.task3

import homework.task3.actions.InsertToStart
import homework.task3.actions.InsertToEnd
import homework.task3.actions.MoveElement
import homework.task3.storage.PerformedCommandStorage
import homework.task3.storage.PerformedCommandStorage.IntSerialize.loadFromJson
import homework.task3.storage.PerformedCommandStorage.IntSerialize.saveToJson
import util.scanNumber
import java.io.File
import java.util.Scanner

/**
 * Basic interface for options
 */
interface Option<T> {
    fun performOption(performedCommandStorage: PerformedCommandStorage<T>)
}

/**
 * Option: select action InsertToStart
 */
class InsertToStartOption<T>(private val userValue: T) : Option<T> {
    override fun performOption(performedCommandStorage: PerformedCommandStorage<T>) {
        performedCommandStorage.makeAction(InsertToStart(userValue))
    }
}

/**
 * Option: select action InsertToEnd
 */
class InsertToEndOption<T>(private val userValue: T) : Option<T> {
    override fun performOption(performedCommandStorage: PerformedCommandStorage<T>) {
        performedCommandStorage.makeAction(InsertToEnd(userValue))
    }
}

/**
 * Option: select action MoveElement
 */
class MoveElementOption<T> : Option<T> {
    override fun performOption(performedCommandStorage: PerformedCommandStorage<T>) {
        val startPosition = scanNumber("Enter the start position: ")
        val finalPosition = scanNumber("Enter the final position: ")
        try {
            performedCommandStorage.makeAction(MoveElement(startPosition, finalPosition))
        } catch (e: IllegalArgumentException) {
            println("${e.message}")
        }
    }
}

/**
 * Option: output list of elements
 */
class OutputListOption<T> : Option<T> {
    override fun performOption(performedCommandStorage: PerformedCommandStorage<T>) {
        val elements: MutableList<T> = performedCommandStorage.elements
        if (elements.size != 0) {
            for (element in elements) print("$element ")
            print("\n")
        } else {
            println("List is empty")
        }
    }
}

/**
 * Option: cancel last performed action
 */
class CancelLastAction<T> : Option<T> {
    override fun performOption(performedCommandStorage: PerformedCommandStorage<T>) {
        if (performedCommandStorage.performedActions.size != 0) {
            performedCommandStorage.cancelLastAction()
        } else {
            println("No actions had been performed yet")
        }
    }
}

/**
 * Option: save from json or load to json
 */
class JsonOperations(private val jsonFile: File) : Option<Int> {
    override fun performOption(performedCommandStorage: PerformedCommandStorage<Int>) {
        when (scanNumber("Type 1 to load from Json\nType 2 to save to Json\nEnter here: ")) {
            1 -> {
                performedCommandStorage.loadFromJson(jsonFile)
            }
            2 -> {
                performedCommandStorage.saveToJson(jsonFile)
            }
            else -> {
                print("Your input is incorrect\n")
            }
        }
    }
}

enum class UserOption(val value: Char) {
    INSERT_TO_START('1'), INSERT_TO_END('2'),
    MOVE_ELEMENT('3'), OUTPUT_LIST('4'),
    CANCEL_LAST_ACTION('5'), OPERATE_WITH_JSON('6')
}

/**
 * Adds options to the list
 * @return list of options
 */
fun getOption(userOption: Char, jsonFile: File): Option<Int>? {
    return when (userOption) {
        UserOption.INSERT_TO_START.value -> InsertToStartOption(scanNumber("Enter your value: "))
        UserOption.INSERT_TO_END.value -> InsertToEndOption(scanNumber("Enter your value: "))
        UserOption.MOVE_ELEMENT.value -> MoveElementOption()
        UserOption.OUTPUT_LIST.value -> OutputListOption()
        UserOption.CANCEL_LAST_ACTION.value -> CancelLastAction()
        UserOption.OPERATE_WITH_JSON.value -> JsonOperations(jsonFile)
        else -> null
    }
}

/**
 * Program initialization
 */
fun showUserInterface(fileName: String) {
    val jsonFile = File(fileName)
    if (!jsonFile.createNewFile()) jsonFile.writeText("[]")
    val scan = Scanner(System.`in`)
    val performedCommandStorage = PerformedCommandStorage<Int>()
    println("Have a look at the commands I can perform...")
    println("Press 0 exit\nPress 1 to insert the element to the start\n" +
            "Press 2 to insert the element to the end\nPress 3 to move the element (first position is 0)\n" +
            "Press 4 to see the list of elements\nPress 5 to cancel last action\n" +
            "Press 6 to operate with JSON file\n")
    var chosenOption = ' '
    while (chosenOption != '0') {
        print("Type option here: ")
        chosenOption = scan.next().single()
        if (chosenOption == '0') {
            break
        }
        val option = getOption(chosenOption, jsonFile)
        if (option != null) {
            option.performOption(performedCommandStorage)
        } else {
            println("Your option number is incorrect")
        }
    }
    println("You exit the app")
}
