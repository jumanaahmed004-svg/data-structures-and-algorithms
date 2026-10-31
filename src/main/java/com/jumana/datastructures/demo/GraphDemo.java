package com.jumana.datastructures.demo;

import com.jumana.datastructures.graph.Graph;

final class GraphDemo {

    private GraphDemo() {
    }

    static void run(ConsoleInput in) {
        Graph<String> graph = new Graph<>();
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");
        graph.addEdge("C", "D");
        graph.addEdge("D", "E");

        while (true) {
            System.out.println("\n--- Graph ---  (" + graph.vertexCount() + " vertices, "
                    + graph.edgeCount() + " edges)");
            System.out.println("1.Add Edge  2.Show Adjacency List  3.BFS  4.DFS  0.Back");
            try {
                switch (in.readInt("Choose: ")) {
                    case 1 -> {
                        String a = in.readLine("From vertex: ");
                        String b = in.readLine("To vertex: ");
                        graph.addEdge(a, b);
                        System.out.println("Connected " + a + " <-> " + b);
                    }
                    case 2 -> System.out.print(graph);
                    case 3 -> System.out.println("BFS: " + graph.bfs(in.readLine("Start vertex: ")));
                    case 4 -> System.out.println("DFS: " + graph.dfs(in.readLine("Start vertex: ")));
                    case 0 -> {
                        return;
                    }
                    default -> System.out.println("Invalid choice!");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
