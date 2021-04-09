package homework.task3.actions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Basic interface for actions
 */
interface Action<T> {
    fun makeAction(elements: MutableList<T>)
    fun cancelAction(elements: MutableList<T>)
}

/**
 * Action: insert element to the start of the list
 */
@Serializable
@SerialName("InsertToStart")
class InsertToStart<T>(private val value: T) : Action<T> {
    override fun makeAction(elements: MutableList<T>) {
        elements.add(0, value)
    }
    override fun cancelAction(elements: MutableList<T>) {
        elements.removeFirst()
    }
}

/**
 * Action: insert element to the end of the list
 */
@Serializable
@SerialName("InsertToEnd")
class InsertToEnd<T>(private val value: T) : Action<T> {
    override fun makeAction(elements: MutableList<T>) {
        elements.add(value)
    }
    override fun cancelAction(elements: MutableList<T>) {
        elements.removeLast()
    }
}

/**
 * Action: change position of given element in the list
 */
@Serializable
@SerialName("MoveElement")
class MoveElement<T>(private val start: Int, private val end: Int) : Action<T> {
    private fun areCorrect(startIndex: Int, endIndex: Int, range: IntRange): Boolean {
        return startIndex in range && endIndex in range
    }
    private fun moveElementAction(elements: MutableList<T>, startIndex: Int, endIndex: Int) {
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

    override fun makeAction(elements: MutableList<T>) {
        moveElementAction(elements, start, end)
    }

    override fun cancelAction(elements: MutableList<T>) {
        moveElementAction(elements, end, start)
    }
}
