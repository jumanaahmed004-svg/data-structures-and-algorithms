# 3. Stack + Postfix Evaluation

📄 Code: [`ArrayStack.java`](../src/main/java/com/jumana/datastructures/stack/ArrayStack.java), [`PostfixEvaluator.java`](../src/main/java/com/jumana/datastructures/stack/PostfixEvaluator.java) · Tests: [`ArrayStackTest.java`](../src/test/java/com/jumana/datastructures/stack/ArrayStackTest.java), [`PostfixEvaluatorTest.java`](../src/test/java/com/jumana/datastructures/stack/PostfixEvaluatorTest.java)

## The idea

A stack is **Last In, First Out (LIFO)**. You can only touch the top.

**Analogy:** a stack of plates. You put a plate on top (**push**) and take the top plate off (**pop**). You can look at the top plate without taking it (**peek**).

Stacks appear everywhere: the **Undo** button, the browser **Back** button, checking matching brackets `( [ ] )`, and Java itself. Every method call is pushed onto the *call stack*, and when it returns it's popped. That's where the name `StackOverflowError` comes from.

## How it's stored

```java
private Object[] stack = new Object[10];
private int top = -1;     // index of the top element; -1 means empty
```

```
push(5), push(8), push(3):

index:  0   1   2   3 ...
      +---+---+---+---+
      | 5 | 8 | 3 |   |
      +---+---+---+---+
              ^
             top = 2
```

- The bottom of the stack is index 0, and it grows to the right.
- `size()` is `top + 1`.
- This is the same idea as your original `STACK[]` and `TOP`. The new version also **doubles** when full (like the dynamic array) instead of printing "Stack is FULL!".

---

## Operations step by step

### `push(value)` → O(1) amortized

```java
if (top == stack.length - 1) {                        // full → grow
    stack = Arrays.copyOf(stack, stack.length * 2);
}
stack[++top] = value;     // move top up first, then store
```

`++top` (prefix) increases `top` **before** it's used as the index. Compare with `size++` in the dynamic array, which uses the value first and increases it afterwards.

### `peek()` → O(1)

```java
if (isEmpty()) throw new NoSuchElementException("Stack is empty");
return (T) stack[top];
```

### `pop()` → O(1)

```java
T value = peek();          // reuses peek: also checks for empty
stack[top--] = null;       // clear the slot, then move top down
return value;
```

`pop` reuses `peek()` instead of repeating the empty check. Avoiding duplicated logic like this is called the **DRY principle** (Don't Repeat Yourself).

> All operations touch only the top, so there's never any shifting. That's why everything is O(1).

---

## Postfix evaluation (a real use of a stack)

### What is postfix?

| Style | Example | Notes |
|---|---|---|
| Infix (normal) | `(3 + 4) * 2` | needs brackets and precedence rules |
| Postfix (RPN) | `3 4 + 2 *` | operator comes **after** its two operands; no brackets needed |

Computers like postfix because it can be evaluated left to right in **one pass with one stack**, with no precedence rules.

### The algorithm

```
for each token, left to right:
    if it's a number   → push it
    if it's an operator → b = pop, a = pop, push (a op b)
at the end, exactly one number must be left: the answer
```

### Walkthrough: `5 1 2 + 4 * + 3 -`

| Token | Action | Stack after (top on right) |
|---|---|---|
| `5` | push | `[5]` |
| `1` | push | `[5, 1]` |
| `2` | push | `[5, 1, 2]` |
| `+` | b=2, a=1 → push 1+2 | `[5, 3]` |
| `4` | push | `[5, 3, 4]` |
| `*` | b=4, a=3 → push 3*4 | `[5, 12]` |
| `+` | b=12, a=5 → push 5+12 | `[17]` |
| `3` | push | `[17, 3]` |
| `-` | b=3, a=17 → push 17−3 | `[14]` |
| end | one value left | **result = 14** |

In infix, that's `5 + (1 + 2) * 4 - 3 = 14`.

### ❓ Why pop `b` first?

The stack reverses order. For `10 4 -`, 10 is pushed first and 4 second, so **4 comes out first**. The first pop is the *right* operand:

```java
int b = stack.pop();   // 4
int a = stack.pop();   // 10
// a - b = 10 - 4 = 6   ✅      (b - a would give -6 ❌)
```

This only matters for `-` and `/`, which is why there's a test called `operandOrderMattersForMinusAndDivide`.

### The code

```java
for (String token : expression.trim().split("\\s+")) {
    if (token.matches("-?\\d+")) {          // a number like 42 or -7
        stack.push(Integer.parseInt(token));
        continue;                            // go to next token
    }
    if (stack.size() < 2) {
        throw new IllegalArgumentException("Not enough operands for '" + token + "'");
    }
    int b = stack.pop();
    int a = stack.pop();
    stack.push(apply(token, a, b));
}
if (stack.size() != 1) {
    throw new IllegalArgumentException("Malformed expression: too many operands");
}
return stack.pop();
```

A few details:

- **`split("\\s+")`** splits on *one or more* spaces, so `"3   4 +"` still works. Your original used `split(" ")`, which creates empty tokens when there are double spaces.
- **`matches("-?\\d+")`** is a *regular expression*: an optional `-` followed by one or more digits. It accepts negative numbers like `-2`. Your original checked `Character.isDigit(token.charAt(0))`, which treats `-2` as an operator.
- **Error checks.** `"+"` alone has nothing to pop, so it throws. `"1 2"` leaves two numbers on the stack, so it throws. Your original printed a result even after an unknown operator, because `break` only left the `for` loop.

### The `switch` expression

```java
return switch (operator) {
    case "+" -> a + b;
    case "-" -> a - b;
    case "*" -> a * b;
    case "/" -> a / b;      // integer division: 7 / 2 = 3
    default  -> throw new IllegalArgumentException("Unknown operator: " + operator);
};
```

This is the modern `switch` with arrows (Java 14+) that you already used in your menus. Here it *returns a value*, which replaces a long `if / else if` chain.

---

## Complexity summary

| Operation | Time |
|---|---|
| push | O(1) amortized |
| pop / peek / isEmpty / size | O(1) |
| postfix evaluate | O(n) for n tokens |

## Common mistakes

- Popping operands in the wrong order (`b - a`).
- Not checking for an empty stack before `pop`.
- Using `top++` vs `++top` incorrectly. Trace through it with a tiny example on paper.

## Practice

1. Write `boolean isBalanced(String s)` that returns `true` for `"([]{})"` and `false` for `"([)]"`. Hint: push opening brackets, and pop and compare on closing ones.
2. Add the `^` (power) operator to `PostfixEvaluator`, and add a test for it.
3. Write `LinkedStack<T>` using nodes instead of an array. Which operations of `SinglyLinkedList` would `push` and `pop` correspond to?
4. *(Challenge)* Convert infix to postfix: `"3 + 4 * 2"` → `"3 4 2 * +"`. Look up the **Shunting-yard algorithm**, which also uses a stack.
