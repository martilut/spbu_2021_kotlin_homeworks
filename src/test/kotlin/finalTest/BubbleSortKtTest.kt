package finalTest

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class BubbleSortTest {
    companion object {
        @JvmStatic
        fun dataInt(): List<Arguments> = listOf(
            Arguments.of(mutableListOf(9, 7, 2, 3, 4, 6, 5, 1, 8)),
            Arguments.of(mutableListOf(75, 12, 36, 255, 12, -78, -41, 3, 11, 12)),
            Arguments.of(mutableListOf(1, 1000000, -4564564, 123245, 454984465, 1235132))
        )

        @JvmStatic
        fun dataDouble(): List<Arguments> = listOf(
            Arguments.of(mutableListOf(9.31, 7.643, 2.01, 3.1415, 4.11, 6.0, 5.00, 1.3, 8.01)),
            Arguments.of(mutableListOf(7.5, 1.2, 3.6, 25.5, 1.2, -78.6, -41.8, 3.7, 11.11, 12.7)),
            Arguments.of(mutableListOf(1.0, 1000.0001, -4564.564, 1232.45, 45498446.5, 123513.2))
        )

        @JvmStatic
        fun dataString(): List<Arguments> = listOf(
            Arguments.of(mutableListOf("9.31", "7.643", "2.01", "3.1415", "4.11", "6.0", "5.00", "1.3", "")),
            Arguments.of(mutableListOf("one", "oneone", "twothree", "twenty", "twenty five", "minus plus", "one one one one", "o")),
            Arguments.of(mutableListOf("prosay", "sollise", "hossana", "protego", "sanctus", "cosa", "padre", "illuminata"))
        )
    }

    @MethodSource("dataInt")
    @ParameterizedTest(name = "dataInt test, {1}")
    fun bubbleSortInt(data: MutableList<Int>) {
        assertEquals(data.sorted(), data.bubbleSort(Comparator.naturalOrder()))
    }

    @MethodSource("dataDouble")
    @ParameterizedTest(name = "dataDouble test, {1}")
    fun bubbleSortDouble(data: MutableList<Int>) {
        assertEquals(data.sorted(), data.bubbleSort(Comparator.naturalOrder()))
    }

    @MethodSource("dataString")
    @ParameterizedTest(name = "dataString test, {1}")
    fun bubbleSortString(data: MutableList<String>) {
        val expectedList = data
        expectedList.sortBy { it.length }
        assertEquals(expectedList, data.bubbleSort(compareBy { it.length }))
    }
}