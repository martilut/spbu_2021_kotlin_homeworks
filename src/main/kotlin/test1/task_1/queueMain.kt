package test1.task_1

fun main() {
    val priorityQueue = CustomPriorityQueue()
    try {
        priorityQueue.enqueue(12, 1)
        priorityQueue.enqueue("helloWorld", 2)
        priorityQueue.enqueue("234", 0)
        priorityQueue.remove()
        println(priorityQueue.rool()?.value)
    } catch (e: NoSuchElementException) {
        println(e.message)
    }
}