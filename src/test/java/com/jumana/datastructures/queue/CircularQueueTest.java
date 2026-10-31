package com.jumana.datastructures.queue;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class CircularQueueTest {

    @Test
    void firstInFirstOut() {
        CircularQueue<String> queue = new CircularQueue<>(5);
        queue.enqueue("Ali");
        queue.enqueue("Sara");
        queue.enqueue("Omar");
        assertEquals("Ali", queue.dequeue());
        assertEquals("Sara", queue.dequeue());
        assertEquals(1, queue.size());
    }

    @Test
    void peekDoesNotRemove() {
        CircularQueue<Integer> queue = new CircularQueue<>(3);
        queue.enqueue(1);
        assertEquals(1, queue.peek());
        assertEquals(1, queue.size());
    }

    @Test
    void wrapsAroundAndReusesSpace() {
        CircularQueue<Integer> queue = new CircularQueue<>(3);
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        assertTrue(queue.isFull());
        queue.dequeue();
        queue.dequeue();
        queue.enqueue(4); // these go into slots 0 and 1 again
        queue.enqueue(5);
        assertEquals("[3, 4, 5]", queue.toString());
        assertEquals(3, queue.dequeue());
        assertEquals(4, queue.dequeue());
        assertEquals(5, queue.dequeue());
        assertTrue(queue.isEmpty());
    }

    @Test
    void fullQueueThrows() {
        CircularQueue<Integer> queue = new CircularQueue<>(1);
        queue.enqueue(1);
        assertThrows(IllegalStateException.class, () -> queue.enqueue(2));
    }

    @Test
    void emptyQueueThrows() {
        CircularQueue<Integer> queue = new CircularQueue<>(2);
        assertThrows(NoSuchElementException.class, queue::dequeue);
        assertThrows(NoSuchElementException.class, queue::peek);
    }

    @Test
    void nonPositiveCapacityThrows() {
        assertThrows(IllegalArgumentException.class, () -> new CircularQueue<Integer>(0));
    }
}
