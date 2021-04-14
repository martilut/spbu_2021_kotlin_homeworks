package homework5

enum class ArithmeticOperation(val value: String) {
    PLUS("+"), MINUS("-"),
    MULTIPLY("*"), DIVIDE("/")
}

fun operationContains(lexeme: String): ArithmeticOperation? {
    var foundArithmeticOperation: ArithmeticOperation? = null
    for (operation in enumValues<ArithmeticOperation>()) {
        if (operation.value == lexeme) {
            foundArithmeticOperation = operation
        }
    }
    return foundArithmeticOperation
}
