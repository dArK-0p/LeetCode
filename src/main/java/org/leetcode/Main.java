package org.leetcode;

import org.leetcode.core.Problem;
import org.leetcode.solutions.LC268;

import java.util.Scanner;

/**
 * Entry point to test and interact with a LeetCode-style problem class.
 * This example is bound to LC268. Replace it with other implementations as needed.
 */
public class Main {

    public static void main(String[] args) {
        Problem<?, ?> problem = new LC268(); // Can be changed to any Problem implementation

        problem.printDetails(); // Print metadata and visible test cases

        System.out.println("\nChoose an option:");
        System.out.println("1. Run all test cases");
        System.out.println("2. Enter custom input");
        System.out.println("3. Exit");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter choice (1/2/3): ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1" -> {
                System.out.println("\nRunning all test cases...");
                problem.runTestCases();
            }
            case "2" -> {
                System.out.println("\nEnter your custom input:");
                problem.processUserInput();
            }
            case "3" -> System.out.println("Exiting.");
            default -> System.out.println("❌ Invalid choice. Exiting.");
        }
    }
}
