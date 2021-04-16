package homework5.hashTable

import util.parseString
import util.scanLine
import java.io.File

interface Action {
    val name: String
    fun performAction(hashTable: HashTable<String, Double>)
}

class InsertElement : Action {
    override val name: String
        get() = "Insert element"

    override fun performAction(hashTable: HashTable<String, Double>) {
        val key = scanLine("Enter key: ")
        val value = scanLine("Enter value: ").toDoubleOrNull()
        if (value != null) {
            hashTable.add(key, value)
        } else {
            println("Value is incorrect")
        }
    }
}

class RemoveElement : Action {
    override val name: String
        get() = "Remove element"

    override fun performAction(hashTable: HashTable<String, Double>) {
        val key = scanLine("Enter key: ")
        if (!hashTable.remove(key)) {
            println("There is no such element in the hash table")
        }
    }
}

class FindElement : Action {
    override val name: String
        get() = "Find element"

    override fun performAction(hashTable: HashTable<String, Double>) {
        val key = scanLine("Enter key: ")
        if (hashTable.contains(key)) {
            println(hashTable.getHashValue(key))
        } else {
            println("There is no such element in the hash table")
        }
    }
}

class OutputStatistics : Action {
    override val name: String
        get() = "Output statistics"

    override fun performAction(hashTable: HashTable<String, Double>) {
        val loadFactor = hashTable.loadFactor
        val data = hashTable.getStatistics()
        println("Load factor: $loadFactor\n" +
                "Element count: ${data[0]}\n" +
                "Bucket count: ${data[1]}\n" +
                "Conflicts count: ${data[2]}\n" +
                "Maximal bucket size: ${data[data.size - 1]}\n")
    }
}

class SelectHashFunction : Action {
    override val name: String
        get() = "Select hash function"

    override fun performAction(hashTable: HashTable<String, Double>) {
        println("Type ${DefaultHashFunction().name} to use default function\n" +
                "Type ${ReverseHashFunction().name} to use reverse function")
        when (scanLine("Enter hash function name: ")) {
            "default" -> hashTable.changeHashFunction(DefaultHashFunction())
            "reverse" -> hashTable.changeHashFunction(ReverseHashFunction())
            else -> println("Name is incorrect")
        }
    }
}

class FillFromFile : Action {
    override val name: String
        get() = "Fill from file"

    override fun performAction(hashTable: HashTable<String, Double>) {
        val fileName = scanLine("Enter file name: ")
        val fileData = File(javaClass.getResource(fileName).path).readText()
        val pairsList = parseString(fileData)
        pairsList.forEach {
            hashTable.add(it.first, it.second)
        }
    }
}
