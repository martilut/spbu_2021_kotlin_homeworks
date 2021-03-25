package homework.task3

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import java.io.File

internal class JsonOperationsTest {

    @Test
    fun loadFromJson() {
        val jsonFile = File("src/test/resources/inputFile.json")
        val performedCommandStorage = PerformedCommandStorage()
        jsonFile.writeText("[{\"type\":\"InsertToStart\",\"value\":1},{\"type\":\"InsertToEnd\",\"value\":2}]")
        JsonOperations(jsonFile).loadFromJson(performedCommandStorage)
        assertEquals(mutableListOf(1, 2), performedCommandStorage.elements)
    }

    @Test
    fun saveToJson() {
        val jsonFile = File("src/test/resources/inputFile.json")
        val performedCommandStorage = PerformedCommandStorage()
        performedCommandStorage.makeAction(InsertToStart(1))
        performedCommandStorage.makeAction(InsertToEnd(2))
        JsonOperations(jsonFile).saveToJson(performedCommandStorage)
        assertEquals("[{\"type\":\"InsertToStart\",\"value\":1},{\"type\":\"InsertToEnd\",\"value\":2}]", jsonFile.readText())
    }
}
