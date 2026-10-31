package com.jumana.datastructures.demo;

import java.util.Scanner;

/**
 * Entry point: an interactive console menu for trying out each data structure.
 *
 * <p>Run it from VS Code (click "Run" above {@code main}) or with {@code mvn compile exec:java}.
 */
public class Main {

    public static void main(String[] args) {
        ConsoleInput in = new ConsoleInput(new Scanner(System.in));

        while (true) {
            System.out.println("\n===== Data Structures and Algorithms in Java =====");
            System.out.println("1. Dynamic Array        2. Singly Linked List");
            System.out.println("3. Stack (+ postfix)    4. Circular Queue");
            System.out.println("5. Binary Tree          6. Graph");
            System.out.println("0. Exit");

            switch (in.readInt("Choose: ")) {
                case 1 -> DynamicArrayDemo.run(in);
                case 2 -> LinkedListDemo.run(in);
                case 3 -> StackDemo.run(in);
                case 4 -> QueueDemo.run(in);
                case 5 -> TreeDemo.run(in);
                case 6 -> GraphDemo.run(in);
                case 0 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
