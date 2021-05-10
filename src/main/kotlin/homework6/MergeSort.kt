package homework6

class MergeSorting {

    private fun IntArray.binarySearch(value: Int, left: Int, right: Int): Int {
        var leftBorder = left
        var rightBorder = when {
            left > right + 1 -> left
            else -> right + 1
        }
        while (leftBorder < rightBorder) {
            val middle = (leftBorder + rightBorder) / 2
            if (value <= this[middle]) {
                rightBorder = middle
            } else {
                leftBorder = middle + 1
            }
        }
        return rightBorder
    }

    fun mergeSort(array: IntArray, threadCount: Int) {
        if (array.isEmpty()) return
        val tempArray = IntArray(array.size) { 0 }
        array.mergeSortMultithreading(
            ArrayBorders(0, array.lastIndex),
            tempArray, 0, threadCount
        )
        for (i in array.indices) {
            array[i] = tempArray[i]
        }
    }

    data class ArrayBorders(val left: Int, val right: Int, val size: Int = right - left + 1)

    private fun IntArray.mergeArraysMultithreading(
        firstPart: ArrayBorders,
        secondPart: ArrayBorders,
        finalArray: IntArray,
        finalLeftBorder: Int,
        threadCount: Int = 1
    ) {
        when {
            firstPart.size < secondPart.size -> {
                this.mergeArraysMultithreading(
                    secondPart,
                    firstPart,
                    finalArray,
                    finalLeftBorder
                )
                return
            }
            firstPart.size == 0 -> return
            else -> {
                val firstMiddle = (firstPart.left + firstPart.right) / 2
                val secondMiddle =
                    this.binarySearch(this[firstMiddle], secondPart.left, secondPart.right)
                val thirdMiddle =
                    finalLeftBorder + (firstMiddle - firstPart.left) + (secondMiddle - secondPart.left)
                finalArray[thirdMiddle] = this[firstMiddle]

                val leftThreadCount: Int = threadCount / 2
                val rightThreadCount: Int = threadCount - leftThreadCount

                val leftThread = Thread {
                    this.mergeArraysMultithreading(
                        ArrayBorders(firstPart.left, firstMiddle - 1),
                        ArrayBorders(secondPart.left, secondMiddle - 1),
                        finalArray,
                        finalLeftBorder,
                        leftThreadCount
                    )
                }
                val rightThread = Thread {
                    this.mergeArraysMultithreading(
                        ArrayBorders(firstMiddle + 1, firstPart.right),
                        ArrayBorders(secondMiddle, secondPart.right),
                        finalArray,
                        thirdMiddle + 1,
                        rightThreadCount
                    )
                }
                leftThread.start()
                rightThread.start()
                leftThread.join()
                rightThread.join()
            }
        }
    }

    private fun IntArray.mergeSortMultithreading(
        currentPart: ArrayBorders,
        sortedArray: IntArray,
        finalLeftBorder: Int,
        threadCount: Int = 1
    ) {
        when (currentPart.size) {
            1 -> sortedArray[finalLeftBorder] = this[currentPart.left]
            else -> {
                val tempArray = IntArray(currentPart.size) { 0 }
                val middle = (currentPart.left + currentPart.right) / 2
                val sortingMiddle = middle - currentPart.left
                when (threadCount) {
                    1 -> {
                        this.mergeSortMultithreading(
                            ArrayBorders(currentPart.left, middle), tempArray, 0
                        )
                        this.mergeSortMultithreading(
                            ArrayBorders(middle + 1, currentPart.right), tempArray, sortingMiddle + 1
                        )
                    }
                    else -> {
                        val leftThreads: Int = threadCount / 2
                        val rightThreads: Int = threadCount - leftThreads

                        val leftThread = Thread {
                            this.mergeSortMultithreading(
                                ArrayBorders(currentPart.left, middle),
                                tempArray, 0, leftThreads
                            )
                        }
                        val rightThread = Thread {
                            this.mergeSortMultithreading(
                                ArrayBorders(middle + 1, currentPart.right),
                                tempArray, sortingMiddle + 1, rightThreads
                            )
                        }
                        leftThread.start()
                        rightThread.start()
                        leftThread.join()
                        rightThread.join()
                    }
                }
                tempArray.mergeArraysMultithreading(
                    ArrayBorders(0, sortingMiddle),
                    ArrayBorders(sortingMiddle + 1, currentPart.size - 1),
                    sortedArray,
                    finalLeftBorder,
                    threadCount
                )
            }
        }
    }

    /*fun IntArray.mergeSortMultithreading(threadCount: Int = 2, left: Int = 0, right: Int = this.size) {
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
    }*/
}
