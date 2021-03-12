package homework2

fun removeDuplicateElements(elements: MutableList<Int>): Set<Int> {
    return elements.reversed().toSet()
}

fun inputElements(): MutableList<Int> {
    val scan = java.util.Scanner(System.`in`)
    val elements = mutableListOf<Int>()
    print("Enter the amount of elements: ")
    val size = scan.nextInt()
    print("Enter your elements: ")
    for (i in 1..size) {
        elements.add(scan.nextInt())
    }
    return elements
}

fun outputElements(elements: List<Int>) {
    for (element in elements) {
        print("$element ")
    }
}

fun main() {
    val elements = inputElements()
    val newList = removeDuplicateElements(elements).reversed()
    outputElements(newList)
}
