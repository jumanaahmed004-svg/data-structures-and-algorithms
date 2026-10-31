package com.jumana.datastructures.stack;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ArrayStackTest {

    @Test
    void pushThenPopIsLastInFirstOut() {
        ArrayStack<Integer> stack = new ArrayStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void peekDoesNotRemove() {
        ArrayStack<String> stack = new ArrayStack<>();
        stack.push("a");
        assertEquals("a", stack.peek());
        assertEquals(1, stack.size());
    }

    @Test
    void growsWhenFull() {
        ArrayStack<Integer> stack = new ArrayStack<>();
        for (int i = 0; i < 50; i++) {
            stack.push(i);
        }
        assertEquals(50, stack.size());
        assertEquals(49, stack.peek());
    }

    @Test
    void toStringShowsBottomToTop() {
        ArrayStack<Integer> stack = new ArrayStack<>();
        stack.push(1);
        stack.push(2);
        assertEquals("[1, 2]", stack.toString());
    }

    @Test
    void emptyStackThrows() {
        ArrayStack<Integer> stack = new ArrayStack<>();
        assertThrows(NoSuchElementException.class, stack::pop);
        assertThrows(NoSuchElementException.class, stack::peek);
    }
}
