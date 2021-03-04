package homework.task3

class PerformedCommandStorage {
    private val elements = mutableListOf<Int>()
    private val performedActions = mutableListOf<Action>()
    fun getElements() = elements
    fun getActions() = performedActions
    fun makeAction(action: Action) {
        action.makeAction(elements)
        performedActions.add(action)
    }
    fun cancelLastAction() {
        val action: Action = performedActions.last()
        action.cancelAction(elements)
        performedActions.removeLast()
    }
}

interface Action {
    fun makeAction(elements: MutableList<Int>)
    fun cancelAction(elements: MutableList<Int>)
}

class InsertToStart(private val value: Int) : Action {
    override fun makeAction(elements: MutableList<Int>) {
        elements.add(0, value)
    }
    override fun cancelAction(elements: MutableList<Int>) {
        elements.removeFirst()
    }
}

class InsertToEnd(private val value: Int) : Action {
    override fun makeAction(elements: MutableList<Int>) {
        elements.add(value)
    }
    override fun cancelAction(elements: MutableList<Int>) {
        elements.removeLast()
    }
}

fun areCorrect(startIndex: Int, endIndex: Int, listSize: Int): Boolean {
    return startIndex in 0 until listSize && endIndex in 0 until listSize
}

class MoveElement(private val start: Int, private val end: Int) : Action {
    private fun moveElementAction(elements: MutableList<Int>, startIndex: Int, endIndex: Int) {
        if (!areCorrect(startIndex, endIndex, elements.size))
            throw IllegalArgumentException("Your position(s) are incorrect")
        if (endIndex > startIndex) {
            elements.add(endIndex + 1, elements[startIndex])
            elements.removeAt(startIndex)
        } else {
            elements.add(endIndex, elements[startIndex])
            elements.removeAt(startIndex + 1)
        }
    }

    override fun makeAction(elements: MutableList<Int>) {
        moveElementAction(elements, start, end)
    }

    override fun cancelAction(elements: MutableList<Int>) {
        moveElementAction(elements, end, start)
    }
}
