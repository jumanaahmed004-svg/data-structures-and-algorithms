package com.jumana.datastructures.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    private Graph<String> graph;

    /*
     *   A --- B
     *   |     |
     *   C --- D --- E
     */
    @BeforeEach
    void setUp() {
        graph = new Graph<>();
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");
        graph.addEdge("C", "D");
        graph.addEdge("D", "E");
    }

    @Test
    void edgesAreUndirected() {
        assertTrue(graph.hasEdge("A", "B"));
        assertTrue(graph.hasEdge("B", "A"));
        assertFalse(graph.hasEdge("A", "E"));
    }

    @Test
    void countsVerticesAndEdges() {
        assertEquals(5, graph.vertexCount());
        assertEquals(5, graph.edgeCount());
    }

    @Test
    void duplicateEdgeIsIgnored() {
        graph.addEdge("A", "B");
        graph.addEdge("B", "A");
        assertEquals(5, graph.edgeCount());
        assertEquals(Set.of("B", "C"), graph.neighbors("A"));
    }

    @Test
    void addVertexWithoutEdges() {
        graph.addVertex("Z");
        assertTrue(graph.hasVertex("Z"));
        assertTrue(graph.neighbors("Z").isEmpty());
    }

    @Test
    void breadthFirstSearch() {
        assertEquals(List.of("A", "B", "C", "D", "E"), graph.bfs("A"));
    }

    @Test
    void depthFirstSearch() {
        assertEquals(List.of("A", "B", "D", "C", "E"), graph.dfs("A"));
    }

    @Test
    void unreachableVerticesAreNotVisited() {
        graph.addEdge("X", "Y");
        assertEquals(List.of("X", "Y"), graph.bfs("X"));
    }

    @Test
    void unknownVertexThrows() {
        assertThrows(IllegalArgumentException.class, () -> graph.bfs("Q"));
        assertThrows(IllegalArgumentException.class, () -> graph.neighbors("Q"));
    }

    @Test
    void toStringShowsAdjacencyList() {
        String text = graph.toString();
        assertTrue(text.contains("A -> [B, C]"));
        assertTrue(text.contains("D -> [B, C, E]"));
    }
}
