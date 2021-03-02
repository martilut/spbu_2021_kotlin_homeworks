package homework

fun getIterativeFactorial(n: Int): Int {
    var answer = 1
    for (i in 1..n) {
        answer *= i
    }
    return answer
}

fun getRecursiveFactorial(n: Int): Int {
    if (n == 0) {
        return 1
    }
    return n * getRecursiveFactorial(n - 1)
}

fun getInput(): Int {
    val scan = java.util.Scanner(System.`in`)
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
    println("Iterative factorial is ${getIterativeFactorial(n)}")
    println("Recursive factorial is ${getRecursiveFactorial(n)}")
}
