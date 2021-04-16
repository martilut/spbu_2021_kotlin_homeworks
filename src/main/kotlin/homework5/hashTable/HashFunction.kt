package homework5.hashTable

import kotlin.math.abs

interface HashFunction<K> {
    val name: String
    fun getHash(key: K): Int
}

class DefaultHashFunction : HashFunction<String> {
    override val name: String
        get() = "default"
    override fun getHash(key: String): Int {
        var hash = 0
        for (symbol in key) {
            hash = hash * 2 + (symbol - 'a')
        }
        return abs(hash)
    }
}

class ReverseHashFunction : HashFunction<String> {
    override val name: String
        get() = "reverse"
    override fun getHash(key: String): Int {
        var hash = 0
        for (symbol in key.reversed()) {
            hash = hash * 2 + (symbol - 'a')
        }
        return abs(hash)
    }
}
