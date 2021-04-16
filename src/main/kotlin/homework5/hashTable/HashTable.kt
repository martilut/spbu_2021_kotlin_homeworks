package homework5.hashTable

class HashTable<K, V>(private var hashFunction: HashFunction<K>) {
    companion object {
        const val MAX_LOAD_FACTOR = 0.7
    }

    private var bucketCount = 1
    private var elementCount = 0
    val loadFactor: Double
        get() {
            return elementCount / bucketCount.toDouble()
        }

    private data class HashElement<K, V>(val key: K, var value: V)

    private var hashArray = Array(bucketCount) { mutableListOf<HashElement<K, V>>() }

    private fun expand() {
        bucketCount *= 2
        changeHashTable(bucketCount)
    }

    private fun changeHashTable(newSize: Int) {
        val newHashArray = Array(newSize) { mutableListOf<HashElement<K, V>>() }
        for (list in hashArray) {
            for (element in list) {
                val hash = hashFunction.getHash(element.key) % (newSize)
                newHashArray[hash].add(element)
            }
        }
        hashArray = newHashArray
    }

    fun contains(key: K): Boolean {
        val hash = hashFunction.getHash(key) % bucketCount
        return hashArray[hash].find { key == it.key } != null
    }

    fun add(key: K, value: V) {
        val hash = hashFunction.getHash(key) % bucketCount
        val hashElement = hashArray[hash].find { key == it.key }

        if (hashElement != null) {
            if (value != hashElement.value) {
                hashElement.value = value
            }
        } else {
            hashArray[hash].add(HashElement(key, value))
            ++elementCount
        }

        if (loadFactor >= MAX_LOAD_FACTOR) {
            this.expand()
        }
    }

    fun remove(key: K): Boolean {
        val hash = hashFunction.getHash(key) % (bucketCount)
        return when (val element = hashArray[hash].find { key == it.key }) {
            null -> false
            else -> {
                hashArray[hash].remove(element)
                --elementCount
                true
            }
        }
    }

    fun getHashValue(key: K): V? {
        val hash = hashFunction.getHash(key) % bucketCount
        return hashArray[hash].find { key == it.key }?.value
    }

    fun changeHashFunction(newHashFunction: HashFunction<K>) {
        hashFunction = newHashFunction
        changeHashTable(bucketCount)
    }

    fun getStatistics(): List<Int> {
        var maxBucketSize = 0
        var conflictsCount = 0
        for (bucket in hashArray) {
            val bucketSize = bucket.size
            when {
                bucketSize > 1 -> ++conflictsCount
                bucketSize > maxBucketSize -> maxBucketSize = bucketSize
            }
        }
        return listOf(elementCount, bucketCount, conflictsCount, maxBucketSize)
    }
}
