package com.jumana.datastructures.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * A binary tree filled in level order: each new value goes into the first empty spot,
 * scanning top to bottom and left to right. This keeps the tree <em>complete</em>
 * (as balanced as possible).
 *
 * <p>Note: this is not a binary <em>search</em> tree &mdash; values are not kept in sorted order.
 *
 * <table border="1">
 *   <caption>Time complexity</caption>
 *   <tr><th>Operation</th><th>Cost</th></tr>
 *   <tr><td>insert</td><td>O(n) &mdash; breadth-first search for the first empty spot</td></tr>
 *   <tr><td>traversals</td><td>O(n)</td></tr>
 * </table>
 *
 * @param <T> the type of values stored
 */
public class BinaryTree<T> {

    private static class Node<T> {
        T value;
        Node<T> left;
        Node<T> right;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root;
    private int size;

    /** Inserts a value at the first free position in level order. O(n). */
    public void insert(T value) {
        Node<T> newNode = new Node<>(value);
        size++;

        if (root == null) {
            root = newNode;
            return;
        }

        Queue<Node<T>> queue = new ArrayDeque<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node<T> current = queue.poll();

            if (current.left == null) {
                current.left = newNode;
                return;
            }
            queue.add(current.left);

            if (current.right == null) {
                current.right = newNode;
                return;
            }
            queue.add(current.right);
        }
    }

    /** @return values in pre-order: node, left subtree, right subtree */
    public List<T> preOrder() {
        List<T> result = new ArrayList<>();
        preOrder(root, result);
        return result;
    }

    /** @return values in in-order: left subtree, node, right subtree */
    public List<T> inOrder() {
        List<T> result = new ArrayList<>();
        inOrder(root, result);
        return result;
    }

    /** @return values in post-order: left subtree, right subtree, node */
    public List<T> postOrder() {
        List<T> result = new ArrayList<>();
        postOrder(root, result);
        return result;
    }

    /** @return values level by level, left to right (breadth-first) */
    public List<T> levelOrder() {
        List<T> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Queue<Node<T>> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node<T> current = queue.poll();
            result.add(current.value);
            if (current.left != null) queue.add(current.left);
            if (current.right != null) queue.add(current.right);
        }
        return result;
    }

    /** @return the number of values in the tree */
    public int size() {
        return size;
    }

    /** @return {@code true} if the tree has no values */
    public boolean isEmpty() {
        return size == 0;
    }

    private void preOrder(Node<T> node, List<T> out) {
        if (node == null) return;
        out.add(node.value);
        preOrder(node.left, out);
        preOrder(node.right, out);
    }

    private void inOrder(Node<T> node, List<T> out) {
        if (node == null) return;
        inOrder(node.left, out);
        out.add(node.value);
        inOrder(node.right, out);
    }

    private void postOrder(Node<T> node, List<T> out) {
        if (node == null) return;
        postOrder(node.left, out);
        postOrder(node.right, out);
        out.add(node.value);
    }
}
