package homework5

import util.scanLine

fun userInput() {
    try {
        val tree = ExpressionTree()
        val expression = scanLine("Enter your expression: ")
        tree.buildParseTree(parseExpression(expression))
        tree.outputTree().forEach { print(it)}
        print("Result: ${tree.getResult()}")
    } catch (e: IllegalArgumentException) {
        print("${e.message}")
    }
}

fun main() {
    userInput()
}