package homework6

import kotlin.random.Random
import kotlin.system.measureNanoTime

class SortingStatistics {

    data class SortingData(val count: Int, val time: Double)

    private fun generateElements(count: Int): IntArray {
        return IntArray(count) { Random.nextInt(0, count) }
    }

    fun getStatistics(threadValue: Int, countValue: Int, stepValue: Int): MutableList<SortingData> {
        val statistics = mutableListOf<SortingData>()
        for (i in 0..countValue step stepValue) {
            val elements = generateElements(i)
            val time = measureNanoTime { MergeSorting().mergeSort(elements, threadValue) }
            statistics.add(
                SortingData(i, time.toDouble())
            )
        }
        return statistics
    }
}
