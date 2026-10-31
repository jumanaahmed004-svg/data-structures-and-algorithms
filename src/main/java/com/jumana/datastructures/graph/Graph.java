package com.jumana.datastructures.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * An undirected, unweighted graph stored as an adjacency list.
 *
 * <p>Each vertex maps to the set of vertices it is connected to. Vertices and neighbours
 * are kept in insertion order so output is predictable.
 *
 * <table border="1">
 *   <caption>Time complexity (V = vertices, E = edges)</caption>
 *   <tr><th>Operation</th><th>Cost</th></tr>
 *   <tr><td>addVertex / addEdge / hasEdge</td><td>O(1) average</td></tr>
 *   <tr><td>bfs / dfs</td><td>O(V + E)</td></tr>
 * </table>
 *
 * @param <T> the type of the vertex labels
 */
public class Graph<T> {

    private final Map<T, Set<T>> adjacency = new LinkedHashMap<>();

    /** Adds a vertex if it is not already in the graph. */
    public void addVertex(T vertex) {
        adjacency.putIfAbsent(vertex, new LinkedHashSet<>());
    }

    /**
     * Connects {@code a} and {@code b} in both directions, adding either vertex if missing.
     * Adding the same edge twice has no extra effect.
     */
    public void addEdge(T a, T b) {
        addVertex(a);
        addVertex(b);
        adjacency.get(a).add(b);
        adjacency.get(b).add(a);
    }

    /** @return {@code true} if the vertex is in the graph */
    public boolean hasVertex(T vertex) {
        return adjacency.containsKey(vertex);
    }

    /** @return {@code true} if there is an edge between {@code a} and {@code b} */
    public boolean hasEdge(T a, T b) {
        return adjacency.containsKey(a) && adjacency.get(a).contains(b);
    }

    /**
     * @return a read-only view of the vertices connected to {@code vertex}
     * @throws IllegalArgumentException if the vertex is not in the graph
     */
    public Set<T> neighbors(T vertex) {
        requireVertex(vertex);
        return Collections.unmodifiableSet(adjacency.get(vertex));
    }

    /** @return the number of vertices */
    public int vertexCount() {
        return adjacency.size();
    }

    /** @return the number of edges (each undirected edge counted once) */
    public int edgeCount() {
        int endpoints = 0;
        int selfLoops = 0;
        for (Map.Entry<T, Set<T>> entry : adjacency.entrySet()) {
            endpoints += entry.getValue().size();
            if (entry.getValue().contains(entry.getKey())) selfLoops++;
        }
        return (endpoints + selfLoops) / 2;
    }

    /**
     * Breadth-first search: visits vertices in order of their distance from {@code start}.
     *
     * @return the vertices reachable from {@code start}, in visiting order
     * @throws IllegalArgumentException if the vertex is not in the graph
     */
    public List<T> bfs(T start) {
        requireVertex(start);
        List<T> order = new ArrayList<>();
        Set<T> visited = new HashSet<>();
        Queue<T> queue = new ArrayDeque<>();

        visited.add(start);
        queue.add(start);
        while (!queue.isEmpty()) {
            T current = queue.poll();
            order.add(current);
            for (T next : adjacency.get(current)) {
                if (visited.add(next)) {
                    queue.add(next);
                }
            }
        }
        return order;
    }

    /**
     * Depth-first search: follows each path as far as possible before backtracking.
     *
     * @return the vertices reachable from {@code start}, in visiting order
     * @throws IllegalArgumentException if the vertex is not in the graph
     */
    public List<T> dfs(T start) {
        requireVertex(start);
        List<T> order = new ArrayList<>();
        dfs(start, new HashSet<>(), order);
        return order;
    }

    /** @return one line per vertex, e.g. {@code A -> [B, C]} */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<T, Set<T>> entry : adjacency.entrySet()) {
            sb.append(entry.getKey()).append(" -> ").append(entry.getValue()).append(System.lineSeparator());
        }
        return sb.toString();
    }

    private void dfs(T vertex, Set<T> visited, List<T> order) {
        visited.add(vertex);
        order.add(vertex);
        for (T next : adjacency.get(vertex)) {
            if (!visited.contains(next)) {
                dfs(next, visited, order);
            }
        }
    }

    private void requireVertex(T vertex) {
        if (!adjacency.containsKey(vertex)) {
            throw new IllegalArgumentException("Vertex not in graph: " + vertex);
        }
    }
}
