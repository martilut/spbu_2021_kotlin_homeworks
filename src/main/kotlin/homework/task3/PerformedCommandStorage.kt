package homework.task3

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * Main class for operations with actions
 * @property elements contains the current list of elements
 * @property performedActions contains the list of actions performed to the elements
 */
class PerformedCommandStorage {
    val elements: MutableList<Int> = mutableListOf<Int>()
    val performedActions: MutableList<Action> = mutableListOf<Action>()

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

/**
 * Basic interface for actions
 */
interface Action {
    fun makeAction(elements: MutableList<Int>)
    fun cancelAction(elements: MutableList<Int>)
}

/**
 * Action: insert element to the start of the list
 */
@Serializable
@SerialName("InsertToStart")
class InsertToStart(private val value: Int) : Action {
    override fun makeAction(elements: MutableList<Int>) {
        elements.add(0, value)
    }
    override fun cancelAction(elements: MutableList<Int>) {
        elements.removeFirst()
    }
}

/**
 * Action: insert element to the end of the list
 */
@Serializable
@SerialName("InsertToEnd")
class InsertToEnd(private val value: Int) : Action {
    override fun makeAction(elements: MutableList<Int>) {
        elements.add(value)
    }
    override fun cancelAction(elements: MutableList<Int>) {
        elements.removeLast()
    }
}

/**
 * Action: change position of given element in the list
 */
@Serializable
@SerialName("MoveElement")
class MoveElement(private val start: Int, private val end: Int) : Action {
    private fun areCorrect(startIndex: Int, endIndex: Int, range: IntRange): Boolean {
        return startIndex in range && endIndex in range
    }
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
