package util

fun scanNumber(message: String): Int {
    print(message)
    return java.util.Scanner(System.`in`).nextInt()
}

fun scanLine(message: String): String {
    print(message)
    return java.util.Scanner(System.`in`).nextLine()
}

fun parseString(elements: String): MutableList<Pair<String, Double>> {
    val pairsList = elements.replace("(", "").replace(")", "").split(", ")
    val elementList = mutableListOf<Pair<String, Double>>()
    for (pair in pairsList) {
        val key = pair.split(": ")[0]
        val value = pair.split(": ")[1].toDoubleOrNull()
        if (value != null) {
            elementList.add(Pair(key, value))
        } else {
            println("Input is incorrect")
            break
        }
    }
    return elementList
}
