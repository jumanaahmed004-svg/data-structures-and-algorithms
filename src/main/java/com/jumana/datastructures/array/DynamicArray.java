package com.jumana.datastructures.array;

import java.util.Arrays;

/**
 * A resizable array list, similar to {@link java.util.ArrayList}, built on a plain array.
 *
 * <p>Elements are stored in positions {@code 0 .. size-1}. When the array is full,
 * its capacity is doubled, so adding at the end is fast on average.
 *
 * <table border="1">
 *   <caption>Time complexity</caption>
 *   <tr><th>Operation</th><th>Cost</th></tr>
 *   <tr><td>add (at end)</td><td>O(1) amortized</td></tr>
 *   <tr><td>insert / remove (at index)</td><td>O(n)</td></tr>
 *   <tr><td>get / set</td><td>O(1)</td></tr>
 * </table>
 *
 * @param <T> the type of elements stored
 */
public class DynamicArray<T> {

    private static final int DEFAULT_CAPACITY = 10;

    private Object[] data;
    private int size;

    /** Creates an empty list with a default capacity of 10. */
    public DynamicArray() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Creates an empty list with the given starting capacity.
     *
     * @param initialCapacity starting size of the internal array (must be positive)
     * @throws IllegalArgumentException if {@code initialCapacity <= 0}
     */
    public DynamicArray(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive: " + initialCapacity);
        }
        data = new Object[initialCapacity];
    }

    /**
     * Adds an element to the end of the list. O(1) amortized.
     *
     * @param element the element to add
     */
    public void add(T element) {
        growIfFull();
        data[size++] = element;
    }

    /**
     * Inserts an element at {@code index}, shifting later elements one step right. O(n).
     *
     * @param index position to insert at, from {@code 0} to {@code size()} inclusive
     * @param element the element to insert
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public void insert(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        growIfFull();
        for (int i = size - 1; i >= index; i--) {
            data[i + 1] = data[i];
        }
        data[index] = element;
        size++;
    }

    /**
     * Removes the element at {@code index}, shifting later elements one step left. O(n).
     *
     * @param index position of the element to remove
     * @return the removed element
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public T remove(int index) {
        checkIndex(index);
        T removed = elementAt(index);
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        data[--size] = null; // let the garbage collector reclaim the old reference
        return removed;
    }

    /**
     * Returns the element at {@code index}. O(1).
     *
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public T get(int index) {
        checkIndex(index);
        return elementAt(index);
    }

    /**
     * Replaces the element at {@code index}. O(1).
     *
     * @return the element previously at that position
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public T set(int index, T element) {
        checkIndex(index);
        T old = elementAt(index);
        data[index] = element;
        return old;
    }

    /** Removes all elements. */
    public void clear() {
        Arrays.fill(data, 0, size, null);
        size = 0;
    }

    /** @return the number of elements in the list */
    public int size() {
        return size;
    }

    /** @return {@code true} if the list has no elements */
    public boolean isEmpty() {
        return size == 0;
    }

    /** @return the elements in order, e.g. {@code [1, 2, 3]} */
    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOf(data, size));
    }

    private void growIfFull() {
        if (size == data.length) {
            data = Arrays.copyOf(data, data.length * 2);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    @SuppressWarnings("unchecked")
    private T elementAt(int index) {
        return (T) data[index];
    }
}
