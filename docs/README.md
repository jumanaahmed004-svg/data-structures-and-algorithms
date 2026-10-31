# Learning Guide

These notes explain how each data structure in this project works, step by step, using the actual code from `src/main/java`.

## Suggested reading order

| # | Guide | You will learn |
|---|---|---|
| 0 | This page | Big-O notation and generics: two ideas used everywhere |
| 1 | [Dynamic Array](01-dynamic-array.md) | Storing items side by side, shifting, and resizing |
| 2 | [Singly Linked List](02-linked-list.md) | Nodes and pointers (references) |
| 3 | [Stack + Postfix Evaluation](03-stack.md) | LIFO, and using a stack to evaluate expressions |
| 4 | [Circular Queue](04-queue.md) | FIFO, and the "wrap-around" trick with `%` |
| 5 | [Binary Tree](05-binary-tree.md) | Recursion and the four tree traversals |
| 6 | [Graph](06-graph.md) | Adjacency lists, BFS and DFS |

Each guide has the same sections: **Idea → How it's stored → Operations step by step → Complexity → Common mistakes → Practice**.

---

## Big-O notation in 2 minutes

Big-O describes **how the work grows as the data grows**. It ignores small details and only keeps the trend. `n` means "number of elements".

| Big-O | Name | Example in this project | If n = 1,000 → about |
|---|---|---|---|
| O(1) | constant | `stack.push()`, `queue.dequeue()`, `array.get(i)` | 1 step |
| O(n) | linear | `list.contains(x)`, `array.insert(0, x)` | 1,000 steps |
| O(V + E) | linear in graph size | `graph.bfs()` | vertices + edges |

**How to find it yourself:** look for loops.
- No loop that depends on `n` → **O(1)**
- One loop over the elements → **O(n)**
- A loop inside a loop, both over `n` → **O(n²)**

**Amortized O(1):** the dynamic array and stack sometimes copy everything into a bigger array (O(n)). Because the size *doubles* each time, this happens so rarely that the *average* cost per `add` is still constant. Guide 1 explains this in detail.

---

## Generics: what does `<T>` mean?

Your original code used `int[]` and `String[]`, so each structure only worked for one type. Now the classes are written like this:

```java
public class ArrayStack<T> { ... }
```

`T` is a **placeholder for a type**. You choose the type when you create the object:

```java
ArrayStack<Integer> numbers = new ArrayStack<>();
ArrayStack<String>  names   = new ArrayStack<>();
```

One class now works for any type, and the compiler stops you from putting a `String` into the `Integer` stack.

> Generics only work with objects, so we write `Integer` instead of `int`. Java converts between them automatically (this is called *autoboxing*).

**Why `Object[]` inside the class?** Java doesn't allow `new T[10]`, because type information is erased at runtime. The usual workaround (also used inside Java's own `ArrayList`) is to store an `Object[]` and cast back to `T` when reading:

```java
@SuppressWarnings("unchecked")
private T elementAt(int index) {
    return (T) data[index];
}
```

This is safe because only `T` values are ever put into the array.

---

## Exceptions instead of print statements

Your original programs printed messages like `"Stack is EMPTY!"`. The reusable classes **throw exceptions** instead, and the demo menus catch them and print the message.

```java
// In the data structure: report the problem
if (isEmpty()) throw new NoSuchElementException("Stack is empty");

// In the menu: decide what to show the user
try {
    System.out.println("Popped: " + stack.pop());
} catch (NoSuchElementException e) {
    System.out.println("Stack is EMPTY!");
}
```

**Why?** A data structure shouldn't decide how errors are shown. A console app prints them, a GUI shows a popup, and a test checks that the exception happened. This separation is what makes the classes reusable.
