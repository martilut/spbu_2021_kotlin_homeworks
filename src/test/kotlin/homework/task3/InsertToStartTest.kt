package homework.task3

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

internal class InsertToStartTest {

    @Test
    fun makeAction() {
        val testList = mutableListOf(1, 2, 3)
        InsertToStart(0).makeAction(testList)
        assertEquals(mutableListOf(0, 1, 2, 3), testList)
    }

    @Test
    fun cancelAction() {
        val testList = mutableListOf(0, 1, 2, 3)
        InsertToStart(0).cancelAction(testList)
        assertEquals(mutableListOf(1, 2, 3), testList)
    }
}
