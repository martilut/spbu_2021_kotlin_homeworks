package finalTest

@Suppress("NestedBlockDepth")

fun <T> MutableList<T>.bubbleSort(comparator: Comparator<T>): List<T> {
    try {
        var swap = true
        while (swap) {
            swap = false
            for (i in 0 until this.lastIndex) {
                if (comparator.compare(this[i], this[i + 1]) > 0) {
                    this.swap(i, i + 1)
                    swap = true
                }
            }
        }
    } catch (e: ArithmeticException) {
        print("List is incorrect")
    }
    return this
}

fun <T> MutableList<T>.swap(first: Int, second: Int) {
    val buffer = this[first]
    this[first] = this[second]
    this[second] = buffer
}
