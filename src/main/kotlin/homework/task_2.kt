package homework

fun countSubstrings(mainString: String, subString: String): Int {
    var answer = 0
    val mainStringSize = mainString.length
    val subStringSize = subString.length
    for (i in 0 until (mainStringSize - subStringSize + 1)) {
        if (mainString.substring(i, i + subStringSize) == subString) {
            ++answer
        }
    }
    return answer
}

val scan = java.util.Scanner(System.`in`)

fun main() {
    print("Enter the first string: ")
    val mainString = scan.next()
    print("Enter the second string: ")
    val subString = scan.next()
    println("The second string occurs in the first string ${countSubstrings(mainString, subString)} times")
}
