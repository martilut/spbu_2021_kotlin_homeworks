package homework.task3.actions

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

internal class InsertToEndTest {

    @Test
    fun makeActionInt() {
        val listInt = mutableListOf<Int>()
        InsertToEnd(1).makeAction(listInt)
        InsertToEnd(2).makeAction(listInt)
        InsertToEnd(3).makeAction(listInt)
        assertEquals(listOf(1, 2, 3), listInt)
    }

    @Test
    fun makeActionString() {
        val listString = mutableListOf<String>()
        InsertToEnd("B").makeAction(listString)
        InsertToEnd("C").makeAction(listString)
        InsertToEnd("A").makeAction(listString)
        assertEquals(listOf("B", "C", "A"), listString)
    }

    @Test
    fun cancelActionInt() {
        val listInt = mutableListOf<Int>()
        InsertToEnd(1).makeAction(listInt)
        InsertToEnd(2).makeAction(listInt)
        InsertToEnd(2).cancelAction(listInt)
        InsertToEnd(3).makeAction(listInt)
        assertEquals(listOf(1, 3), listInt)
    }

    @Test
    fun cancelActionString() {
        val listString = mutableListOf<String>()
        InsertToEnd("B").makeAction(listString)
        InsertToEnd("C").makeAction(listString)
        InsertToEnd("C").cancelAction(listString)
        InsertToEnd("A").makeAction(listString)
        assertEquals(listOf("B", "A"), listString)
    }
}
