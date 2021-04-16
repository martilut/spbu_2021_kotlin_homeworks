package homework5

enum class OperationType(val value: String) {
    PLUS("+"), MINUS("-"),
    MULTIPLY("*"), DIVIDE("/")
}

fun operationContains(lexeme: String): OperationType? =
    OperationType.values().find { it.value == lexeme }
