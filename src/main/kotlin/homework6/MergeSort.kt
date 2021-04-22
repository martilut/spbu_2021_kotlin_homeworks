package homework6

fun IntArray.mergeSort(left: Int = 0, right: Int = this.size) {
    if (left + 1 >= right) {
        return
    }
    val mid: Int = (left + right) / 2
    mergeSort(left, mid)
    mergeSort(mid, right)
    mergeArrays(left, right, mid)
}

fun IntArray.mergeArrays(left: Int, right: Int, mid: Int) {
    var firstIndex = 0
    var secondIndex = 0
    val result = IntArray(right - left) { 0 }

    while (left + firstIndex < mid && mid + secondIndex < right) {
        if (this[left + firstIndex] < this[mid + secondIndex]) {
            result[firstIndex + secondIndex] = this[left + firstIndex]
            ++firstIndex
        } else {
            result[firstIndex + secondIndex] = this[mid + secondIndex]
            ++secondIndex
        }
    }

    while (left + firstIndex < mid) {
        result[firstIndex + secondIndex] = this[left + firstIndex]
        ++firstIndex
    }

    while (mid + secondIndex < right) {
        result[firstIndex + secondIndex] = this[mid + secondIndex]
        ++secondIndex
    }

    for (i in 0 until right - left) {
        this[left + i] = result[i]
    }
}

fun IntArray.mergeSortMultithreading(threadCount: Int = 2, left: Int = 0, right: Int = this.size) {
    val mid: Int = (left + right) / 2
    if (threadCount == 1 || right - left < 1) {
        this.mergeSort(left, right)
    } else {
        val leftThreads: Int = threadCount / 2
        val rightThreads: Int = threadCount - leftThreads
        val leftThread = Thread { this.mergeSortMultithreading(leftThreads, left, mid) }
        val rightThread = Thread { this.mergeSortMultithreading(rightThreads, mid, right) }

        leftThread.start()
        rightThread.start()
        leftThread.join()
        rightThread.join()

        this.mergeArrays(left, right, mid)
    }
}
