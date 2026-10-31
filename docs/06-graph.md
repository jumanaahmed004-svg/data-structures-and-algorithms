# 6. Graph

📄 Code: [`Graph.java`](../src/main/java/com/jumana/datastructures/graph/Graph.java) · Tests: [`GraphTest.java`](../src/test/java/com/jumana/datastructures/graph/GraphTest.java)

## The idea

A graph is a set of **vertices** (points) connected by **edges** (lines). It's the most general structure in this project: a tree is a special kind of graph, and so is a linked list.

**Examples:** cities and roads (Google Maps), people and friendships (social networks), web pages and links, airports and flights.

```
   A --- B
   |     |
   C --- D --- E
```

| Term | Meaning |
|---|---|
| vertex (node) | A, B, C, D, E |
| edge | a connection, e.g. A–B |
| neighbours (adjacent) | vertices directly connected: neighbours of D are B, C, E |
| undirected | edges go both ways (if A–B then B–A). This project uses undirected edges. |
| directed | one-way edges, like a one-way street or Instagram "follow" |
| path | a route, e.g. A → C → D → E |
| cycle | a path that returns to its start: A → B → D → C → A |

> Unlike trees, graphs can have **cycles**. That's why traversals must remember which vertices they've already visited, or they would loop forever.

## How it's stored: adjacency list

Each vertex maps to the set of its neighbours:

```java
private final Map<T, Set<T>> adjacency = new LinkedHashMap<>();
```

```
A -> [B, C]
B -> [A, D]
C -> [A, D]
D -> [B, C, E]
E -> [D]
```

Note that every undirected edge appears **twice**: A–B is stored in A's set *and* B's set.

### Why these collection types?

| Choice | Original | Now | Reason |
|---|---|---|---|
| Map | `HashMap` | `LinkedHashMap` | `HashMap` prints vertices in an unpredictable order; `LinkedHashMap` keeps insertion order, so output and tests are predictable |
| Neighbours | `ArrayList` | `LinkedHashSet` | calling `addEdge("A","B")` twice would store B twice in a list; a **Set** ignores duplicates, and lookups (`hasEdge`) are O(1) instead of O(n) |

### The other option: adjacency matrix

A 2D array where `matrix[i][j] = true` if there's an edge from i to j. It can check an edge in O(1), but it always uses V × V memory, even with few edges. Adjacency lists are better for most real graphs, which are *sparse* (each vertex has only a few neighbours).

---

## Building the graph

```java
public void addVertex(T vertex) {
    adjacency.putIfAbsent(vertex, new LinkedHashSet<>());
}

public void addEdge(T a, T b) {
    addVertex(a);              // make sure both vertices exist
    addVertex(b);
    adjacency.get(a).add(b);   // a → b
    adjacency.get(b).add(a);   // b → a   (undirected: both directions)
}
```

`putIfAbsent` adds the key only if it's not already there. That's a one-line version of your original `if (!graph.containsKey(v)) graph.put(...)`.

### `neighbors()` returns a read-only view

```java
return Collections.unmodifiableSet(adjacency.get(vertex));
```

If we returned the real set, someone could call `graph.neighbors("A").add("Z")` and create a one-way edge, breaking the "undirected" rule. A read-only view protects the graph's internal data. This is called **encapsulation**.

### Counting edges

Every undirected edge is stored twice, so the edge count is the total number of neighbour entries divided by 2. (The code also handles a vertex connected to itself, called a *self-loop*, which is stored only once.)

---

## Breadth-First Search (BFS)

**Idea:** visit vertices in "waves" by distance from the start. First the start, then all its neighbours, then *their* neighbours, and so on. Think of ripples spreading in water.

It uses a **queue**, the same pattern as the tree's level-order traversal, plus a **visited** set because of cycles.

```java
Set<T> visited = new HashSet<>();
Queue<T> queue = new ArrayDeque<>();

visited.add(start);
queue.add(start);
while (!queue.isEmpty()) {
    T current = queue.poll();
    order.add(current);
    for (T next : adjacency.get(current)) {
        if (visited.add(next)) {   // true only if 'next' was NOT already in the set
            queue.add(next);
        }
    }
}
```

**`visited.add(next)` trick:** `Set.add` returns `true` if the item was new and `false` if it was already there. So one call both *checks* and *marks* a vertex.

**Why mark vertices when adding them to the queue, not when removing them?** Otherwise the same vertex could be added to the queue several times by different neighbours. In our graph, D is a neighbour of both B and C.

### Walkthrough: `bfs("A")`

| Step | Poll | Neighbours | Newly added | Queue after | Visit order |
|---|---|---|---|---|---|
| start | | | A | `[A]` | |
| 1 | A | B, C | B, C | `[B, C]` | A |
| 2 | B | A, D | D | `[C, D]` | A B |
| 3 | C | A, D | none (both visited) | `[D]` | A B C |
| 4 | D | B, C, E | E | `[E]` | A B C D |
| 5 | E | D | none | `[]` | A B C D E |

Result: **`[A, B, C, D, E]`**

⭐ **Key property:** in an unweighted graph, BFS reaches every vertex by the **shortest path** (fewest edges). That's why it's used for "minimum number of moves" problems.

---

## Depth-First Search (DFS)

**Idea:** go as **deep** as possible along one path, and only **backtrack** when you hit a dead end. Think of exploring a maze by always taking the first unexplored turn.

It uses **recursion**, which uses the call stack (guide 3) under the hood.

```java
private void dfs(T vertex, Set<T> visited, List<T> order) {
    visited.add(vertex);
    order.add(vertex);
    for (T next : adjacency.get(vertex)) {
        if (!visited.contains(next)) {
            dfs(next, visited, order);   // go deeper before trying the other neighbours
        }
    }
}
```

### Walkthrough: `dfs("A")`

```
dfs(A)  visit A        neighbours [B, C]
 └ dfs(B)  visit B     neighbours [A ✓, D]
    └ dfs(D)  visit D  neighbours [B ✓, C, E]
       ├ dfs(C)  visit C   neighbours [A ✓, D ✓] → dead end, backtrack
       └ dfs(E)  visit E   neighbours [D ✓]      → dead end, backtrack
 (back in A: C already visited → done)
```

Result: **`[A, B, D, C, E]`**

Notice that DFS reached C *through D* (deep first), while BFS reached C directly from A (wide first).

---

## BFS vs. DFS

| | BFS | DFS |
|---|---|---|
| Helper structure | Queue | Stack (or recursion) |
| Explores | level by level (wide) | one path at a time (deep) |
| Finds shortest path (unweighted)? | ✅ yes | ❌ not guaranteed |
| Typical uses | shortest route, nearest friend, level-order | mazes, detecting cycles, topological sort, connected components |
| Time | O(V + E) | O(V + E) |

**Why O(V + E)?** Each vertex is visited once (V), and for each vertex we loop over its neighbours. Across all vertices, that's every edge (twice for undirected, which is still O(E)).

**Unreachable vertices:** if the graph has separate pieces, BFS and DFS only visit the piece containing `start`. The test `unreachableVerticesAreNotVisited` checks this.

---

## Complexity summary

| Operation | Time |
|---|---|
| addVertex / addEdge / hasEdge | O(1) average |
| neighbors | O(1) |
| bfs / dfs | O(V + E) |
| memory | O(V + E) |

## Common mistakes

- Forgetting the `visited` set, which causes an infinite loop on any cycle.
- Adding only one direction for an undirected edge.
- Using `HashMap` and expecting a fixed output order.

## Practice

1. Add `List<T> shortestPath(T from, T to)` using BFS. Hint: keep a `Map<T, T> parent` that records how you reached each vertex, then walk it backwards from `to`.
2. Add `boolean isConnected()`: run BFS from any vertex and check whether it visited `vertexCount()` vertices.
3. Write `dfsIterative(start)` using an explicit `ArrayStack` instead of recursion. Is the visit order the same? Why or why not?
4. Make a `DirectedGraph<T>` where `addEdge(a, b)` only adds `a → b`. Which methods need to change?
5. *(Challenge)* Add weights to edges and look up **Dijkstra's algorithm** for shortest paths by distance.
