package com.jumana.datastructures.linkedlist;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class SinglyLinkedListTest {

    @Test
    void addFirstAndAddLast() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.addLast(2);
        list.addFirst(1);
        list.addLast(3);
        assertEquals("[1, 2, 3]", list.toString());
        assertEquals(1, list.getFirst());
        assertEquals(3, list.getLast());
        assertEquals(3, list.size());
    }

    @Test
    void removeFirstAndRemoveLast() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        assertEquals(1, list.removeFirst());
        assertEquals(3, list.removeLast());
        assertEquals(2, list.getFirst());
        assertEquals(2, list.getLast());
        assertEquals(2, list.removeLast());
        assertTrue(list.isEmpty());
    }

    @Test
    void removingOnlyElementResetsHeadAndTail() {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        list.addFirst("x");
        list.removeFirst();
        list.addLast("y"); // would fail if tail was left pointing at the old node
        assertEquals("y", list.getFirst());
        assertEquals("y", list.getLast());
    }

    @Test
    void removeByValue() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        assertTrue(list.remove(3)); // removing the tail updates it
        assertEquals(2, list.getLast());
        assertTrue(list.remove(1));
        assertFalse(list.remove(99));
        assertEquals("[2]", list.toString());
    }

    @Test
    void contains() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.addLast(5);
        assertTrue(list.contains(5));
        assertFalse(list.contains(6));
    }

    @Test
    void emptyListOperationsThrow() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        assertThrows(NoSuchElementException.class, list::removeFirst);
        assertThrows(NoSuchElementException.class, list::removeLast);
        assertThrows(NoSuchElementException.class, list::getFirst);
        assertFalse(list.remove(1));
    }
}
