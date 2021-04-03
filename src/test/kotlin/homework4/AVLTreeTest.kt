package homework4

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class AVLTreeTest {

    private val tree = AVLTree<Int, String>()

    @Test
    fun getSize() {
        tree.addNode(1, "First")
        tree.addNode(2, "Second")
        tree.addNode(3, "Third")
        tree.addNode(3, "Another third")
        assertEquals(3, tree.size)
    }

    @Test
    fun get() {
        tree.addNode(14, "UwU")
        tree.addNode(18, "oWo")
        tree.addNode(16, "0w0")
        assertEquals(setOf("oWo", "UwU", "0w0"), setOf(tree[18], tree[14], tree[16]))
    }

    @Test
    fun containsKey() {
        tree.addNode(725, "Just a value")
        tree.addNode(8, "Just another value")
        assertEquals(setOf(true, false, true),
            setOf(tree.containsKey(8), tree.containsKey(24), tree.containsKey(725)))
    }

    @Test
    fun containsValue() {
        tree.addNode(1, "A")
        tree.addNode(4, "C")
        assertEquals(setOf(true, false, true),
            setOf(tree.containsValue("A"), tree.containsValue("B"), tree.containsValue("C")))
    }

    @Test
    fun isEmpty() {
        assertTrue(tree.isEmpty())
    }

    @Test
    fun addNode() {
        tree.addNode(5, "Romania")
        tree.addNode(3, "Serbia")
        tree.addNode(7, "Poland")
        assertEquals(setOf(Pair(5, "Romania"), Pair(3, "Serbia"), Pair(7, "Poland")),
            tree.entries.map { Pair(it.key, it.value) }.toSet())
    }

    @Test
    fun removeNode() {
        tree.addNode(1, "Hello there")
        tree.addNode(2, "General Kenobi")
        tree.addNode(3, "!")
        tree.remove(1)
        assertEquals(setOf(Pair(2, "General Kenobi"), Pair(3, "!")),
            tree.entries.map { Pair(it.key, it.value) }.toSet())
    }
}
