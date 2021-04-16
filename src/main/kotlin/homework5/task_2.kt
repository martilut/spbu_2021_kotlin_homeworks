package homework5

import homework5.hashTable.HashTable
import homework5.hashTable.DefaultHashFunction
import homework5.hashTable.Action
import homework5.hashTable.InsertElement
import homework5.hashTable.RemoveElement
import homework5.hashTable.FindElement
import homework5.hashTable.SelectHashFunction
import homework5.hashTable.FillFromFile
import homework5.hashTable.OutputStatistics

import util.scanNumber

fun getActionList(): List<Action> {
    return listOf(
        InsertElement(),
        RemoveElement(),
        FindElement(),
        SelectHashFunction(),
        FillFromFile(),
        OutputStatistics()
    )
}

fun showUserInterface(hashTable: HashTable<String, Double>) {
    println("Exit -> 0\n" +
            "${InsertElement().name} -> 1\n" +
            "${RemoveElement().name} -> 2\n" +
            "${FindElement().name} -> 3\n" +
            "${SelectHashFunction().name} -> 4\n" +
            "${FillFromFile().name} -> 5\n" +
            "${OutputStatistics().name} -> 6\n")
    var userInput = -1
    val actionList = getActionList()
    while (userInput != 0) {
        userInput = scanNumber("Enter here: ")
        if (userInput == 0) {
            println("You exit")
            break
        }
        if (userInput in 1..actionList.size) {
            actionList[userInput - 1].performAction(hashTable)
        } else {
            println("Input is incorrect")
        }
    }
}

fun main() {
    val hashTable = HashTable<String, Double>(DefaultHashFunction())
    showUserInterface(hashTable)
}
