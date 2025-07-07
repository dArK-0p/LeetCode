package org.leetcode.solutions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.leetcode.core.Problem;
import org.leetcode.core.TestCases;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@SuppressWarnings({"InstantiationOfUtilityClass", "unused"})
public class LC268 implements Problem<int[], Integer> {
    static {
        new TestCases(new LC268());
    } // Initialize Test Cases

    private static final Gson gson = new Gson();

    /**
     * Fetches test cases by converting serialized string representations
     * of inputs and outputs into their appropriate types.
     *
     * @param testCases a map where each key is a serialized input and the value is the expected output
     * @return a map containing deserialized test cases as input-output pairs
     */
    @Override
    public Map<List<Integer>, Integer> fetchTestCases(Map<String, String> testCases) {
        Map<List<Integer>, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : testCases.entrySet()) {
            List<Integer> input = parseInput(entry.getKey());
            Integer output = Integer.parseInt(entry.getValue());
            result.put(input, output);
        }
        return result;
    }

    /**
     * Parses a JSON-formatted string into a list of integers.
     *
     * @param rawInput the JSON string representing the input array
     * @return a list of integers representing the deserialized input
     */
    private List<Integer> parseInput(String rawInput) {
        return gson.fromJson(rawInput, new TypeToken<List<Integer>>() {
        }.getType());
    }

    /**
     * Solves the Missing Number problem using arithmetic sum formula.
     *
     * @param nums array of length n with one number missing from [0, n]
     * @return the missing number
     */
    @Override
    public Integer solve(int[] nums) {
        int actualSum = 0;
        int expectedSum = (nums.length * (nums.length + 1)) / 2;
        for (int num : nums) actualSum += num;
        return expectedSum - actualSum;
    }

    /**
     * Combines visible and hidden test cases and validates against all.
     * Enhanced with statistics reporting.
     */
    @Override
    public void runTestCases() {
        System.out.println("📘 Running test cases for: LC" + getId());

        // Check if test cases are available
        if (hasAnyTestCases()) {
            System.out.println(EMPTY_TEST_CASES_MESSAGE);
            return;
        }

        Map<List<Integer>, Integer> allTestCases = new LinkedHashMap<>();
        allTestCases.putAll(fetchTestCases(TestCases.visible));
        allTestCases.putAll(fetchTestCases(TestCases.hidden));

        for (Map.Entry<List<Integer>, Integer> entry : allTestCases.entrySet()) {
            int[] input = toIntArray(entry.getKey());
            int expected = entry.getValue();
            int actual = solve(input);
            System.out.printf("Input: %-20s | Expected: %-5d | Got: %-5d | %s%n",
                    entry.getKey(), expected, actual, (expected == actual) ? "✅" : "❌");
        }

        // Print statistics
        printTestCaseStatistics();
    }

    /**
     * Processes user input with enhanced error handling and help information.
     * Uses the new interface utility methods for better consistency.
     */
    @Override
    public void processUserInput() {
        try (Scanner scanner = new Scanner(System.in)) {
            displayInputHelp();

            System.out.println("🔹 Enter space-separated numbers for the missing number problem (e.g., 3 0 1):");
            System.out.print(INPUT_ARROW);

            String input = scanner.nextLine().trim();

            try {
                int[] nums = parseIntegerArray(input);

                if (nums.length == 0) {
                    System.out.println(OUTPUT_ERROR + " Array cannot be empty for this problem.");
                    return;
                }

                // Validate input for the missing number problem
                if (!isValidMissingNumberInput(nums)) {
                    System.out.println(OUTPUT_ERROR + " Invalid input: array should contain distinct numbers in range [0, n].");
                    return;
                }

                int result = solve(nums);
                System.out.println(OUTPUT_SUCCESS + result);

            } catch (NumberFormatException e) {
                System.out.println(OUTPUT_ERROR + " " + e.getMessage());
            }
        }
    }

    /**
     * Validates input for the missing number problem.
     * The array should contain distinct numbers from [0, n] with exactly one missing.
     *
     * @param nums the input array
     * @return true if the input is valid for the missing number problem
     */
    private boolean isValidMissingNumberInput(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }

        // Check if all numbers are in valid range [0, n]
        int n = nums.length;
        for (int num : nums) {
            if (num < 0 || num > n) {
                return false;
            }
        }

        // Check for duplicates (simple approach)
        boolean[] seen = new boolean[n + 1];
        for (int num : nums) {
            if (seen[num]) {
                return false; // Duplicate found
            }
            seen[num] = true;
        }

        return true;
    }

    /**
     * Displays problem-specific input help.
     */
    @Override
    public void displayInputHelp() {
        System.out.println("ℹ️  Missing Number Problem Help:");
        System.out.println("   • Enter space-separated numbers (e.g., 3 0 1)");
        System.out.println("   • Numbers should be distinct and in range [0, n]");
        System.out.println("   • Array should have exactly one missing number");
        System.out.println("   • Example: '3 0 1' has missing number 2");
        System.out.println("   • Example: '1 2' has missing number 0");
    }

    /**
     * Converts List[Integer] to int[].
     */
    private int[] toIntArray(List<Integer> list) {
        return list.stream().mapToInt(Integer::intValue).toArray();
    }
}