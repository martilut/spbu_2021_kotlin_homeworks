package homework.task3

import java.lang.IndexOutOfBoundsException

interface Option {
    fun performOption(scan: java.util.Scanner, performedCommandStorage: PerformedCommandStorage)
}

class InsertToStartOption : Option {
    override fun performOption(scan: java.util.Scanner, performedCommandStorage: PerformedCommandStorage) {
        print("Enter your value: ")
        val userValue = scan.nextInt()
        performedCommandStorage.makeAction(InsertToStart(userValue))
    }
}

class InsertToEndOption : Option {
    override fun performOption(scan: java.util.Scanner, performedCommandStorage: PerformedCommandStorage) {
        print("Enter your value: ")
        val userValue = scan.nextInt()
        performedCommandStorage.makeAction(InsertToEnd(userValue))
    }
}

class MoveElementOption : Option {
    override fun performOption(scan: java.util.Scanner, performedCommandStorage: PerformedCommandStorage) {
        print("Enter the start and the end position: ")
        val startPosition = scan.nextInt()
        val endPosition = scan.nextInt()
        try {
            performedCommandStorage.makeAction(MoveElement(startPosition, endPosition))
        }
        catch (e: IndexOutOfBoundsException) {
            println("Your position(s) are incorrect")
        }
    }
}

class OutputListOption : Option {
    override fun performOption(scan: java.util.Scanner, performedCommandStorage: PerformedCommandStorage) {
        val elements: MutableList<Int> = performedCommandStorage.getElements()
        if (elements.size != 0) {
            for (element in elements) print("$element ")
            print("\n")
        } else {
            println("List is empty")
        }
    }
}

class CancelLastAction : Option {
    override fun performOption(scan: java.util.Scanner, performedCommandStorage: PerformedCommandStorage) {
        if (performedCommandStorage.getActions().size != 0) {
            performedCommandStorage.cancelLastAction()
        } else {
            println("No actions had been performed yet")
        }
    }
}

fun getListOfOptions(): MutableList<Option> {
    val optionList = mutableListOf<Option>()
    optionList.add(InsertToStartOption())
    optionList.add(InsertToEndOption())
    optionList.add(MoveElementOption())
    optionList.add(OutputListOption())
    optionList.add(CancelLastAction())
    return optionList
}

fun showUserInterface() {
    val scan = java.util.Scanner(System.`in`)
    val performedCommandStorage = PerformedCommandStorage()
    val optionList: MutableList<Option> = getListOfOptions()
    println("Have a look at the commands I can perform...")
    println("Press 0 exit\nPress 1 to insert the element to the start\n" +
            "Press 2 to insert the element to the end\nPress 3 to move the element\n" +
            "Press 4 to see the list of elements\nPress 5 to cancel last action\n")
    var chosenOption = -1
    while (chosenOption != 0) {
        print("Type option here: ")
        chosenOption = scan.nextInt()
        try {
            optionList[chosenOption - 1].performOption(scan, performedCommandStorage)
        } catch (e: IndexOutOfBoundsException) {
            println("Your option number is incorrect")
        }
    }
    println("You exit the app")
}
