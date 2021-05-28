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
    }

    @MethodSource("dataInt")
    @ParameterizedTest(name = "dataInt test, {1}")
    fun bubbleSortInt(data: MutableList<Int>) {
        assertEquals(data.sorted(), data.bubbleSort(Comparator.naturalOrder()))
    }
}