package homework5

fun parseExpression(expression: String): List<String> {
    return expression
        .replace("(", "")
        .replace(")", "")
        .split(" ")
}
