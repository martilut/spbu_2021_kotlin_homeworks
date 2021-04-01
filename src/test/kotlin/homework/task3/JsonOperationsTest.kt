package homework.task3

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Path

internal class JsonOperationsTest {

    @Test
    fun loadFromJson() {
        val jsonFile = File(javaClass.getResource("loadFromJsonTest.json").path)
        val performedCommandStorage = PerformedCommandStorage()
        JsonOperations(jsonFile).loadFromJson(performedCommandStorage)
        assertEquals(mutableListOf(1, 2), performedCommandStorage.elements)
    }

    @Test
    fun saveToJson(@TempDir tempDir: Path) {
        val expectedFile = File(javaClass.getResource("saveToJsonTest.json").path)
        val tempFile = File(tempDir.resolve("tempFile.json").toString())
        val performedCommandStorage = PerformedCommandStorage()
        performedCommandStorage.makeAction(InsertToStart(1))
        performedCommandStorage.makeAction(InsertToEnd(2))
        JsonOperations(tempFile).saveToJson(performedCommandStorage)
        assertEquals(expectedFile.readText(), tempFile.readText())
        tempFile.delete()
    }
}
