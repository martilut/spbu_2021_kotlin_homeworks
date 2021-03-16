package library

fun scanNumber(message: String): Int {
    print(message)
    return java.util.Scanner(System.`in`).nextInt()
}
