package com.jumana.datastructures.demo;

import com.jumana.datastructures.linkedlist.SinglyLinkedList;

import java.util.NoSuchElementException;

final class LinkedListDemo {

    private LinkedListDemo() {
    }

    static void run(ConsoleInput in) {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();

        while (true) {
            System.out.println("\n--- Singly Linked List ---  " + list);
            System.out.println("1.AddFirst  2.AddLast  3.RemoveFirst  4.RemoveLast");
            System.out.println("5.RemoveElement  6.Contains  7.Show  0.Back");
            try {
                switch (in.readInt("Choose: ")) {
                    case 1 -> list.addFirst(in.readInt("Enter value: "));
                    case 2 -> list.addLast(in.readInt("Enter value: "));
                    case 3 -> System.out.println("Removed first element: " + list.removeFirst());
                    case 4 -> System.out.println("Removed last element: " + list.removeLast());
                    case 5 -> {
                        int value = in.readInt("Enter value to remove: ");
                        System.out.println(list.remove(value) ? "Removed " + value : "Value not found!");
                    }
                    case 6 -> {
                        int value = in.readInt("Enter value to search: ");
                        System.out.println(list.contains(value) ? "Value " + value + " found!" : "Value not found!");
                    }
                    case 7 -> {
                        System.out.println("List elements: " + list);
                        if (!list.isEmpty()) {
                            System.out.println("START = " + list.getFirst() + " , END = " + list.getLast());
                        }
                    }
                    case 0 -> {
                        return;
                    }
                    default -> System.out.println("Invalid choice!");
                }
            } catch (NoSuchElementException e) {
                System.out.println("List is empty!");
            }
        }
    }
}
