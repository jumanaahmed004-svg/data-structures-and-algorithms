package com.jumana.datastructures.array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DynamicArrayTest {

    @Test
    void newListIsEmpty() {
        DynamicArray<Integer> list = new DynamicArray<>();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
        assertEquals("[]", list.toString());
    }

    @Test
    void addAndGetKeepOrder() {
        DynamicArray<String> list = new DynamicArray<>();
        list.add("a");
        list.add("b");
        list.add("c");
        assertEquals(3, list.size());
        assertEquals("a", list.get(0));
        assertEquals("c", list.get(2));
    }

    @Test
    void growsBeyondInitialCapacity() {
        DynamicArray<Integer> list = new DynamicArray<>(2);
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        assertEquals(100, list.size());
        assertEquals(99, list.get(99));
    }

    @Test
    void insertShiftsElementsRight() {
        DynamicArray<Integer> list = new DynamicArray<>();
        list.add(1);
        list.add(3);
        list.insert(1, 2);
        list.insert(0, 0);
        list.insert(4, 4); // inserting at size() appends
        assertEquals("[0, 1, 2, 3, 4]", list.toString());
    }

    @Test
    void removeShiftsElementsLeft() {
        DynamicArray<Integer> list = new DynamicArray<>();
        list.add(10);
        list.add(20);
        list.add(30);
        assertEquals(20, list.remove(1));
        assertEquals("[10, 30]", list.toString());
    }

    @Test
    void setReplacesElement() {
        DynamicArray<Integer> list = new DynamicArray<>();
        list.add(1);
        assertEquals(1, list.set(0, 5));
        assertEquals(5, list.get(0));
    }

    @Test
    void clearEmptiesList() {
        DynamicArray<Integer> list = new DynamicArray<>();
        list.add(1);
        list.add(2);
        list.clear();
        assertTrue(list.isEmpty());
    }

    @Test
    void invalidIndexThrows() {
        DynamicArray<Integer> list = new DynamicArray<>();
        list.add(1);
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(5));
        assertThrows(IndexOutOfBoundsException.class, () -> list.insert(3, 9));
    }

    @Test
    void nonPositiveCapacityThrows() {
        assertThrows(IllegalArgumentException.class, () -> new DynamicArray<Integer>(0));
    }
}
