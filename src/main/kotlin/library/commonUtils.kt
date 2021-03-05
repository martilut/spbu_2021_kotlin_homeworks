package library

fun scanNumber(scan: java.util.Scanner, message: String): Int {
    print(message)
    return scan.nextInt()
}