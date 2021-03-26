package test1.task_1

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

internal class CustomPriorityQueueTest {

    @Test
    fun enqueue() {
        val priorityQueue = CustomPriorityQueue()
        priorityQueue.enqueue(100, 1)
        priorityQueue.enqueue(20, 5)
        assertEquals(20, priorityQueue.peek()?.value)
    }

    @Test
    fun peek() {
        val priorityQueue = CustomPriorityQueue()
        priorityQueue.enqueue(100, 1)
        assertEquals(false, priorityQueue.isEmpty)
    }

    @Test
    fun remove() {
        val priorityQueue = CustomPriorityQueue()
        priorityQueue.enqueue(100, 1)
        priorityQueue.enqueue(20, 5)
        priorityQueue.enqueue(75, 3)
        priorityQueue.remove()
        assertEquals(75, priorityQueue.peek()?.value)
    }

    @Test
    fun rool() {
        val priorityQueue = CustomPriorityQueue()
        priorityQueue.enqueue(100, 1)
        priorityQueue.enqueue(20, 5)
        priorityQueue.enqueue(75, 3)
        val removedElement = priorityQueue.rool()?.value
        assertEquals(20, removedElement)
        assertEquals(75, priorityQueue.peek()?.value)
    }
}