package homework5

import util.scanLine

fun userInput() {
    try {
        val expression = scanLine("Enter your expression: ")
        val tree = ParserTree(parseExpression(expression))
        print(tree.getOutputTree())
        print("Result: ${tree.getResult()}")
    } catch (e: IllegalArgumentException) {
        print("${e.message}")
    }
}

fun main() {
    userInput()
}
