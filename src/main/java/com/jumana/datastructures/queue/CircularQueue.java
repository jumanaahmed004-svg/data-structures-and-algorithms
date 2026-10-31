package com.jumana.datastructures.queue;

import java.util.NoSuchElementException;
import java.util.StringJoiner;

/**
 * A first-in, first-out (FIFO) queue with a fixed capacity, stored in a circular array.
 *
 * <p>{@code front} is the index of the oldest element and {@code rear} the index of the newest.
 * Both indices wrap around to {@code 0} using {@code % capacity}, so the free space left
 * behind by dequeued elements is reused instead of wasted.
 *
 * <table border="1">
 *   <caption>Time complexity</caption>
 *   <tr><th>Operation</th><th>Cost</th></tr>
 *   <tr><td>enqueue / dequeue / peek</td><td>O(1)</td></tr>
 * </table>
 *
 * @param <T> the type of elements stored
 */
public class CircularQueue<T> {

    private final Object[] queue;
    private int front = 0;
    private int rear = -1;
    private int size = 0;

    /**
     * Creates an empty queue that can hold up to {@code capacity} elements.
     *
     * @throws IllegalArgumentException if {@code capacity <= 0}
     */
    public CircularQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive: " + capacity);
        }
        queue = new Object[capacity];
    }

    /**
     * Adds an element at the rear of the queue. O(1).
     *
     * @throws IllegalStateException if the queue is full
     */
    public void enqueue(T value) {
        if (isFull()) {
            throw new IllegalStateException("Queue is full");
        }
        rear = (rear + 1) % queue.length;
        queue[rear] = value;
        size++;
    }

    /**
     * Removes and returns the element at the front of the queue. O(1).
     *
     * @throws NoSuchElementException if the queue is empty
     */
    public T dequeue() {
        T value = peek();
        queue[front] = null; // let the garbage collector reclaim the old reference
        front = (front + 1) % queue.length;
        size--;
        return value;
    }

    /**
     * Returns the element at the front without removing it. O(1).
     *
     * @throws NoSuchElementException if the queue is empty
     */
    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return (T) queue[front];
    }

    /** @return {@code true} if the queue has no elements */
    public boolean isEmpty() {
        return size == 0;
    }

    /** @return {@code true} if the queue has reached its capacity */
    public boolean isFull() {
        return size == queue.length;
    }

    /** @return the number of elements in the queue */
    public int size() {
        return size;
    }

    /** @return the maximum number of elements the queue can hold */
    public int capacity() {
        return queue.length;
    }

    /** @return the elements from front to rear, e.g. {@code [Ali, Sara]} */
    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        for (int i = 0; i < size; i++) {
            joiner.add(String.valueOf(queue[(front + i) % queue.length]));
        }
        return joiner.toString();
    }
}
