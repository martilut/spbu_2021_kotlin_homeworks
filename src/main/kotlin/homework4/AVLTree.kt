package homework4

class AVLTree<K : Comparable<K>, V> : Map<K, V> {
    private var root: AVLTreeNode<K, V>? = null
    private var _size = 0
    override val entries: Set<Map.Entry<K, V>>
        get() {
            val entries = mutableSetOf<AVLTreeNode<K, V>>()
            root?.getEntries(entries) ?: emptySet<AVLTreeNode<K, V>>()
            return entries
        }
    override val keys: Set<K>
        get() = entries.map { it.key }.toSet()
    override val size: Int
        get() = _size
    override val values: Collection<V>
        get() = entries.map { it.value }

    override fun get(key: K): V? = this.root?.getRecursive(key)?.value

    override fun containsKey(key: K): Boolean = (this.root?.getRecursive(key) != null)

    override fun containsValue(value: V): Boolean = this.root?.containsValueRecursive(value) ?: false

    override fun isEmpty(): Boolean = this.root == null

    fun addNode(keyToAdd: K, valueToAdd: V): AVLTreeNode<K, V>? {
        val addNodeAttempt: AVLTreeNode<K, V>?
        return when (this.root) {
            null -> {
                this.root = AVLTreeNode(keyToAdd, valueToAdd)
                this._size++
                null
            }
            else -> {
                addNodeAttempt = this.root?.addNodeRecursive(keyToAdd, valueToAdd)
                if (addNodeAttempt == null) {
                    this.root = this.root?.balance()
                    this._size++
                    null
                } else {
                    addNodeAttempt
                }
            }
        }
    }

    fun remove(key: K) {
        root = root?.removeNodeRecursive(key)
        root?.balance()
    }
}
