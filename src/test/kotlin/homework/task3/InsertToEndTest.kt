package homework.task3

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

internal class InsertToEndTest {

    @Test
    fun makeAction() {
        val testList = mutableListOf(1, 2, 3)
        InsertToEnd(4).makeAction(testList)
        assertEquals(mutableListOf(1, 2, 3, 4), testList)
    }

    @Test
    fun cancelAction() {
        val testList = mutableListOf(1, 2, 3, 4)
        InsertToEnd(4).cancelAction(testList)
        assertEquals(mutableListOf(1, 2, 3), testList)
    }
}