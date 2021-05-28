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
            ),
            Arguments.of(
                intArrayOf(2, 43, 86, 777, 783, 940, 1000), intArrayOf(783, 43, 1000, 940, 2, 86, 777)
            )
        )

        @JvmStatic
        fun arraysToSortThreadCount(): List<Arguments> = listOf(
            Arguments.of(
                intArrayOf(1, 2, 3, 4, 5), intArrayOf(5, 1, 2, 4, 3), 1
            ),
            Arguments.of(
                intArrayOf(4, 17, 22, 100, 9151, 15000), intArrayOf(100, 9151, 15000, 4, 17, 22), 2
            ),
            Arguments.of(
                intArrayOf(0, 10, 1434, 49999, 1123331, 35533553), intArrayOf(1123331, 1434, 10, 49999, 35533553, 0), 10
            ),
            Arguments.of(
                intArrayOf(19, 19, 59, 59, 59), intArrayOf(59, 19, 59, 19, 59), 100
            ),
            Arguments.of(
                intArrayOf(2, 43, 86, 777, 783, 940, 1000), intArrayOf(783, 43, 1000, 940, 2, 86, 777), 500
            )
        )
    }

    @MethodSource("arraysToSort")
    @ParameterizedTest(name = "test{index}, 1")
    fun mergeSort(expected: IntArray, input: IntArray) {
        MergeSorting.mergeSort(input, 1)
        assertEquals(expected.toList(), input.toList())
    }

    @MethodSource("arraysToSortThreadCount")
    @ParameterizedTest(name = "test{index}, 1")
    fun mergeSortThreadCount(expected: IntArray, input: IntArray, threadCount: Int) {
        MergeSorting.mergeSort(input, threadCount)
        assertEquals(expected.toList(), input.toList())
    }
}
