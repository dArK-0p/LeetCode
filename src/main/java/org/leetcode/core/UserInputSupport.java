package org.leetcode.core;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

/// Interface for handling interactive user input for a LeetCode problem.
interface UserInputSupport {

    /// Constants
    int MAX_TEST_CASES = 10;
    int MAX_INVALID_ATTEMPTS = 3;

    /**
     * Accepts input from the user via the console, parses it into the appropriate type,
     * invokes the solution logic, and displays the result.
     * <p>
     * Implementations are encouraged to handle invalid input and parsing errors gracefully.
     */
    void runUserTestCases();

    /**
     * Validates the raw user-provided input and expected output.
     * Implementations should define custom rules to ensure the input
     * matches the expected problem format (e.g., valid integers, arrays, strings).
     *
     * @param userInput an array where index 0 is the input, and index 1 is the expected output
     * @return {@code true} if the input is valid; {@code false} otherwise
     */
    boolean validateUserInput(String[] userInput);

    /**
     * Formats and normalizes the raw user-provided input-output pair to conform to JSON formatting.
     * <p>
     * This method ensures that both the input and expected output strings are valid JSON representations,
     * allowing them to be safely deserialized using a JSON parser like Gson or Jackson.
     * Implementations can apply trimming, quoting, array/object wrapping, or escaping as needed.
     *
     * @param userInput a raw input-output pair entered by the user; index 0 is input, index 1 is expected output
     * @return a normalized {@code String[]} where both entries are guaranteed to be valid JSON strings
     */
    String[] formatUserInput(String[] userInput);

    /**
     * Reads and collects multiple user test cases from the console.
     * Prompts for the number of test cases, then repeatedly fetches input and expected output pairs.
     *
     * @return a map where keys are raw input strings and values are expected output strings
     */
    default void getUserInput() {
        try (Scanner scanner = new Scanner(System.in)) {
            int testCaseLimit = promptTestCaseLimit(scanner);

            TestCases.user = new HashMap<>(testCaseLimit);
            System.out.printf("%n%s%n", "🔹 Enter your test cases:");

            collectTestCases(scanner, testCaseLimit);

            System.out.println("📊 Total No. of Test Cases = " + TestCases.user.size());
        } catch (InputMismatchException e) {
            System.out.println("❌ Please enter a valid number for the test case count.");
        } catch (Exception e) {
            System.out.println("❌ Unexpected error during user input: " + e.getMessage());
        }
    }

    /**
     * Prompts the user to enter the number of test cases and validates the input.
     *
     * @param scanner the Scanner instance to read user input.
     * @return the number of test cases to process.
     */
    private int promptTestCaseLimit(Scanner scanner) {
        System.out.print("Enter no. of test cases: [1 - " + MAX_TEST_CASES + "] ");
        int testCaseLimit = scanner.nextInt();
        scanner.nextLine(); // Consume leftover newline

        if (testCaseLimit > MAX_TEST_CASES) {
            System.err.println("Input exceeds " + MAX_TEST_CASES + ". Defaulting to " + MAX_TEST_CASES + " test cases.");
            testCaseLimit = MAX_TEST_CASES;
        }

        return testCaseLimit;
    }

    /**
     * Collects user test cases by validating and formatting inputs.
     *
     * @param scanner       the Scanner instance to read inputs.
     * @param testCaseLimit the maximum number of test cases to process.
     */
    private void collectTestCases(Scanner scanner, int testCaseLimit) {
        int invalidInputCount = 0;

        while (TestCases.user.size() < testCaseLimit && invalidInputCount < MAX_INVALID_ATTEMPTS) {
            System.out.printf("Test Case #%d:%n", TestCases.user.size() + 1);
            String[] userInput = fetchUserInput(scanner);

            if (!validateUserInput(userInput)) {
                System.out.println("❌ Invalid input. Please try again.");
                invalidInputCount++;
                continue;
            }

            String[] formattedInput = formatUserInput(userInput);
            TestCases.user.put(formattedInput[0], formattedInput[1]);
        }
    }

    /**
     * Reads a single test case from the user, consisting of an input and an expected output.
     * Uses the provided scanner to fetch console input.
     *
     * @param scanner the Scanner instance to read from
     * @return a String array of size 2: index 0 contains the input, index 1 contains the expected output
     */
    private String[] fetchUserInput(Scanner scanner) {
        String[] userInput = new String[2];
        System.out.println("↪️  Input: ");
        userInput[0] = scanner.nextLine().trim();
        System.out.println("↪️  Expected Output: ");
        userInput[1] = scanner.nextLine().trim();
        return userInput;
    }
}