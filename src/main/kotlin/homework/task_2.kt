package homework

fun countSubstrings(mainString: String, subString: String): Int {
    val mainStringSize = mainString.length
    val subStringSize = subString.length
    if (mainStringSize == 0 || subStringSize == 0 || subStringSize > mainStringSize) {
        return -1
    }
    return mainString.windowed(subStringSize) {
        if (it == subString) 1 else 0
    }.sum()
}

fun main() {
    val scan = java.util.Scanner(System.`in`)
    print("Enter the main string: ")
    val mainString = scan.nextLine()
    print("Enter the string to find: ")
    val subString = scan.nextLine()
    val answer = countSubstrings(mainString, subString)
    if (answer == -1) {
        println("Your input is incorrect")
    } else {
        println("The string $subString occurs in the string $mainString $answer times")
    }
}
