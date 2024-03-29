@file:Suppress("TooGenericExceptionCaught")
package homework7

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Matrix(private val matrix: Array<IntArray>) {

    private val rows: Int = matrix.size
    private val columns: Int = matrix[0].size

    init {
        matrix.forEach {
            require(it.size == matrix[0].size) {
                "Incorrect matrix: all the row sizes should be equal"
            }
        }
    }

    fun multiplyOn(other: Matrix): Matrix {
        require(this.rows == other.columns) {
            "Number of rows of the first matrix must be equal to the number of columns of the second one"
        }

        val result = Array(this.rows) { IntArray(other.columns) { 0 } }

        runBlocking {
            for (i in 0 until rows) {
                for (j in 0 until other.columns) {
                    launch {
                        for (k in 0 until other.rows) {
                            result[i][j] += matrix[i][k] * other.matrix[k][j]
                        }
                    }
                }
            }
        }

        return Matrix(result)
    }

    fun isEqualTo(other: Matrix): Boolean = this.matrix.contentDeepEquals(other.matrix)
}
