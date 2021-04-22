package homework6

import kotlin.random.Random
import kotlin.math.pow
import kotlin.system.measureNanoTime

object SortingStatistics {
    const val TWO = 2.0
    const val TWENTY = 20
    const val THOUSAND = 1000
    const val BILLION = 1000000000
}

fun generateElements(count: Int): IntArray {
    return IntArray(count) { Random.nextInt(0, count) }
}

fun getStatistics(threadCount: Int): MutableList<Pair<Int, Double>> {
    val statistics = mutableListOf<Pair<Int, Double>>()
    for (i in 0..SortingStatistics.TWENTY) {
        val count = SortingStatistics.TWO.pow(i).toInt()
        val elements = generateElements(count)
        val time = measureNanoTime { elements.mergeSortMultithreading(threadCount) }
        statistics.add(
            Pair(count / SortingStatistics.THOUSAND, time.toDouble() / SortingStatistics.BILLION)
        )
    }
    return statistics
}
