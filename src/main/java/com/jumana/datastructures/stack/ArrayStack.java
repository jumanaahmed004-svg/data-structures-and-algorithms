package com.jumana.datastructures.stack;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * A last-in, first-out (LIFO) stack backed by a resizable array.
 *
 * <p>{@code top} is the index of the most recently pushed element ({@code -1} when empty).
 * When the array is full, its capacity is doubled.
 *
 * <table border="1">
 *   <caption>Time complexity</caption>
 *   <tr><th>Operation</th><th>Cost</th></tr>
 *   <tr><td>push</td><td>O(1) amortized</td></tr>
 *   <tr><td>pop / peek / isEmpty</td><td>O(1)</td></tr>
 * </table>
 *
 * @param <T> the type of elements stored
 */
public class ArrayStack<T> {

    private static final int DEFAULT_CAPACITY = 10;

    private Object[] stack = new Object[DEFAULT_CAPACITY];
    private int top = -1;

    /** Pushes an element onto the top of the stack. O(1) amortized. */
    public void push(T value) {
        if (top == stack.length - 1) {
            stack = Arrays.copyOf(stack, stack.length * 2);
        }
        stack[++top] = value;
    }

    /**
     * Removes and returns the top element. O(1).
     *
     * @throws NoSuchElementException if the stack is empty
     */
    public T pop() {
        T value = peek();
        stack[top--] = null; // let the garbage collector reclaim the old reference
        return value;
    }

    /**
     * Returns the top element without removing it. O(1).
     *
     * @throws NoSuchElementException if the stack is empty
     */
    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return (T) stack[top];
    }

    /** @return {@code true} if the stack has no elements */
    public boolean isEmpty() {
        return top == -1;
    }

    /** @return the number of elements on the stack */
    public int size() {
        return top + 1;
    }

    /** @return the elements from bottom to top, e.g. {@code [1, 2, 3]} (3 is on top) */
    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOf(stack, size()));
    }
}
