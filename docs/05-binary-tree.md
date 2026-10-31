# 5. Binary Tree

📄 Code: [`BinaryTree.java`](../src/main/java/com/jumana/datastructures/tree/BinaryTree.java) · Tests: [`BinaryTreeTest.java`](../src/test/java/com/jumana/datastructures/tree/BinaryTreeTest.java)

## The idea

Arrays, lists, stacks and queues are all **linear**: one item after another. A tree is **hierarchical**. Each node can have children.

In a **binary** tree, each node has **at most two children**: `left` and `right`.

**Analogy:** a family tree, or folders on your computer.

## Vocabulary

```
              10          ← root (top node, no parent)
            /    \
           6      15      ← 6 and 15 are children of 10, and siblings
          / \    /  \
         3   8  20   9    ← leaves (no children)
```

| Term | Meaning |
|---|---|
| root | the top node |
| parent / child | 10 is the parent of 6, and 6 is a child of 10 |
| leaf | a node with no children (3, 8, 20, 9) |
| subtree | a node plus everything below it (6, 3, 8 form a subtree) |
| height | number of levels (3 here) |

## How it's stored

```java
private static class Node<T> {
    T value;
    Node<T> left;
    Node<T> right;
}

private Node<T> root;
private int size;
```

This is like a linked list node, but with **two** `next` pointers.

---

## Insertion: level order (filling the tree row by row)

Your tree fills the **first empty spot**, scanning top to bottom and left to right. This keeps the tree **complete**: every level is full except maybe the last, which fills from the left.

> ⚠️ This is **not** a Binary *Search* Tree (BST). A BST keeps `left < node < right`, so values are sorted. Here values go wherever there's space. See Practice question 3.

### How do we find the first empty spot? With a queue!

We visit nodes level by level using a **queue** (guide 4). This is **Breadth-First Search (BFS)**.

```java
Queue<Node<T>> queue = new ArrayDeque<>();
queue.add(root);

while (!queue.isEmpty()) {
    Node<T> current = queue.poll();          // take the next node in line

    if (current.left == null) {              // empty left spot? use it
        current.left = newNode;
        return;
    }
    queue.add(current.left);                 // otherwise check its children later

    if (current.right == null) {             // empty right spot? use it
        current.right = newNode;
        return;
    }
    queue.add(current.right);
}
```

### Walkthrough: inserting `10, 6, 15, 3, 8, 20, 9`

```
insert 10:  root is null → 10 becomes root

insert 6:   queue [10] → 10.left empty → put 6 there
                10
               /
              6

insert 15:  queue [10] → 10.left taken (queue 6) → 10.right empty → 15
                10
               /  \
              6    15

insert 3:   queue [10] → both taken, queue [6, 15]
            poll 6 → 6.left empty → 3

insert 8:   … poll 6 → 6.left taken → 6.right empty → 8
insert 20:  … poll 6 (full) → poll 15 → 15.left empty → 20
insert 9:   … poll 15 → 15.right empty → 9

                10
              /    \
             6      15
            / \    /  \
           3   8  20   9
```

**Why `ArrayDeque` instead of `LinkedList`?** Your original used `LinkedList`, which works. `ArrayDeque` is Java's recommended queue: faster and lighter, because it's a circular array, just like guide 4!

---

## Traversals: visiting every node

There are four standard orders. Three of them use **recursion**.

### Recursion in one minute

A recursive method **calls itself on a smaller problem**. Every recursive method needs:
1. a **base case** that stops: here, `if (node == null) return;`
2. a **recursive case** that solves smaller pieces: here, the left and right subtrees

A tree is naturally recursive: each subtree is itself a tree.

### The three depth-first orders

They differ **only in when the node itself is visited**:

```java
// Pre-order: Node, Left, Right
out.add(node.value);
preOrder(node.left, out);
preOrder(node.right, out);

// In-order: Left, Node, Right
inOrder(node.left, out);
out.add(node.value);
inOrder(node.right, out);

// Post-order: Left, Right, Node
postOrder(node.left, out);
postOrder(node.right, out);
out.add(node.value);
```

**Memory trick:** "pre", "in" and "post" describe where the **node** goes relative to its children: before, in between, or after.

### Results for our tree

```
                10
              /    \
             6      15
            / \    /  \
           3   8  20   9
```

| Order | Result | Used for |
|---|---|---|
| Pre-order (N L R) | `10 6 3 8 15 20 9` | copying a tree, printing folder structures |
| In-order (L N R) | `3 6 8 10 20 15 9` | in a BST this gives **sorted** order |
| Post-order (L R N) | `3 8 6 20 9 15 10` | deleting a tree (children before parent), calculating folder sizes |
| Level-order (BFS) | `10 6 15 3 8 20 9` | printing level by level, shortest paths |

### Tracing pre-order by hand

```
preOrder(10): add 10
  preOrder(6): add 6
    preOrder(3): add 3
      preOrder(null) → return
      preOrder(null) → return
    preOrder(8): add 8
      (both children null → return)
  preOrder(15): add 15
    preOrder(20): add 20
    preOrder(9):  add 9

result: 10 6 3 8 15 20 9
```

Try tracing in-order and post-order the same way on paper. It's the best way to really understand recursion.

### Level-order (BFS)

```java
queue.add(root);
while (!queue.isEmpty()) {
    Node<T> current = queue.poll();
    result.add(current.value);
    if (current.left != null) queue.add(current.left);
    if (current.right != null) queue.add(current.right);
}
```

This is the same queue pattern as `insert`, but it visits *every* node instead of stopping at the first gap.

### Why return a `List` instead of printing?

Your original traversals called `System.out.print`. Returning a `List<T>` is more flexible: the caller can print it, test it with `assertEquals(List.of(...), tree.preOrder())`, or use it for something else.

The public method `preOrder()` creates the list and calls a **private helper** `preOrder(node, list)` that does the recursion. Users don't need to know about nodes. Your original required calling `t.preOrder(t.root)`.

---

## Complexity summary

| Operation | Time | Notes |
|---|---|---|
| insert | O(n) | BFS to find the first gap |
| any traversal | O(n) | each node visited once |
| recursion memory | O(height) | one call-stack frame per level |

## Common mistakes

- Forgetting the base case `if (node == null) return;`, which causes a `NullPointerException` or `StackOverflowError`.
- Mixing up the three orders. Remember: the position of the **node** is what changes.
- Thinking every binary tree is sorted. Only a BST is.

## Practice

1. Add `int height()` recursively. Hint: `1 + max(height(left), height(right))`, and an empty tree has height 0.
2. Add `boolean contains(T value)`. What's its Big-O here, and why would it be faster in a BST?
3. *(Big one)* Create `BinarySearchTree<T extends Comparable<T>>` where `insert` goes left if the value is smaller and right if it's larger. Insert `10, 6, 15, 3, 8, 20, 9` and check that `inOrder()` returns them sorted.
4. Write pre-order **without recursion**, using an explicit stack (guide 3).
