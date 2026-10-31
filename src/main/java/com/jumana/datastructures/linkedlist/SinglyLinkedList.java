package com.jumana.datastructures.linkedlist;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * A singly linked list with references to both the first node ({@code head}, a.k.a. START)
 * and the last node ({@code tail}, a.k.a. END).
 *
 * <table border="1">
 *   <caption>Time complexity</caption>
 *   <tr><th>Operation</th><th>Cost</th></tr>
 *   <tr><td>addFirst / addLast / removeFirst</td><td>O(1)</td></tr>
 *   <tr><td>removeLast</td><td>O(n) &mdash; must walk to the node before the tail</td></tr>
 *   <tr><td>remove(value) / contains</td><td>O(n)</td></tr>
 * </table>
 *
 * @param <T> the type of elements stored
 */
public class SinglyLinkedList<T> {

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    /** Adds an element at the beginning of the list. O(1). */
    public void addFirst(T value) {
        Node<T> node = new Node<>(value);
        node.next = head;
        head = node;
        if (tail == null) {
            tail = node;
        }
        size++;
    }

    /** Adds an element at the end of the list. O(1). */
    public void addLast(T value) {
        Node<T> node = new Node<>(value);
        if (head == null) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;
    }

    /**
     * Removes and returns the first element. O(1).
     *
     * @throws NoSuchElementException if the list is empty
     */
    public T removeFirst() {
        if (head == null) {
            throw new NoSuchElementException("List is empty");
        }
        T value = head.data;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
        return value;
    }

    /**
     * Removes and returns the last element. O(n).
     *
     * @throws NoSuchElementException if the list is empty
     */
    public T removeLast() {
        if (head == null) {
            throw new NoSuchElementException("List is empty");
        }
        if (head == tail) {
            return removeFirst();
        }
        Node<T> ptr = head;
        while (ptr.next != tail) {
            ptr = ptr.next;
        }
        T value = tail.data;
        ptr.next = null;
        tail = ptr;
        size--;
        return value;
    }

    /**
     * Removes the first occurrence of {@code value}. O(n).
     *
     * @return {@code true} if the value was found and removed
     */
    public boolean remove(T value) {
        if (head == null) {
            return false;
        }
        if (Objects.equals(head.data, value)) {
            removeFirst();
            return true;
        }
        Node<T> ptr = head;
        while (ptr.next != null && !Objects.equals(ptr.next.data, value)) {
            ptr = ptr.next;
        }
        if (ptr.next == null) {
            return false;
        }
        ptr.next = ptr.next.next;
        if (ptr.next == null) {
            tail = ptr;
        }
        size--;
        return true;
    }

    /** @return {@code true} if the list contains {@code value}. O(n). */
    public boolean contains(T value) {
        for (Node<T> ptr = head; ptr != null; ptr = ptr.next) {
            if (Objects.equals(ptr.data, value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the first element
     * @throws NoSuchElementException if the list is empty
     */
    public T getFirst() {
        if (head == null) {
            throw new NoSuchElementException("List is empty");
        }
        return head.data;
    }

    /**
     * @return the last element
     * @throws NoSuchElementException if the list is empty
     */
    public T getLast() {
        if (tail == null) {
            throw new NoSuchElementException("List is empty");
        }
        return tail.data;
    }

    /** @return the number of elements in the list */
    public int size() {
        return size;
    }

    /** @return {@code true} if the list has no elements */
    public boolean isEmpty() {
        return size == 0;
    }

    /** @return the elements from first to last, e.g. {@code [1, 2, 3]} */
    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        for (Node<T> ptr = head; ptr != null; ptr = ptr.next) {
            joiner.add(String.valueOf(ptr.data));
        }
        return joiner.toString();
    }
}
