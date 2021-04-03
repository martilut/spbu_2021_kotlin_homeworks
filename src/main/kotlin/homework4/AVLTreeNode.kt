package homework4

import kotlin.math.max

class AVLTreeNode<K : Comparable<K>, V>(private val nodeKey: K, private var nodeValue: V) : Map.Entry<K, V> {

    override val key: K
        get() = nodeKey
    override val value: V
        get() = nodeValue

    private var leftChild: AVLTreeNode<K, V>? = null
    private var rightChild: AVLTreeNode<K, V>? = null
    private val height: Int
        get() = max((leftChild?.height ?: 0), (rightChild?.height ?: 0)) + 1
    private val balanceFactor: Int
        get() = (rightChild?.height ?: 0) - (leftChild?.height ?: 0)

    private fun rotateRight(): AVLTreeNode<K, V>? {
        val pivot = this.leftChild
        this.leftChild = pivot?.rightChild
        pivot?.rightChild = this
        return pivot
    }

    private fun rotateLeft(): AVLTreeNode<K, V>? {
        val pivot = this.rightChild
        this.rightChild = pivot?.leftChild
        pivot?.leftChild = this
        return pivot
    }

    fun balance(): AVLTreeNode<K, V>? {
        this.leftChild = this.leftChild?.balance()
        this.rightChild = this.rightChild?.balance()
        return when {
            this.balanceFactor > 1 -> {
                if ((this.rightChild?.balanceFactor ?: 0) < 0) {
                    this.rightChild = this.rightChild?.rotateRight()
                }
                this.rotateLeft()
            }
            this.balanceFactor < -1 -> {
                if ((this.leftChild?.balanceFactor ?: 0) > 0) {
                    this.leftChild = this.leftChild?.rotateLeft()
                }
                this.rotateRight()
            }
            else -> this
        }
    }

    fun getRecursive(keyToGet: K): AVLTreeNode<K, V>? {
        return when {
            keyToGet == this.nodeKey -> this
            keyToGet > this.nodeKey -> this.rightChild?.getRecursive(keyToGet)
            keyToGet < this.nodeKey -> this.leftChild?.getRecursive(keyToGet)
            else -> null
        }
    }

    fun containsValueRecursive(valueToFind: V): Boolean {
        return when {
            this.value != valueToFind -> {
                val containsLeft: Boolean = this.leftChild?.containsValueRecursive(valueToFind) ?: false
                val containsRight: Boolean = this.rightChild?.containsValueRecursive(valueToFind) ?: false
                containsLeft || containsRight
            }
            else -> true
        }
    }

    fun addNodeRecursive(keyToAdd: K, valueToAdd: V): AVLTreeNode<K, V>? {
        return when {
            keyToAdd == this.nodeKey -> {
                val previous = this
                this.nodeValue = valueToAdd
                previous
            }
            keyToAdd < this.nodeKey -> {
                if (this.leftChild == null) {
                    this.leftChild = AVLTreeNode(keyToAdd, valueToAdd)
                    null
                } else {
                    this.leftChild?.addNodeRecursive(keyToAdd, valueToAdd)
                }
            }
            keyToAdd > this.nodeKey -> {
                if (this.rightChild == null) {
                    this.rightChild = AVLTreeNode(keyToAdd, valueToAdd)
                    null
                } else {
                    this.rightChild?.addNodeRecursive(keyToAdd, valueToAdd)
                }
            }
            else -> null
        }
    }

    private fun getMinNode(): AVLTreeNode<K, V> = this.leftChild?.getMinNode() ?: this

    private fun removeMinNode(minNode: AVLTreeNode<K, V>): AVLTreeNode<K, V>? {
        return when (minNode.leftChild) {
            null -> minNode.rightChild
            else -> {
                minNode.leftChild = removeMinNode(minNode.leftChild!!)
                minNode.balance()
            }
        }
    }

    fun removeNodeRecursive(keyToRemove: K): AVLTreeNode<K, V>? {
        return when {
            keyToRemove < this.nodeKey -> {
                this.leftChild = this.leftChild?.removeNodeRecursive(keyToRemove)
                this
            }
            keyToRemove > this.nodeKey -> {
                this.rightChild = this.rightChild?.removeNodeRecursive(keyToRemove)
                this
            }
            else -> {
                when (this.rightChild) {
                    null -> this.leftChild
                    else -> {
                        val minNode = getMinNode()
                        minNode.rightChild = removeMinNode(this.rightChild!!)
                        minNode.leftChild = this.leftChild
                        minNode.balance()
                    }
                }
            }
        }
    }

    fun getEntries(entries: MutableSet<AVLTreeNode<K, V>>) {
        entries.add(this)
        this.leftChild?.getEntries(entries)
        this.rightChild?.getEntries(entries)
    }
}
