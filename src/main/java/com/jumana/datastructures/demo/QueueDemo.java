package com.jumana.datastructures.demo;

import com.jumana.datastructures.queue.CircularQueue;

final class QueueDemo {

    private QueueDemo() {
    }

    static void run(ConsoleInput in) {
        CircularQueue<String> queue = new CircularQueue<>(10);

        while (true) {
            System.out.println("\n--- Customer Queue ---  " + queue.size() + "/" + queue.capacity());
            System.out.println("1.Add Customer  2.Serve Next  3.View Queue  0.Back");
            switch (in.readInt("Choose: ")) {
                case 1 -> {
                    if (queue.isFull()) {
                        System.out.println("Queue is FULL! Cannot add new customer.");
                    } else {
                        String name = in.readLine("Enter customer name: ");
                        queue.enqueue(name);
                        System.out.println(name + " added to the queue.");
                    }
                }
                case 2 -> {
                    if (queue.isEmpty()) {
                        System.out.println("Queue is EMPTY! No customers to serve.");
                    } else {
                        System.out.println(queue.dequeue() + " is being served.");
                    }
                }
                case 3 -> System.out.println("Customers in queue (front first): " + queue);
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
