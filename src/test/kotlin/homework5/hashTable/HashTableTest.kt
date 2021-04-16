package homework5.hashTable

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

internal class HashTableTest {

    @Test
    fun addTest() {
        val hashTable = HashTable<String, Int>(DefaultHashFunction())
        val pairs = listOf(Pair("a", 1), Pair("b", 2), Pair("c", 3), Pair("d", 4), Pair("e", 5))
        pairs.forEach { hashTable.add(it.first, it.second) }
        pairs.forEach { assertEquals(it.second, hashTable.getHashValue(it.first)) }
    }

    @Test
    fun containsTest() {
        val hashTable = HashTable<String, Int>(DefaultHashFunction())
        val pairs = listOf(Pair("a", 1), Pair("b", 2), Pair("c", 3), Pair("d", 4), Pair("e", 5))
        pairs.forEach {
            if (it.second % 2 != 0) {
                hashTable.add(it.first, it.second)
            }
        }
        pairs.forEach { assertEquals((it.second % 2 != 0), hashTable.contains(it.first)) }
    }

    @Test
    fun removeTest() {
        val hashTable = HashTable<String, Int>(DefaultHashFunction())
        val pairs = listOf(Pair("a", 1), Pair("b", 2), Pair("c", 3), Pair("d", 4), Pair("e", 5))
        pairs.forEach { hashTable.add(it.first, it.second) }
        pairs.forEach {
            if (it.second % 2 == 0) {
                hashTable.remove(it.first)
            }
        }
        pairs.forEach { assertEquals((it.second % 2 != 0), hashTable.contains(it.first)) }
    }

    @Test
    fun changeHashFunction() {
        val hashTable = HashTable<String, Int>(DefaultHashFunction())
        val pairs = listOf(Pair("a", 1), Pair("b", 2), Pair("c", 3), Pair("d", 4), Pair("e", 5))
        pairs.forEach { hashTable.add(it.first, it.second) }
        hashTable.changeHashFunction(ReverseHashFunction())
        pairs.forEach { assertEquals(hashTable.getHashValue(it.first), it.second) }
    }
}
