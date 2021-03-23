package homework.task3

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

internal class PerformedCommandStorageTest {
    @Test
    fun makeAction() {
        val performedCommandStorage = PerformedCommandStorage()
        performedCommandStorage.makeAction(InsertToStart(0))
        assertEquals(mutableListOf(0), performedCommandStorage.elements)
    }

    @Test
    fun cancelLastAction() {
        val performedCommandStorage = PerformedCommandStorage()
        performedCommandStorage.makeAction(InsertToStart(0))
        performedCommandStorage.makeAction(InsertToEnd(1))
        performedCommandStorage.cancelLastAction()
        assertEquals(mutableListOf(0), performedCommandStorage.elements)
    }

}