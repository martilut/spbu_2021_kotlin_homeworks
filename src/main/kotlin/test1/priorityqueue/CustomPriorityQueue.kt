package test1.priorityqueue

import java.util.PriorityQueue

data class QueueElement(val value: Any, val priority: Int) : Comparable<QueueElement> {
    override fun compareTo(other: QueueElement): Int {
        return other.priority.compareTo(priority)
    }
}

class CustomPriorityQueue {
    private val priorityQueue = PriorityQueue<QueueElement>()
    private var isEmpty: Boolean = true
    fun enqueue(element: Any, priority: Int) {
        priorityQueue.add(QueueElement(element, priority))
        isEmpty = false
    }
    fun peek(): QueueElement? {
        if (priorityQueue.isEmpty()) {
            throw NoSuchElementException("Queue is empty")
        }
        return priorityQueue.peek()
    }
    fun remove() {
        if (priorityQueue.isEmpty()) {
            throw NoSuchElementException("Queue is empty")
        } else {
            priorityQueue.remove(priorityQueue.peek())
        }
    }
    fun rool(): QueueElement? {
        if (priorityQueue.isEmpty()) {
            throw NoSuchElementException("Queue is empty")
        }
        val queueElement = priorityQueue.peek()
        priorityQueue.remove(queueElement)
        return queueElement
    }
}
