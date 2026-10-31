# Data Structures and Algorithms in Java

Core data structures and algorithms implemented from scratch in Java, without using the built-in collection classes for the structures themselves. Each structure is a reusable generic class with documented time complexity, unit tests, and an interactive console demo. The algorithms include postfix evaluation, tree traversals, and breadth-first and depth-first search.

I built this project while studying data structures and algorithms, to understand how each one works internally.

📚 **New to these topics? Start with the [Learning Guide](docs/README.md).** It explains each structure and algorithm step by step, with diagrams, walkthroughs and practice questions.

## What's inside

| Structure | Class | Key operations | Complexity |
|---|---|---|---|
| Dynamic array | [`DynamicArray`](src/main/java/com/jumana/datastructures/array/DynamicArray.java) | `add`, `insert`, `remove`, `get`, `set`, `clear` | add O(1)\*, get/set O(1), insert/remove O(n) |
| Singly linked list | [`SinglyLinkedList`](src/main/java/com/jumana/datastructures/linkedlist/SinglyLinkedList.java) | `addFirst`, `addLast`, `removeFirst`, `removeLast`, `remove`, `contains` | add/removeFirst O(1), removeLast O(n), search O(n) |
| Stack (array-based) | [`ArrayStack`](src/main/java/com/jumana/datastructures/stack/ArrayStack.java) | `push`, `pop`, `peek` | push O(1)\*, pop/peek O(1) |
| Postfix evaluator | [`PostfixEvaluator`](src/main/java/com/jumana/datastructures/stack/PostfixEvaluator.java) | `evaluate("5 1 2 + 4 * + 3 -")` → `14` | O(n) |
| Circular queue | [`CircularQueue`](src/main/java/com/jumana/datastructures/queue/CircularQueue.java) | `enqueue`, `dequeue`, `peek` | all O(1) |
| Binary tree | [`BinaryTree`](src/main/java/com/jumana/datastructures/tree/BinaryTree.java) | level-order `insert`, pre/in/post/level-order traversals | insert O(n), traversals O(n) |
| Graph (adjacency list) | [`Graph`](src/main/java/com/jumana/datastructures/graph/Graph.java) | `addVertex`, `addEdge`, `neighbors`, `bfs`, `dfs` | add O(1), BFS/DFS O(V + E) |

\* amortized: the array doubles in size when full, so the occasional copy averages out to constant time.

## Project structure

```
src/
├── main/java/com/jumana/datastructures/
│   ├── array/        DynamicArray
│   ├── linkedlist/   SinglyLinkedList
│   ├── stack/        ArrayStack, PostfixEvaluator
│   ├── queue/        CircularQueue
│   ├── tree/         BinaryTree
│   ├── graph/        Graph
│   └── demo/         Main (interactive menu) + one demo per structure
└── test/java/com/jumana/datastructures/
    └── ...           JUnit 5 tests for every structure
```

## Running it

**Requirements:** Java 17 or newer.

### In VS Code
1. Install the **Extension Pack for Java** (VS Code suggests it when you open this folder).
2. Open the project folder and wait for the Java project to finish loading.
3. Open `src/main/java/com/jumana/datastructures/demo/Main.java` and click **Run** above `main`.
4. To run the tests, open the **Testing** panel (flask icon) and click **Run Tests**.

### From the command line (with Maven installed)
```bash
mvn test                  # run all unit tests
mvn compile exec:java     # start the interactive menu
```

## Example

```java
ArrayStack<Integer> stack = new ArrayStack<>();
stack.push(1);
stack.push(2);
stack.pop();   // 2

CircularQueue<String> queue = new CircularQueue<>(3);
queue.enqueue("Ali");
queue.enqueue("Sara");
queue.dequeue();   // "Ali"

Graph<String> graph = new Graph<>();
graph.addEdge("A", "B");
graph.addEdge("A", "C");
graph.addEdge("B", "D");
graph.bfs("A");   // [A, B, C, D]
```

## What I learned

- How array-based and node-based structures trade off memory and speed.
- Why a circular array lets a queue reuse space with O(1) operations.
- How a stack evaluates postfix expressions.
- Recursive tree traversals, and breadth-first vs. depth-first search on graphs.
- Writing generic classes, handling edge cases with exceptions, and testing with JUnit.

## Author

**Jumana Albalushi**

## License

[MIT](LICENSE)
