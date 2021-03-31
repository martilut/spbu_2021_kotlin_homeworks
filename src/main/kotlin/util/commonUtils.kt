package util

fun scanNumber(message: String): Int {
    print(message)
    return java.util.Scanner(System.`in`).nextInt()
}

fun scanLine(message: String): String {
    print(message)
    return java.util.Scanner(System.`in`).nextLine()
}
