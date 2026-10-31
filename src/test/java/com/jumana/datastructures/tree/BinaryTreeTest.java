package com.jumana.datastructures.tree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BinaryTreeTest {

    private BinaryTree<Integer> tree;

    /*
     * Inserting 10, 6, 15, 3, 8, 20, 9 in level order builds:
     *
     *            10
     *          /    \
     *         6      15
     *        / \    /  \
     *       3   8  20   9
     */
    @BeforeEach
    void setUp() {
        tree = new BinaryTree<>();
        for (int x : new int[] {10, 6, 15, 3, 8, 20, 9}) {
            tree.insert(x);
        }
    }

    @Test
    void levelOrderMatchesInsertionOrder() {
        assertEquals(List.of(10, 6, 15, 3, 8, 20, 9), tree.levelOrder());
    }

    @Test
    void preOrder() {
        assertEquals(List.of(10, 6, 3, 8, 15, 20, 9), tree.preOrder());
    }

    @Test
    void inOrder() {
        assertEquals(List.of(3, 6, 8, 10, 20, 15, 9), tree.inOrder());
    }

    @Test
    void postOrder() {
        assertEquals(List.of(3, 8, 6, 20, 9, 15, 10), tree.postOrder());
    }

    @Test
    void size() {
        assertEquals(7, tree.size());
        assertFalse(tree.isEmpty());
    }

    @Test
    void emptyTreeHasNoValues() {
        BinaryTree<String> empty = new BinaryTree<>();
        assertTrue(empty.isEmpty());
        assertTrue(empty.preOrder().isEmpty());
        assertTrue(empty.levelOrder().isEmpty());
    }
}
