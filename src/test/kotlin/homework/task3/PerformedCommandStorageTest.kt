package homework.task3

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

internal class PerformedCommandStorageTest {
    @Test
    fun makeAction() {
        val performedCommandStorage = PerformedCommandStorage()
        performedCommandStorage.makeAction(InsertToStart(5))
        performedCommandStorage.makeAction(InsertToEnd(10))
        performedCommandStorage.makeAction(InsertToStart(1))
        performedCommandStorage.makeAction(MoveElement(0, 2))
        performedCommandStorage.makeAction(InsertToEnd(4))
        assertEquals(mutableListOf(5, 10, 1, 4), performedCommandStorage.elements)
    }

    @Test
    fun cancelLastAction() {
        val performedCommandStorage = PerformedCommandStorage()
        performedCommandStorage.makeAction(InsertToStart(5))
        performedCommandStorage.makeAction(InsertToEnd(1))
        performedCommandStorage.makeAction(InsertToStart(2))
        performedCommandStorage.cancelLastAction()
        performedCommandStorage.makeAction(MoveElement(1, 0))
        performedCommandStorage.makeAction(InsertToEnd(3))
        performedCommandStorage.makeAction(MoveElement(1, 2))
        performedCommandStorage.cancelLastAction()
        assertEquals(mutableListOf(1, 5, 3), performedCommandStorage.elements)
    }

}
