package homework

fun iterativeFactorial(n: Int): Int {
    var answer = 1
    var count = 0
    repeat(n) {
        answer *= (++count)
    }
    return answer
}

fun recursiveFactorial(n: Int): Int {
    if (n == 0) {
        return 1
    }
    return n * recursiveFactorial(n - 1)
}

val scan = java.util.Scanner(System.`in`)

fun getInput(): Int {
    var n = scan.nextInt()
    while (n < 0) {
        print("Your number must be positive: ")
        n = scan.nextInt()
    }
    return n
}

fun main() {
    print("Enter your number: ")
    val n = getInput()
    println("Iterative factorial is ${iterativeFactorial(n)}")
    println("Recursive factorial is ${recursiveFactorial(n)}")
}
