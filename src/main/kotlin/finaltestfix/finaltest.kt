package finaltestfix

/**
 * Function compresses given ByteArray
 * replacing duplicates with two bytes:
 * element count and element itself
 */
fun ByteArray.compress(): ByteArray {
    val listByteCount = mutableListOf<Pair<Byte, Int>>()
    for (byte in this) {
        val newMap = Pair(byte, this.count { it == byte })
        if (!listByteCount.contains(newMap)) {
            listByteCount.add(newMap)
        }
    }
    val newByteList = mutableListOf<Byte>()
    for (byte in listByteCount) {
        newByteList.add(byte.second.toByte())
        newByteList.add(byte.first)
    }
    return newByteList.toByteArray()
}

/**
 * Function decompresses given
 * compressed ByteArray
 */
fun ByteArray.decompress(): ByteArray {
    val newByteList = mutableListOf<Byte>()
    var index = 0
    while (index < this.size - 1) {
        repeat(this[index].toInt()) {
            newByteList.add(this[index + 1])
        }
        index += 2
    }
    return newByteList.toByteArray()
}
