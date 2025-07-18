package org.leetcode.core;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

/// Interface for handling interactive user input for a LeetCode problem.
interface UserInputSupport {

    /// Constants
    int MAX_TEST_CASES = 10;
    int MAX_INVALID_ATTEMPTS = 3;
    Scanner SCANNER = new Scanner(System.in);

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
     */
    default void getUserInput() {
        try {
            int testCaseLimit = promptTestCaseLimit();

            TestCases.user = new HashMap<>(testCaseLimit);
            System.out.printf("%n%s%n", "🔹 Enter your test cases:");

            collectTestCases(testCaseLimit);
        } catch (InputMismatchException e) {
            System.out.println("❌ Please enter a valid number for the test case count.");
        } catch (Exception e) {
            System.out.println("❌ Unexpected error during user input: " + e.getMessage());
        }
    }

    /**
     * Prompts the user to enter the number of test cases and validates the input.
     * @return the number of test cases to process.
     */
    private int promptTestCaseLimit() {
        System.out.print("Enter no. of test cases: [1 - " + MAX_TEST_CASES + "] ");
        int testCaseLimit = SCANNER.nextInt(); SCANNER.nextLine();

        if (testCaseLimit > MAX_TEST_CASES) {
            System.err.println("Input exceeds " + MAX_TEST_CASES + ". Defaulting to " + MAX_TEST_CASES + " test cases.");
            testCaseLimit = MAX_TEST_CASES;
        }

        return testCaseLimit;
    }

    /**
     * Collects user test cases by validating and formatting inputs.
     *
     * @param testCaseLimit the maximum number of test cases to process.
     */
    private void collectTestCases(int testCaseLimit) {
        int invalidInputCount = 0;

        while (TestCases.user.size() < testCaseLimit && invalidInputCount < MAX_INVALID_ATTEMPTS) {
            System.out.printf("Test Case #%d:%n", TestCases.user.size() + 1);
            String[] userInput = fetchUserInput();

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
     * @return a String array of size 2: index 0 contains the input, index 1 contains the expected output
     */
    private String @NotNull [] fetchUserInput() {
        String[] userInput = new String[2];

        System.out.println("↪️  Input: ");
        userInput[0] = SCANNER.nextLine().trim();

        System.out.println("↪️  Expected Output: ");
        userInput[1] = SCANNER.nextLine().trim();

        return userInput;
    }
}