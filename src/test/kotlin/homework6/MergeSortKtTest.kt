package homework6

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class MergeSortKtTest {

    companion object {
        @JvmStatic
        fun arraysToSort(): List<Arguments> = listOf(
            Arguments.of(
                intArrayOf(1, 2, 3, 4, 5), intArrayOf(5, 1, 2, 4, 3)
            ),
            Arguments.of(
                intArrayOf(4, 17, 22, 100, 9151, 15000), intArrayOf(100, 9151, 15000, 4, 17, 22)
            ),
            Arguments.of(
                intArrayOf(0, 10, 1434, 49999, 1123331, 35533553), intArrayOf(1123331, 1434, 10, 49999, 35533553, 0)
            ),
            Arguments.of(
                intArrayOf(19, 19, 59, 59, 59), intArrayOf(59, 19, 59, 19, 59)
            )
        )

        @JvmStatic
        fun arraysToMerge(): List<Arguments> = listOf(
            Arguments.of(
                intArrayOf(1, 2, 3, 4, 5), intArrayOf(1, 4, 5, 2, 3), 3
            ),
            Arguments.of(
                intArrayOf(4, 17, 22, 100, 9151, 15000), intArrayOf(22, 100, 15000, 4, 17, 9151), 3
            ),
            Arguments.of(
                intArrayOf(0, 10, 1434, 4999, 11233, 35530), intArrayOf(0, 35530, 10, 1434, 4999, 11233), 2
            ),
            Arguments.of(
                intArrayOf(19, 19, 59, 59, 59), intArrayOf(19, 59, 59, 19, 59), 3
            )
        )

        @JvmStatic
        fun arraysToSortMultithreading(): List<Arguments> = listOf(
            Arguments.of(
                intArrayOf(1, 2, 3, 4, 5), intArrayOf(5, 1, 2, 4, 3), 2
            ),
            Arguments.of(
                intArrayOf(4, 17, 22, 100, 9151, 15000), intArrayOf(100, 9151, 15000, 4, 17, 22), 10
            ),
            Arguments.of(
                intArrayOf(0, 10, 1434, 49999, 1123331, 35533553), intArrayOf(1123331, 1434, 10, 49999, 35533553, 0), 100
            ),
            Arguments.of(
                intArrayOf(19, 19, 59, 59, 59), intArrayOf(59, 19, 59, 19, 59), 500
            )
        )
    }

    @MethodSource("arraysToSort")
    @ParameterizedTest(name = "MergeSortTest")
    fun mergeSort(expected: IntArray, input: IntArray) {
        input.mergeSort()
        assertEquals(expected.toList(), input.toList())
    }

    @MethodSource("arraysToMerge")
    @ParameterizedTest(name = "MergeArraysTest")
    fun mergeArrays(expected: IntArray, input: IntArray, mid: Int) {
        input.mergeArrays(0, input.size, mid)
        assertEquals(expected.toList(), input.toList())
    }

    @MethodSource("arraysToSortMultithreading")
    @ParameterizedTest(name = "MergeSortMultithreadingTest")
    fun mergeSortMultithreading(expected: IntArray, input: IntArray, threadCount: Int) {
        input.mergeSortMultithreading(threadCount)
        assertEquals(expected.toList(), input.toList())
    }
}
