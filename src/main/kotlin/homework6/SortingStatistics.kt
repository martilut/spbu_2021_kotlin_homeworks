package homework6

import kotlin.random.Random
import kotlin.math.pow
import kotlin.system.measureNanoTime

object BigNumbers {
    const val THOUSAND = 1000
    const val BILLION = 1000000000
}

fun generateElements(count: Int): IntArray {
    return IntArray(count) { Random.nextInt(0, count) }
}

fun getStatistics(threadCount: Int): MutableList<Pair<Int, Double>> {
    val statistics = mutableListOf<Pair<Int, Double>>()
    val two = 2.0
    for (i in 0..20) {
        val count = two.pow(i).toInt()
        val elements = generateElements(count)
        val time = measureNanoTime { elements.mergeSortMultithreading(threadCount) }
        statistics.add(Pair(count / BigNumbers.THOUSAND, time.toDouble() / BigNumbers.BILLION))
    }
    return statistics
}
