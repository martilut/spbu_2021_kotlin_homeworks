package homework.task3

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

internal class MoveElementTest {

    @Test
    fun makeAction() {
        val testList = mutableListOf(1, 2, 3, 4, 5)
        MoveElement(1, 3).makeAction(testList)
        assertEquals(mutableListOf(1, 3, 4, 2, 5), testList)
    }

    @Test
    fun cancelAction() {
        val testList = mutableListOf(1, 3, 4, 2, 5)
        MoveElement(1, 3).cancelAction(testList)
        assertEquals(mutableListOf(1, 2, 3, 4, 5), testList)
    }
}
