# 2. Singly Linked List

📄 Code: [`SinglyLinkedList.java`](../src/main/java/com/jumana/datastructures/linkedlist/SinglyLinkedList.java) · Tests: [`SinglyLinkedListTest.java`](../src/test/java/com/jumana/datastructures/linkedlist/SinglyLinkedListTest.java)

## The idea

Instead of one big block of memory, a linked list is a **chain of small objects called nodes**. Each node holds a value plus a reference (pointer) to the next node.

**Analogy:** a treasure hunt. Each clue tells you where the next clue is. You can't jump to clue #5; you have to follow the chain from the start. But adding a new clue at the beginning is easy: write it and point it at the old first clue.

## How it's stored

```java
private static class Node<T> {
    T data;
    Node<T> next;   // the next node, or null if this is the last one
}

private Node<T> head;   // first node (START in your original code)
private Node<T> tail;   // last node  (END in your original code)
private int size;
```

```
 head                                  tail
  |                                     |
  v                                     v
+----+---+    +----+---+    +----+---+
| 10 | --+--->| 20 | --+--->| 30 | / |      / = null
+----+---+    +----+---+    +----+---+
 data next
```

- Empty list: `head == null` and `tail == null`.
- One element: `head == tail` (both point at the same node).

**Why keep `tail`?** Without it, `addLast` would have to walk the whole chain to find the end, which is O(n). With `tail`, it's O(1).

**Why `private static class Node`?** `private` hides it, so users of the list never deal with nodes directly. `static` means a node doesn't need a hidden link back to the list object, which saves memory.

---

## Operations step by step

### `addFirst(value)` → O(1)

```java
Node<T> node = new Node<>(value);
node.next = head;        // 1. new node points at the old first node
head = node;             // 2. head now points at the new node
if (tail == null) {      // 3. list was empty → it is also the last node
    tail = node;
}
size++;
```

```
before:          head → [10] → [20] → null

1. node.next = head     [5] ─┐
                              v
                 head → [10] → [20] → null

2. head = node   head → [5] → [10] → [20] → null
```

⚠️ **Order matters.** If you did `head = node` first, you'd lose your only reference to the old list.

### `addLast(value)` → O(1)

```java
Node<T> node = new Node<>(value);
if (head == null) {
    head = node;          // empty list: node is first...
} else {
    tail.next = node;     // otherwise: link old last node to it
}
tail = node;              // ...and in both cases it's now the last
size++;
```

### `removeFirst()` → O(1)

```java
T value = head.data;
head = head.next;         // skip over the first node
if (head == null) {       // list became empty
    tail = null;
}
size--;
return value;
```

The old first node now has nothing pointing to it, so Java's garbage collector deletes it automatically.

⚠️ **The classic bug:** forgetting `tail = null` when the list becomes empty. `tail` would still point at the removed node, and the next `addLast` would attach to a node that's no longer in the list. There's a test for exactly this: `removingOnlyElementResetsHeadAndTail`.

### `removeLast()` → O(n)

This is the weak spot of a *singly* linked list. To remove the last node, we must change the **second-to-last** node's `next` to `null`. Nodes only point forwards, though, so we have to walk from the head to find it.

```java
if (head == tail) {           // only one element
    return removeFirst();
}
Node<T> ptr = head;
while (ptr.next != tail) {    // stop at the node just before tail
    ptr = ptr.next;
}
T value = tail.data;
ptr.next = null;
tail = ptr;
size--;
return value;
```

```
head → [10] → [20] → [30] → null
                ^      ^
               ptr    tail       loop stops here because ptr.next == tail

after:  head → [10] → [20] → null
                       ^
                      tail
```

(Your original used `while (PTR.next.next != null)`, which does the same thing in a different way.)

> A **doubly** linked list (each node also has `prev`) makes `removeLast` O(1). That's the trade-off: more memory per node for faster operations at the end.

### `remove(value)` → O(n)

To remove a node from the middle, we need the node **before** it, so we can "jump over" it.

```java
if (Objects.equals(head.data, value)) {   // special case: it's the first node
    removeFirst();
    return true;
}
Node<T> ptr = head;
while (ptr.next != null && !Objects.equals(ptr.next.data, value)) {
    ptr = ptr.next;
}
if (ptr.next == null) return false;       // reached the end: not found

ptr.next = ptr.next.next;                  // jump over the found node
if (ptr.next == null) tail = ptr;          // we removed the last node
size--;
return true;
```

```
remove(20):
head → [10] → [20] → [30] → null
        ptr   found

ptr.next = ptr.next.next
head → [10] ────────→ [30] → null
```

**Why `Objects.equals(a, b)` instead of `a == b`?**
For objects, `==` checks if two references point to *the same object*, not if they have *the same value*. `Objects.equals` compares values and also handles `null` safely. With `Integer`, `==` even works for small numbers (−128 to 127, which Java caches) and then fails for bigger ones. That's a very confusing bug to find.

### `contains(value)` → O(n)

```java
for (Node<T> ptr = head; ptr != null; ptr = ptr.next) {
    if (Objects.equals(ptr.data, value)) return true;
}
return false;
```

This `for` loop is the standard way to walk a linked list: start at `head`, stop at `null`, and step with `ptr = ptr.next`.

---

## Array vs. Linked List

| | Dynamic Array | Linked List |
|---|---|---|
| Get item #i | **O(1)** | O(n): must walk |
| Add/remove at front | O(n): shift everything | **O(1)** |
| Add at end | O(1) amortized | **O(1)** (with tail) |
| Memory per item | just the value | value + `next` reference |
| Cache friendly | ✅ items side by side | ❌ nodes scattered in memory |

## Common mistakes

- Losing the rest of the list by updating pointers in the wrong order.
- Forgetting to update `tail` (on empty → first add, on removing the last node, and on emptying the list).
- `NullPointerException` from `ptr.next.data` when `ptr.next` is `null`. Check `ptr.next != null` **first**, because `&&` stops early.

## Practice

1. Add `T get(int index)`. What's its Big-O compared with the array version?
2. Add `void reverse()` that reverses the list **in place** by changing the `next` pointers (a classic interview question). Hint: use three variables, `prev`, `current` and `next`.
3. Add `T middle()` that finds the middle element in one pass. Hint: move one pointer 1 step and another 2 steps at a time.
