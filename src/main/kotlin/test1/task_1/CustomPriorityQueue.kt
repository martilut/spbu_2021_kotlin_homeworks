package test1.task_1

import java.util.PriorityQueue

class QueueElement(val value: Any, val priority: Int)

class QueueElementComparator {
    companion object : Comparator<QueueElement> {

        override fun compare(a: QueueElement, b: QueueElement): Int {
            return b.priority.compareTo(a.priority)
        }
    }
}

class CustomPriorityQueue {
    private val priorityQueue = PriorityQueue<QueueElement>(QueueElementComparator)
    var isEmpty : Boolean = true
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
