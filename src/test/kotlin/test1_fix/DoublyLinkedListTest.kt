package test1_fix

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class DoublyLinkedListTest {

    @Test
    fun isEmptyInt() {
        val firstList = DoublyLinkedList<Int>()
        val secondList = DoublyLinkedList<Int>()
        firstList.add(1)
        secondList.add(1)
        secondList.remove(0)
        assertEquals(setOf(false, true), setOf(firstList.isEmpty(), secondList.isEmpty()))
    }

    @Test
    fun isEmptyDouble() {
        val firstList = DoublyLinkedList<Double>()
        val secondList = DoublyLinkedList<Double>()
        firstList.add(1.42)
        secondList.add(3.0001)
        secondList.remove(0)
        assertEquals(setOf(false, true), setOf(firstList.isEmpty(), secondList.isEmpty()))
    }

    @Test
    fun isEmptyString() {
        val firstList = DoublyLinkedList<String>()
        val secondList = DoublyLinkedList<String>()
        firstList.add("first")
        secondList.add("second")
        secondList.remove(0)
        assertEquals(setOf(false, true), setOf(firstList.isEmpty(), secondList.isEmpty()))
    }

    private fun DoublyLinkedList<Int>.addInt() {
        this.add(1)
        this.add(2)
        this.add(3)
        this.add(4)
        this.add(0, 0)
        this.add(5, 5)
        this.add(7, 3)
    }

    @Test
    fun addIntTest() {
        val list = DoublyLinkedList<Int>()
        list.addInt()
        assertEquals(listOf(0, 1, 2, 7, 3, 4, 5), list.getList())
    }

    private fun DoublyLinkedList<Double>.addDouble() {
        this.add(1.12)
        this.add(0.001, 0)
        this.add(5.15, 2)
        this.add(7.56, 1)
    }

    @Test
    fun addDoubleTest() {
        val list = DoublyLinkedList<Double>()
        list.addDouble()
        assertEquals(listOf(0.001, 7.56, 1.12, 5.15), list.getList())
    }

    fun DoublyLinkedList<String>.addString() {
        this.add("one")
        this.add("two")
        this.add("three", 0)
        this.add("four", 1)
        this.add("five", 4)
    }

    @Test
    fun addStringTest() {
        val list = DoublyLinkedList<String>()
        list.addString()
        assertEquals(listOf("three", "four", "one", "two", "five"), list.getList())
    }

    @Test
    fun removeInt() {
        val list = DoublyLinkedList<Int>()
        list.addInt()
        list.remove(0)
        list.remove(5)
        list.remove(2)
        assertEquals(listOf(1, 2, 3, 4), list.getList())
    }

    @Test
    fun removeDouble() {
        val list = DoublyLinkedList<Double>()
        list.addDouble()
        list.remove(2)
        list.remove(1)
        list.remove(0)
        list.remove(0)
        assertEquals(listOf<Double>(), list.getList())
    }

    @Test
    fun removeString() {
        val list = DoublyLinkedList<String>()
        list.addString()
        list.remove(4)
        list.remove(0)
        list.remove(1)
        assertEquals(listOf("four", "two"), list.getList())
    }

    @Test
    fun getInt() {
        val list = DoublyLinkedList<Int>()
        list.addInt()
        assertEquals(setOf(0, 3, 2), setOf(list.get(0)!!.value, list.get(5)!!.value, list.get(3)!!.value))
    }

    @Test
    fun getDouble() {
        val list = DoublyLinkedList<Double>()
        list.addDouble()
        assertEquals(setOf(0.001, 1.12, 7.56), setOf(list.get(0)!!.value, list.get(3)!!.value, list.get(2)!!.value))
    }

    @Test
    fun getString() {
        val list = DoublyLinkedList<String>()
        list.addString()
        assertEquals(setOf("three", "five", "four"), setOf(list.get(0)!!.value, list.get(5)!!.value, list.get(2)!!.value))
    }
}