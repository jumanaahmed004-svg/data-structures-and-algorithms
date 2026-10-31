package com.jumana.datastructures.demo;

import com.jumana.datastructures.array.DynamicArray;

final class DynamicArrayDemo {

    private DynamicArrayDemo() {
    }

    static void run(ConsoleInput in) {
        DynamicArray<Integer> list = new DynamicArray<>();

        while (true) {
            System.out.println("\n--- Dynamic Array ---  " + list);
            System.out.println("1.Add  2.Insert  3.Remove  4.Get  5.Clear  0.Back");
            try {
                switch (in.readInt("Choose: ")) {
                    case 1 -> {
                        int value = in.readInt("Enter element: ");
                        list.add(value);
                        System.out.println("Added " + value + " at index " + (list.size() - 1));
                    }
                    case 2 -> {
                        int index = in.readInt("Enter index: ");
                        int value = in.readInt("Enter element: ");
                        list.insert(index, value);
                        System.out.println("Inserted " + value + " at index " + index);
                    }
                    case 3 -> System.out.println("Removed: " + list.remove(in.readInt("Enter index to remove: ")));
                    case 4 -> System.out.println("Element = " + list.get(in.readInt("Enter index: ")));
                    case 5 -> {
                        list.clear();
                        System.out.println("List cleared!");
                    }
                    case 0 -> {
                        return;
                    }
                    default -> System.out.println("Invalid choice!");
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid index! " + e.getMessage());
            }
        }
    }
}
