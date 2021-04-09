package homework.task3.actions

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

internal class InsertToStartTest {

    @Test
    fun makeActionInt() {
        val listInt = mutableListOf<Int>()
        InsertToStart(1).makeAction(listInt)
        InsertToStart(2).makeAction(listInt)
        InsertToStart(3).makeAction(listInt)
        assertEquals(listOf(3, 2, 1), listInt)
    }

    @Test
    fun makeActionString() {
        val listString = mutableListOf<String>()
        InsertToStart("B").makeAction(listString)
        InsertToStart("C").makeAction(listString)
        InsertToStart("A").makeAction(listString)
        assertEquals(listOf("A", "C", "B"), listString)
    }

    @Test
    fun cancelActionInt() {
        val listInt = mutableListOf<Int>()
        InsertToStart(1).makeAction(listInt)
        InsertToStart(2).makeAction(listInt)
        InsertToStart(2).cancelAction(listInt)
        InsertToStart(3).makeAction(listInt)
        assertEquals(listOf(3, 1), listInt)
    }

    @Test
    fun cancelActionString() {
        val listString = mutableListOf<String>()
        InsertToStart("B").makeAction(listString)
        InsertToStart("C").makeAction(listString)
        InsertToStart("C").cancelAction(listString)
        InsertToStart("A").makeAction(listString)
        assertEquals(listOf("A", "B"), listString)
    }
}
