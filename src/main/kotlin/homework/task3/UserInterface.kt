package homework.task3

import library.scanNumber

/**
 * Basic interface for options
 */
interface Option {
    fun performOption(scan: java.util.Scanner, performedCommandStorage: PerformedCommandStorage)
}

/**
 * Option: select action InsertToStart
 */
class InsertToStartOption : Option {
    override fun performOption(scan: java.util.Scanner, performedCommandStorage: PerformedCommandStorage) {
        val userValue = scanNumber(scan, "Enter your value: ")
        performedCommandStorage.makeAction(InsertToStart(userValue))
    }
}

/**
 * Option: select action InsertToEnd
 */
class InsertToEndOption : Option {
    override fun performOption(scan: java.util.Scanner, performedCommandStorage: PerformedCommandStorage) {
        val userValue = scanNumber(scan, "Enter your value: ")
        performedCommandStorage.makeAction(InsertToEnd(userValue))
    }
}

/**
 * Option: select action MoveElement
 */
class MoveElementOption : Option {
    override fun performOption(scan: java.util.Scanner, performedCommandStorage: PerformedCommandStorage) {
        val startPosition = scanNumber(scan, "Enter the start position: ")
        val endPosition = scanNumber(scan, "Enter the final position: ")
        try {
            performedCommandStorage.makeAction(MoveElement(startPosition, endPosition))
        } catch (e: IllegalArgumentException) {
            println("${e.message}")
        }
    }
}

/**
 * Option: output list of elements
 */
class OutputListOption : Option {
    override fun performOption(scan: java.util.Scanner, performedCommandStorage: PerformedCommandStorage) {
        val elements: MutableList<Int> = performedCommandStorage.elements
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
class CancelLastAction : Option {
    override fun performOption(scan: java.util.Scanner, performedCommandStorage: PerformedCommandStorage) {
        if (performedCommandStorage.performedActions.size != 0) {
            performedCommandStorage.cancelLastAction()
        } else {
            println("No actions had been performed yet")
        }
    }
}

/**
 * Adds options to the list
 * @return list of options
 */
fun getListOfOptions(): MutableList<Option> {
    val optionList = mutableListOf<Option>()
    optionList.add(InsertToStartOption())
    optionList.add(InsertToEndOption())
    optionList.add(MoveElementOption())
    optionList.add(OutputListOption())
    optionList.add(CancelLastAction())
    return optionList
}

/**
 * Program initialization
 */
fun showUserInterface() {
    val scan = java.util.Scanner(System.`in`)
    val performedCommandStorage = PerformedCommandStorage()
    val optionList: MutableList<Option> = getListOfOptions()
    println("Have a look at the commands I can perform...")
    println("Press 0 exit\nPress 1 to insert the element to the start\n" +
            "Press 2 to insert the element to the end\nPress 3 to move the element (first position is 0)\n" +
            "Press 4 to see the list of elements\nPress 5 to cancel last action\n")
    var chosenOption = -1
    while (chosenOption != 0) {
        print("Type option here: ")
        chosenOption = scan.nextInt()
        if (chosenOption == 0) {
            break
        }
        if (chosenOption in 1..optionList.size) {
            optionList[chosenOption - 1].performOption(scan, performedCommandStorage)
        } else {
            println("Your option number is incorrect")
        }
    }
    println("You exit the app")
}
