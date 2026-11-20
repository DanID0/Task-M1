package org.example.util;

import java.util.Scanner;

public class ConsoleInput {
    private final Scanner scanner = new Scanner(System.in);

    public Integer readInt() {
        if (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number.");
            scanner.nextLine();
            return null;
        }
        int number = scanner.nextInt();
        scanner.nextLine();
        return number;
    }

    public String readLine() {
        String line = scanner.nextLine();
        return line;
    }
}
