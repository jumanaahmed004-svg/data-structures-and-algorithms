# 4. Circular Queue

📄 Code: [`CircularQueue.java`](../src/main/java/com/jumana/datastructures/queue/CircularQueue.java) · Tests: [`CircularQueueTest.java`](../src/test/java/com/jumana/datastructures/queue/CircularQueueTest.java)

## The idea

A queue is **First In, First Out (FIFO)**: the first one to arrive is the first one served.

**Analogy:** a line of customers at a bank, like your original "Customer Queue" program. New customers join at the **rear**; the customer at the **front** is served next.

Real uses include printer jobs, messages waiting to be processed, keyboard input buffers, and **BFS** in graphs and trees (see guides 5 and 6).

## The problem with a simple array queue

Suppose we add at `rear` and remove from `front`, always moving to the right:

```
capacity 5, after enqueue A,B,C,D,E then dequeue A,B:

index:   0   1   2   3   4
       +---+---+---+---+---+
       |   |   | C | D | E |
       +---+---+---+---+---+
                 ^       ^
               front    rear = 4 (end of array!)
```

`rear` has hit the end, so the queue looks "full", even though slots 0 and 1 are empty. Shifting everything left after each dequeue would fix it, but that's O(n).

## The solution: wrap around

Treat the array as if it were a **circle**. Index 4 is followed by index 0 again.

```
            [0]
       [4]       [1]
        
         [3]   [2]
```

The trick is the **modulo operator `%`** (the remainder after division):

```java
rear = (rear + 1) % capacity;
```

| rear | rear + 1 | (rear + 1) % 5 |
|---|---|---|
| 2 | 3 | 3 |
| 3 | 4 | 4 |
| 4 | 5 | **0** ← wraps around |

Your original code already used this trick, which is exactly the right approach.

## How it's stored

```java
private final Object[] queue;  // fixed capacity
private int front = 0;         // index of the oldest element
private int rear  = -1;        // index of the newest element
private int size  = 0;         // how many elements are in the queue
```

**Why keep `size`?** When `front` and `rear` wrap around, a *full* queue and an *empty* queue can have the same `front`/`rear` relationship. Keeping a separate `size` counter removes that confusion: empty means `size == 0`, and full means `size == capacity`.

`final` means the `queue` variable can never point to a different array. This queue has a deliberately fixed capacity.

---

## Operations step by step

### `enqueue(value)` → O(1)

```java
if (isFull()) throw new IllegalStateException("Queue is full");
rear = (rear + 1) % queue.length;   // move rear forward (with wrap-around)
queue[rear] = value;
size++;
```

### `dequeue()` → O(1)

```java
T value = peek();                    // throws if empty
queue[front] = null;                 // help garbage collection
front = (front + 1) % queue.length;  // move front forward (with wrap-around)
size--;
return value;
```

Nothing is ever shifted. Only the two indices move, which is why both operations are O(1).

### Full walkthrough (capacity 3)

This is exactly what the test `wrapsAroundAndReusesSpace` checks.

```
                      [0]  [1]  [2]   front rear size
start                  .    .    .      0    -1    0
enqueue 1              1    .    .      0     0    1
enqueue 2              1    2    .      0     1    2
enqueue 3              1    2    3      0     2    3   ← full
dequeue → 1            .    2    3      1     2    2
dequeue → 2            .    .    3      2     2    1
enqueue 4              4    .    3      2     0    2   ← rear wrapped to 0!
enqueue 5              4    5    3      2     1    3   ← full again
dequeue → 3            4    5    .      0     1    2   ← front wrapped to 0!
```

The queue's order (3, 4, 5) is correct even though, in the array, 3 is physically *after* 4 and 5.

### `toString()`: walking a circular array

```java
for (int i = 0; i < size; i++) {
    joiner.add(String.valueOf(queue[(front + i) % queue.length]));
}
```

Start at `front` and take `size` steps, wrapping with `%`. This is the same loop as case 3 in your original program.

---

## Why throw on a full queue instead of growing?

The stack and dynamic array double their size, but this queue has a **fixed capacity** on purpose. Bounded queues are common in real systems (for example, a buffer with limited memory), and "full" is useful information. If you need an unlimited queue, Java has `ArrayDeque`, or you could build one on top of a linked list.

Different exception types tell the caller *what* went wrong:
- `IllegalStateException`: "you can't do this right now" (the queue is full)
- `NoSuchElementException`: "there's nothing there" (the queue is empty)

## Complexity summary

| Operation | Time |
|---|---|
| enqueue / dequeue / peek | O(1) |
| isEmpty / isFull / size | O(1) |
| toString | O(n) |

## Common mistakes

- Forgetting `% capacity`, which causes `ArrayIndexOutOfBoundsException`.
- Trying to detect full or empty using only `front` and `rear` (use `size`).
- Printing `queue[0..size-1]` instead of starting from `front`.

## Practice

1. Make the queue **grow** when full. Careful: you can't just use `Arrays.copyOf`, because the elements may be wrapped around. Copy them in queue order, starting at `front`.
2. Build `LinkedQueue<T>` using nodes. Which end should be the front: `head` or `tail`? (Hint: which removal is O(1) in a singly linked list?)
3. Implement a **queue using two stacks**, a popular interview question.
