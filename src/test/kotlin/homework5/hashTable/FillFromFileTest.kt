package homework5.hashTable

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import util.parseString
import java.io.File

internal class FillFromFileTest {

    @Test
    fun performAction() {
        val hashTable = HashTable<String, Double>(DefaultHashFunction())
        val fileData = parseString(File(javaClass.getResource("inputData.txt").path).readText())
        fileData.forEach {
            hashTable.add(it.first, it.second)
        }
        fileData.forEach { assertEquals(it.second, hashTable.getHashValue(it.first)) }
    }
}