@file:Suppress("TooGenericExceptionCaught")
package homework7

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Matrix(val matrix: Array<IntArray>) {

    val rows: Int = matrix.size
    val columns: Int = matrix[0].size

    init {
        matrix.forEach {
            require(it.size == matrix[0].size) {
                "Matrix has incorrect row"
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

    fun isEqualTo(other: Matrix): Boolean {
        return when {
            this.rows != other.rows || this.columns != other.columns -> false
            else -> {
                var areEqual = true
                for (i in 0 until this.rows) {
                    for (j in 0 until this.columns) {
                        if (this.matrix[i][j] != other.matrix[i][j]) areEqual = false
                    }
                }
                areEqual
            }
        }
    }
}
