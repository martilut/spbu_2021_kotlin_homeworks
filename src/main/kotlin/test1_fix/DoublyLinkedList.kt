package test1_fix

class DoublyLinkedList<T>() {
    data class DoublyLinkedNode<T>(val value: T) {
        var nextNode: DoublyLinkedNode<T>? = null
        var previousNode: DoublyLinkedNode<T>? = null
    }

    private var root: DoublyLinkedNode<T>? = null
    private var listSize = 0

    fun isEmpty(): Boolean = root == null

    fun add(value: T, position: Int = listSize) {
        require(position in 0..listSize) {
            "Wrong position"
        }
        val newNode = DoublyLinkedNode(value)
        when {
            isEmpty() -> root = newNode
            position == 0 -> {
                newNode.nextNode = root
                newNode.nextNode?.previousNode = newNode
                root = newNode
            }
            else -> {
                val currentNode = get(position)
                newNode.previousNode = currentNode
                when (position) {
                    listSize -> {
                        newNode.nextNode = null
                        currentNode?.nextNode = newNode
                    }
                    else -> {
                        newNode.nextNode = currentNode?.nextNode
                        newNode.nextNode?.previousNode = newNode
                        currentNode?.nextNode = newNode
                    }
                }
            }
        }
        ++listSize
    }

    fun remove(position: Int) {
        require(position in 0 until listSize && !isEmpty()) {
            "Wrong position"
        }
        when (position) {
            0 -> {
                when (listSize) {
                    1 -> root = null
                    else -> {
                        val newRoot = root?.nextNode
                        newRoot?.previousNode = null
                        root = newRoot
                    }
                }
            }
            listSize - 1 -> {
                var currentNode = root
                while (currentNode?.nextNode != null) {
                    currentNode = currentNode.nextNode
                }
                val newLastNode = currentNode?.previousNode
                newLastNode?.nextNode = null
            }
            else -> {
                val currentNode = get(position)
                currentNode?.nextNode = currentNode?.nextNode?.nextNode
                currentNode?.nextNode?.previousNode = currentNode
            }
        }
        --listSize
    }

    fun get(position: Int = 0): DoublyLinkedNode<T>? {
        require(position in 0..listSize) {
            "Wrong position"
        }
        var currentNode = root
        repeat(position - 1) {
            currentNode = currentNode?.nextNode
        }
        return currentNode
    }

    fun getList(): List<T> {
        val list = mutableListOf<T>()
        var currentNode = root
        while (currentNode != null) {
            list.add(currentNode.value)
            currentNode = currentNode.nextNode
        }
        return list
    }
}
