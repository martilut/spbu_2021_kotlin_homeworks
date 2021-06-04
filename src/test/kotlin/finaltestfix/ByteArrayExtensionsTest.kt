package finaltestfix

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class ByteArrayExtensionsTest {

    companion object {
        @JvmStatic
        fun uncompressedArrays(): List<Arguments> = listOf(
            Arguments.of(intArrayOf(1, 2, 3, 4, 5), intArrayOf(1, 1, 1, 2, 1, 3, 1, 4, 1, 5)),
            Arguments.of(intArrayOf(1, 1, 1, 1, 1), intArrayOf(5, 1)),
            Arguments.of(intArrayOf(1, 2, 3, 2, 1), intArrayOf(2, 1, 2, 2, 1, 3)),
            Arguments.of(intArrayOf(2, 1, 3, 2, 5, 6, 5, 1), intArrayOf(2, 2, 2, 1, 1, 3, 2, 5, 1, 6)),
            Arguments.of(intArrayOf(2, 2, 3, 7, 3, 0, 0), intArrayOf(2, 2, 2, 3, 1, 7, 2, 0))
        )

        @JvmStatic
        fun compressedArrays(): List<Arguments> = listOf(
            Arguments.of(intArrayOf(1, 1, 1, 2, 1, 3, 1, 4, 1, 5), intArrayOf(1, 2, 3, 4, 5)),
            Arguments.of(intArrayOf(5, 1), intArrayOf(1, 1, 1, 1, 1)),
            Arguments.of(intArrayOf(2, 1, 2, 2, 1, 3), intArrayOf(1, 1, 2, 2, 3)),
            Arguments.of(intArrayOf(2, 2, 2, 1, 1, 3, 2, 5, 1, 6), intArrayOf(2, 2, 1, 1, 3, 5, 5, 6)),
            Arguments.of(intArrayOf(2, 2, 2, 3, 1, 7, 2, 0), intArrayOf(2, 2, 3, 3, 7, 0, 0))
        )
    }

    private fun byteArrayOfInts(ints: IntArray) = ByteArray(ints.size) { pos -> ints[pos].toByte() }

    @MethodSource("uncompressedArrays")
    @ParameterizedTest(name = "test{index}, {1}")
    fun compress(ints: IntArray, expectedInts: IntArray) {
        assertArrayEquals(byteArrayOfInts(expectedInts), byteArrayOfInts(ints).compress())
    }

    @MethodSource("compressedArrays")
    @ParameterizedTest(name = "test{index}, {1}")
    fun decompress(ints: IntArray, expectedInts: IntArray) {
        assertArrayEquals(byteArrayOfInts(expectedInts), byteArrayOfInts(ints).decompress())
    }
}