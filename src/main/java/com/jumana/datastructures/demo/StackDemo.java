package com.jumana.datastructures.demo;

import com.jumana.datastructures.stack.ArrayStack;
import com.jumana.datastructures.stack.PostfixEvaluator;

import java.util.NoSuchElementException;

final class StackDemo {

    private StackDemo() {
    }

    static void run(ConsoleInput in) {
        ArrayStack<Integer> stack = new ArrayStack<>();

        while (true) {
            System.out.println("\n--- Stack ---  " + stack + " (top is on the right)");
            System.out.println("1.Push  2.Pop  3.Peek  4.Postfix Evaluation  0.Back");
            try {
                switch (in.readInt("Choose: ")) {
                    case 1 -> {
                        int value = in.readInt("Enter value: ");
                        stack.push(value);
                        System.out.println("Pushed: " + value);
                    }
                    case 2 -> System.out.println("Popped: " + stack.pop());
                    case 3 -> System.out.println("Top element = " + stack.peek());
                    case 4 -> {
                        String exp = in.readLine("Enter postfix expression (e.g. 5 1 2 + 4 * + 3 -): ");
                        System.out.println("Result = " + PostfixEvaluator.evaluate(exp));
                    }
                    case 0 -> {
                        return;
                    }
                    default -> System.out.println("Invalid choice!");
                }
            } catch (NoSuchElementException e) {
                System.out.println("Stack is EMPTY!");
            } catch (IllegalArgumentException | ArithmeticException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
