package homework7

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.IllegalArgumentException

class Matrix(val matrix: Array<IntArray>) {

    init {
        matrix.forEach {
            require(it.size == matrix[0].size) {
                IllegalArgumentException("Incorrect matrix")
            }
        }
    }

    val rows: Int = matrix.size
    val columns: Int = matrix[0].size

    fun multiplyOn(other: Matrix): Matrix {
        require(this.rows == other.columns) {
            IllegalArgumentException("Incorrect matrices")
        }

        val result = Array(this.rows) { IntArray(other.columns) { 0 } }

        runBlocking {
            for (i in 0 until rows) {
                for (j in 0 until other.columns) {
                    launch {
                        for (k in 0 until other.rows) {
                            try {
                                result[i][j] += matrix[i][k] * other.matrix[k][j]
                            } catch (e: ArrayIndexOutOfBoundsException) {
                                throw IllegalArgumentException("Incorrect matrices")
                            }
                        }
                    }
                }
            }
        }

        return Matrix(result)
    }
}
