package homework.task3.storage

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.io.TempDir
import homework.task3.actions.InsertToEnd
import homework.task3.actions.InsertToStart
import homework.task3.actions.MoveElement
import homework.task3.storage.PerformedCommandStorage.IntSerialize.loadFromJson
import homework.task3.storage.PerformedCommandStorage.IntSerialize.saveToJson
import java.io.File
import java.nio.file.Path

internal class PerformedCommandStorageTest {

    private val storageInt = PerformedCommandStorage<Int>()
    private val storageString = PerformedCommandStorage<String>()

    @Test
    fun makeActionInt() {
        storageInt.makeAction(InsertToStart(1))
        storageInt.makeAction(InsertToEnd(2))
        storageInt.makeAction(MoveElement(1, 0))
        storageInt.makeAction(InsertToEnd(4))
        storageInt.makeAction(InsertToStart(3))
        storageInt.makeAction(MoveElement(2, 3))
        assertEquals(listOf(3, 2, 4, 1), storageInt.elements)
    }

    @Test
    fun makeActionString() {
        storageString.makeAction(InsertToStart("A"))
        storageString.makeAction(InsertToEnd("B"))
        storageString.makeAction(MoveElement(1, 0))
        storageString.makeAction(InsertToEnd("C"))
        storageString.makeAction(InsertToStart("D"))
        storageString.makeAction(MoveElement(2, 3))
        assertEquals(listOf("D", "B", "C", "A"), storageString.elements)
    }

    @Test
    fun cancelLastActionInt() {
        storageInt.makeAction(InsertToStart(1))
        storageInt.makeAction(InsertToEnd(2))
        storageInt.makeAction(MoveElement(1, 0))
        storageInt.makeAction(InsertToEnd(4))
        storageInt.cancelLastAction()
        storageInt.makeAction(InsertToStart(3))
        storageInt.makeAction(MoveElement(2, 1))
        storageInt.cancelLastAction()
        assertEquals(listOf(3, 2, 1), storageInt.elements)
    }

    @Test
    fun cancelLastActionString() {
        storageString.makeAction(InsertToStart("A"))
        storageString.makeAction(InsertToEnd("B"))
        storageString.makeAction(MoveElement(1, 0))
        storageString.makeAction(InsertToEnd("C"))
        storageString.cancelLastAction()
        storageString.makeAction(InsertToStart("D"))
        storageString.makeAction(MoveElement(2, 1))
        storageString.cancelLastAction()
        assertEquals(listOf("D", "B", "A"), storageString.elements)
    }

    @Test
    fun loadFromJsonTest() {
        val jsonFile = File(javaClass.getResource("loadFromJsonTest.json").path)
        storageInt.loadFromJson(jsonFile)
        assertEquals(mutableListOf(1, 2, 3), storageInt.elements)
    }

    @Test
    fun saveToJsonTest(@TempDir tempDir: Path) {
        val expectedFile = File(javaClass.getResource("saveToJsonTest.json").path)
        val tempFile = File(tempDir.resolve("tempFile.json").toString())
        storageInt.makeAction(InsertToStart(1))
        storageInt.makeAction(InsertToEnd(2))
        storageInt.makeAction(InsertToEnd(3))
        storageInt.saveToJson(tempFile)
        assertEquals(expectedFile.readText(), tempFile.readText())
        tempFile.delete()
    }
}
