package com.jumana.datastructures.demo;

import com.jumana.datastructures.tree.BinaryTree;

final class TreeDemo {

    private TreeDemo() {
    }

    static void run(ConsoleInput in) {
        BinaryTree<Integer> tree = new BinaryTree<>();
        for (int x : new int[] {10, 6, 15, 3, 8, 20, 9}) {
            tree.insert(x);
        }

        while (true) {
            System.out.println("\n--- Binary Tree ---  (" + tree.size() + " nodes)");
            System.out.println("1.Insert  2.Show traversals  0.Back");
            switch (in.readInt("Choose: ")) {
                case 1 -> tree.insert(in.readInt("Enter value: "));
                case 2 -> {
                    System.out.println("Level order: " + tree.levelOrder());
                    System.out.println("Preorder:    " + tree.preOrder());
                    System.out.println("Inorder:     " + tree.inOrder());
                    System.out.println("Postorder:   " + tree.postOrder());
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
