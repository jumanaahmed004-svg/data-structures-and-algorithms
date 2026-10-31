# 1. Dynamic Array

📄 Code: [`DynamicArray.java`](../src/main/java/com/jumana/datastructures/array/DynamicArray.java) · Tests: [`DynamicArrayTest.java`](../src/test/java/com/jumana/datastructures/array/DynamicArrayTest.java)

## The idea

A normal Java array has a **fixed size**: `new int[20]` can never hold 21 items. A dynamic array (Java's `ArrayList` is one) *feels* like it can grow forever. Underneath, it is still a plain array. When the array fills up, it quietly creates a bigger one and copies everything over.

**Analogy:** a row of numbered lockers. Items sit next to each other with no gaps, so you can jump straight to locker #7. When every locker is full, you move to a row twice as long.

## How it's stored

```java
private Object[] data;   // the actual array (the capacity)
private int size;        // how many slots are really used
```

```
capacity = 8, size = 5

index:   0    1    2    3    4    5    6    7
       +----+----+----+----+----+----+----+----+
data:  | 10 | 20 | 30 | 40 | 50 |    |    |    |
       +----+----+----+----+----+----+----+----+
                                  ^
                                  size = 5 (next free slot)
```

- Used elements are always at indices `0 .. size-1`, with no gaps.
- `size` plays the role of `UB + 1` from your original code (`UB` was the index of the last element).

---

## Operations step by step

### `get(index)` → O(1)

```java
checkIndex(index);       // 0 <= index < size, otherwise throw
return elementAt(index); // data[index]
```

The computer can calculate where `data[index]` is in memory directly, so there is no searching. This "random access" is the main advantage of arrays.

### `add(element)` → O(1) amortized

```java
growIfFull();
data[size++] = element;   // put it in the next free slot, then size++
```

`data[size++] = x` means "use the current `size` as the index, *then* increase it by 1".

### `growIfFull()`: resizing

```java
if (size == data.length) {
    data = Arrays.copyOf(data, data.length * 2);
}
```

```
Before (full):  [10][20][30][40]          capacity 4

After:          [10][20][30][40][  ][  ][  ][  ]   capacity 8
```

`Arrays.copyOf` creates a new, bigger array and copies the old elements into it. This one call costs O(n).

**Why double, and why is `add` still "O(1)"?**
Suppose we start at capacity 1 and add 16 items. Copies happen at sizes 1, 2, 4 and 8, which is 1 + 2 + 4 + 8 = 15 element copies for 16 adds. That's **less than one copy per add on average**. If we grew by only +1 each time, *every* add would copy the whole array, giving O(n) per add. Doubling is what makes it cheap.

### `insert(index, element)` → O(n)

To insert in the middle, every element from `index` onwards must move one step right to make room.

```java
for (int i = size - 1; i >= index; i--) {
    data[i + 1] = data[i];
}
data[index] = element;
size++;
```

Example: `insert(1, 15)` on `[10, 20, 30]`

```
start:            [10][20][30][  ]
i=2: copy 30 →    [10][20][30][30]
i=1: copy 20 →    [10][20][20][30]
place 15 at 1:    [10][15][20][30]    size = 4
```

❓ **Why does the loop go backwards?** If it went forwards, `data[2] = data[1]` would overwrite 30 before we saved it, and the same value would be copied all the way down. Always shift **away** from the gap, starting at the far end.

Valid indices for insert are `0 .. size` (inclusive). `insert(size, x)` is the same as `add(x)`.

### `remove(index)` → O(n)

The opposite: shift everything after `index` one step **left** to close the gap.

```java
T removed = elementAt(index);
for (int i = index; i < size - 1; i++) {
    data[i] = data[i + 1];
}
data[--size] = null;
return removed;
```

Example: `remove(1)` on `[10, 20, 30, 40]`

```
start:            [10][20][30][40]
i=1:              [10][30][30][40]
i=2:              [10][30][40][40]
null last, size-- [10][30][40][  ]    returns 20
```

**Why set the last slot to `null`?** The array would otherwise still hold a reference to that object, so Java's garbage collector couldn't free it. This is called a *memory leak*. It doesn't matter for `int`, but it does for objects.

### `clear()` → O(n)

```java
Arrays.fill(data, 0, size, null);
size = 0;
```

Your original version just reset `UB = -1`. That works for `int`, but for objects we also null the slots for the same garbage-collection reason.

---

## Complexity summary

| Operation | Time | Why |
|---|---|---|
| `get` / `set` | O(1) | direct index |
| `add` (end) | O(1) amortized | usually just one write; rare doubling |
| `insert` / `remove` | O(n) | shifting elements |
| memory | O(n) | up to 2× the used space while half empty |

## Common mistakes

- **Off-by-one in bounds:** `get` allows `0..size-1`, but `insert` allows `0..size`.
- **Shifting in the wrong direction** (see the backwards loop above).
- **Confusing capacity and size:** `data.length` is the capacity; `size` is how many elements are actually stored.

## Practice

1. Add a method `int indexOf(T value)` that returns the first index of `value`, or `-1`. What is its Big-O?
2. Add `shrink()`: when `size` falls below a quarter of the capacity, halve the array. Why a quarter and not a half? (Hint: think about adding and removing one item over and over at the boundary.)
3. Rewrite the shifting loops using `System.arraycopy`. Is the Big-O different?
