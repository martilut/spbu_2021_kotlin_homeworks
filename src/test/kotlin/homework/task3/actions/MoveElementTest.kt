package homework.task3.actions

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

internal class MoveElementTest {

    @Test
    fun makeActionInt() {
        val listInt = mutableListOf<Int>()
        InsertToStart(1).makeAction(listInt)
        InsertToEnd(2).makeAction(listInt)
        MoveElement<Int>(0, 1).makeAction(listInt)
        assertEquals(listOf(2, 1), listInt)
    }

    @Test
    fun makeActionString() {
        val listString = mutableListOf<String>()
        InsertToStart("B").makeAction(listString)
        InsertToEnd("C").makeAction(listString)
        MoveElement<String>(0, 1).makeAction(listString)
        assertEquals(listOf("C", "B"), listString)
    }

    @Test
    fun cancelActionInt() {
        val listInt = mutableListOf<Int>()
        InsertToStart(1).makeAction(listInt)
        InsertToEnd(2).makeAction(listInt)
        InsertToStart(3).makeAction(listInt)
        MoveElement<Int>(2, 1).makeAction(listInt)
        MoveElement<Int>(2, 1).cancelAction(listInt)
        assertEquals(listOf(3, 1, 2), listInt)
    }

    @Test
    fun cancelActionString() {
        val listString = mutableListOf<String>()
        InsertToStart("A").makeAction(listString)
        InsertToEnd("B").makeAction(listString)
        InsertToStart("C").makeAction(listString)
        MoveElement<String>(2, 1).makeAction(listString)
        MoveElement<String>(2, 1).cancelAction(listString)
        assertEquals(listOf("C", "A", "B"), listString)
    }
}
