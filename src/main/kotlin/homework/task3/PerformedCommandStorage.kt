package homework.task3

class PerformedCommandStorage {
    val elements: MutableList<Int>
        get() {
            return _elements
        }
    val performedActions: MutableList<Action>
        get() {
            return _performedActions
        }
    private val _elements = mutableListOf<Int>()
    private val _performedActions = mutableListOf<Action>()
    fun makeAction(action: Action) {
        action.makeAction(_elements)
        _performedActions.add(action)
    }
    fun cancelLastAction() {
        val action: Action = _performedActions.last()
        action.cancelAction(_elements)
        _performedActions.removeLast()
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

fun areCorrect(startIndex: Int, endIndex: Int, range: IntRange): Boolean {
    return startIndex in range && endIndex in range
}

class MoveElement(private val start: Int, private val end: Int) : Action {
    private fun moveElementAction(elements: MutableList<Int>, startIndex: Int, endIndex: Int) {
        if (!areCorrect(startIndex, endIndex, elements.indices)) {
            throw IllegalArgumentException("Your position(s) are incorrect")
        }
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
