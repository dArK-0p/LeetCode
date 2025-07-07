package org.leetcode.solutions;

import org.leetcode.core.Problem;
import org.leetcode.core.TestCases;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

@SuppressWarnings({"InstantiationOfUtilityClass", "unused"})
public class LC7 implements Problem<Integer, Integer> {
    private static final int INTEGER_MAX_VALUE = Integer.MAX_VALUE;
    private static final int INTEGER_MIN_VALUE = Integer.MIN_VALUE;

    static {
        new TestCases(new LC7()); // Load visible and hidden test cases
    }

    /**
     * Reverses the digits of the given integer.
     * Returns 0 if the result overflows a 32-bit signed integer.
     *
     * @param input the integer to reverse
     * @return the reversed integer, or 0 if overflow occurs
     */
    @Override
    public Integer solve(Integer input) {
        long reversedValue = 0;
        int workingInput = input;

        while (workingInput != 0) {
            reversedValue = reversedValue * 10 + workingInput % 10;
            workingInput /= 10;
        }

        return (reversedValue < INTEGER_MIN_VALUE || reversedValue > INTEGER_MAX_VALUE)
                ? 0
                : (int) reversedValue;
    }

    /**
     * Executes all predefined test cases (visible and hidden),
     * printing results to the console with enhanced statistics.
     */
    @Override
    public void runTestCases() {
        System.out.println("📘 Running test cases for: LC" + getId());

        // Check if test cases are available
        if (hasAnyTestCases()) {
            System.out.println(EMPTY_TEST_CASES_MESSAGE);
            return;
        }

        Map<Integer, Integer> allTestCases = getAllTestCases();

        for (Map.Entry<Integer, Integer> testCase : allTestCases.entrySet()) {
            executeTestCase(testCase.getKey(), testCase.getValue());
        }

        // Print statistics using the new interface method
        printTestCaseStatistics();
    }

    /**
     * Merges visible and hidden test cases into a single map.
     *
     * @return a map containing all test cases
     */
    private Map<Integer, Integer> getAllTestCases() {
        Map<Integer, Integer> allTestCases = new LinkedHashMap<>();
        allTestCases.putAll(fetchTestCases(TestCases.visible));
        allTestCases.putAll(fetchTestCases(TestCases.hidden));
        return allTestCases;
    }

    /**
     * Executes a single test case and prints the result.
     *
     * @param input    the test input
     * @param expected the expected output
     */
    private void executeTestCase(int input, int expected) {
        int actual = solve(input);
        System.out.printf(
                "Input: %-11d | Expected: %-11d | Got: %-11d | %s%n",
                input, expected, actual,
                (expected == actual ? "✅" : "❌")
        );
    }

    /**
     * Converts raw test case strings into typed integer pairs.
     *
     * @param testCases a map of raw string test cases
     * @return a map of parsed integer test cases
     */
    @Override
    public Map<Integer, Integer> fetchTestCases(Map<String, String> testCases) {
        Map<Integer, Integer> parsedTestCases = new LinkedHashMap<>();

        for (Map.Entry<String, String> entry : testCases.entrySet()) {
            try {
                int inputValue = parseInteger(entry.getKey());
                int outputValue = parseInteger(entry.getValue());
                parsedTestCases.put(inputValue, outputValue);
            } catch (NumberFormatException e) {
                System.err.printf("⚠️  Skipping invalid test case: '%s' → '%s'%n",
                        entry.getKey(), entry.getValue());
            }
        }
        return parsedTestCases;
    }

    /**
     * Accepts user input from the console, reverses the number,
     * and prints the result with enhanced error handling.
     */
    @Override
    public void processUserInput() {
        try (Scanner scanner = new Scanner(System.in)) {
            displayInputHelp();

            System.out.println("🔹 Enter an integer to reverse:");
            System.out.print(INPUT_ARROW);

            String userInput = scanner.nextLine().trim();

            try {
                int inputNumber = parseInteger(userInput);

                // Validate input range for integer reversal
                if (!isValidIntegerForReversal(inputNumber)) {
                    System.out.println(OUTPUT_ERROR + " Input might cause overflow issues.");
                    return;
                }

                int result = solve(inputNumber);
                System.out.println(OUTPUT_SUCCESS + result);

            } catch (NumberFormatException e) {
                System.out.println(OUTPUT_ERROR + " " + e.getMessage());
            }
        }
    }

    /**
     * Validates if an integer is suitable for reversal without causing overflow concerns.
     * This is a basic validation - the actual overflow handling is in the solve method.
     *
     * @param input the integer to validate
     * @return true if the input is valid for processing
     */
    private boolean isValidIntegerForReversal(int input) {
        // Basic validation - could be enhanced with more sophisticated checks
        return input >= Integer.MIN_VALUE && input <= Integer.MAX_VALUE;
    }

    /**
     * Displays problem-specific input help.
     */
    @Override
    public void displayInputHelp() {
        System.out.println("ℹ️  Reverse Integer Problem Help:");
        System.out.println("   • Enter a signed 32-bit integer");
        System.out.println("   • Valid range: " + Integer.MIN_VALUE + " to " + Integer.MAX_VALUE);
        System.out.println("   • Returns 0 if reversed number would overflow");
        System.out.println("   • Example inputs: 123 → 321, -456 → -654, 120 → 21");
        System.out.println("   • Overflow examples: 1563847412 → 0");
    }
}