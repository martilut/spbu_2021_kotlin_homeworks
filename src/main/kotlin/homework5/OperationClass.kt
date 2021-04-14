package homework5

enum class Operation(val value: String) {
    PLUS("+"), MINUS("-"),
    MULTIPLY("*"), DIVIDE("/")
}

fun operationContains(lexeme: String): Operation? {
    var foundOperation: Operation? = null
    for (operation in enumValues<Operation>()) {
        if (operation.value == lexeme) {
            foundOperation = operation
        }
    }
    return foundOperation
}
