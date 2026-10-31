package com.jumana.datastructures.demo;

import java.util.Scanner;

/** Small helpers for reading user input safely from the console. */
final class ConsoleInput {

    private final Scanner scanner;

    ConsoleInput(Scanner scanner) {
        this.scanner = scanner;
    }

    /** Keeps asking until the user types a whole number. */
    int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    /** Reads a full line of text. */
    String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
