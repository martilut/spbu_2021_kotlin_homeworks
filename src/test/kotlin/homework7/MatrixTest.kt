package homework7

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.lang.IllegalArgumentException

internal class MatrixTest {

    companion object {
        @JvmStatic
        fun correctMatrices(): List<Arguments> = listOf(
            Arguments.of(
                Matrix(
                    arrayOf(
                        intArrayOf(1, 2, 3),
                        intArrayOf(4, 5, 6),
                        intArrayOf(7, 8, 9)
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(1, 4, 7),
                        intArrayOf(2, 5, 8),
                        intArrayOf(3, 6, 9)
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(14, 32, 50),
                        intArrayOf(32, 77, 122),
                        intArrayOf(50, 122, 194)
                    )
                )
            ),
            Arguments.of(
                Matrix(
                    arrayOf(
                        intArrayOf(1000, -1),
                        intArrayOf(23, 145)
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(-20, -21),
                        intArrayOf(99, 32)
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(-20099, -21032),
                        intArrayOf(13895, 4157)
                    )
                )
            ),

            Arguments.of(
                Matrix(
                    arrayOf(
                        intArrayOf(-1, 0, 2),
                        intArrayOf(3, 1, -4)
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(-3, 7),
                        intArrayOf(4, 0),
                        intArrayOf(5, -2)
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(13, -11),
                        intArrayOf(-25, 29)
                    )
                )
            ),
            Arguments.of(
                Matrix(
                    arrayOf(
                        intArrayOf(1, 2, 3),
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(4),
                        intArrayOf(5),
                        intArrayOf(6)
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(32)
                    )
                )
            ),
            Arguments.of(
                Matrix(
                    arrayOf(
                        intArrayOf(1, 2),
                        intArrayOf(4, 5),
                        intArrayOf(7, 8),
                        intArrayOf(10, 11)
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(1, 4, 7, 10),
                        intArrayOf(2, 5, 8, 11),
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(5, 14, 23, 32),
                        intArrayOf(14, 41, 68, 95),
                        intArrayOf(23, 68, 113, 158),
                        intArrayOf(32, 95, 158, 221)
                    )
                )
            )
        )

        @JvmStatic
        fun incorrectMatrices(): List<Arguments> = listOf(
            Arguments.of(
                Matrix(
                    arrayOf(
                        intArrayOf(1, 2),
                        intArrayOf(3, 4)
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(1, 2, 3),
                        intArrayOf(5, 6, 7)
                    )
                )
            ),
            Arguments.of(
                Matrix(
                    arrayOf(
                        intArrayOf(1, 2, 3),
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(4, 7),
                        intArrayOf(5, 8),
                        intArrayOf(6, 9)
                    )
                )
            ),
            Arguments.of(
                Matrix(
                    arrayOf(
                        intArrayOf(1, 2),
                        intArrayOf(7, 8),
                        intArrayOf(10, 11)
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(1, 4, 7, 10),
                        intArrayOf(2, 5, 8, 11),
                    )
                )
            ),
            Arguments.of(
                Matrix(
                    arrayOf(
                        intArrayOf(1, 2),
                        intArrayOf(4, 5),
                        intArrayOf(7, 8),
                        intArrayOf(10, 11)
                    )
                ),
                Matrix(
                    arrayOf(
                        IntArray(1)
                    )
                )
            ),
            Arguments.of(
                Matrix(
                    arrayOf(
                        intArrayOf(1, 2, 3, 4)
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(1, 4, 7),
                        intArrayOf(2, 5, 8),
                    )
                )
            )
        )
    }

    @MethodSource("correctMatrices")
    @ParameterizedTest(name = "Test correct matrices")
    fun multiplyCorrectMatrices(first: Matrix, second: Matrix, result: Matrix) {
        val newMatrix = first.multiplyOn(second)
        for (i in 0 until result.rows) {
            for (j in 0 until result.columns) {
                assertEquals(result.matrix[i][j], newMatrix.matrix[i][j])
            }
        }
    }

    @MethodSource("incorrectMatrices")
    @ParameterizedTest(name = "incorrectTest{index}")
    fun multiplyIncorrectMatrices(first: Matrix, second: Matrix) {
        assertThrows<IllegalArgumentException> { first.multiplyOn(second) }
    }
}
