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
public class LC2016 implements Problem<int[], Integer> {

    static {
        new TestCases(new LC2016());
    }

    private static final Gson gson = new Gson();

    /**
     * Finds the maximum difference between two elements such that the larger element
     * appears after the smaller element in the array.
     *
     * @param nums the input array
     * @return the maximum difference, or -1 if no valid pair exists
     */
    @Override
    public Integer solve(int[] nums) {
        int minVal = nums[0];
        int maxDiff = -1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > minVal) {
                maxDiff = Math.max(maxDiff, nums[i] - minVal);
            } else {
                minVal = nums[i];
            }
        }

        return maxDiff;
    }

    /**
     * Processes user input with enhanced error handling and validation.
     */
    @Override
    public void processUserInput() {
        try (Scanner scanner = new Scanner(System.in)) {
            displayInputHelp();

            System.out.println("🔹 Enter space-separated integers for maximum difference problem:");
            System.out.print(INPUT_ARROW);

            String input = scanner.nextLine().trim();

            try {
                int[] nums = parseIntegerArray(input);

                if (nums.length < 2) {
                    System.out.println(OUTPUT_ERROR + " Array must have at least 2 elements.");
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
     * Executes all predefined test cases with enhanced statistics.
     */
    @Override
    public void runTestCases() {
        System.out.println("📘 Running test cases for: LC" + getId());

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

        printTestCaseStatistics();
    }

    /**
     * Converts raw test case strings into typed test data.
     */
    @Override
    public Map<List<Integer>, Integer> fetchTestCases(Map<String, String> testCases) {
        Map<List<Integer>, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : testCases.entrySet()) {
            try {
                List<Integer> input = parseInput(entry.getKey());
                Integer output = parseInteger(entry.getValue());
                result.put(input, output);
            } catch (Exception e) {
                System.err.printf("⚠️  Skipping invalid test case: '%s' → '%s'%n",
                        entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    /**
     * Parses a JSON-formatted string into a list of integers.
     */
    private List<Integer> parseInput(String rawInput) {
        return gson.fromJson(rawInput, new TypeToken<List<Integer>>() {
        }.getType());
    }

    /**
     * Converts List<Integer> to int[].
     */
    private int[] toIntArray(List<Integer> list) {
        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * Displays problem-specific input help.
     */
    @Override
    public void displayInputHelp() {
        System.out.println("ℹ️  Maximum Difference Problem Help:");
        System.out.println("   • Enter space-separated integers (e.g., 7 1 5 4)");
        System.out.println("   • Array must have at least 2 elements");
        System.out.println("   • Finds max difference where larger element comes after smaller");
        System.out.println("   • Returns -1 if no valid increasing pair exists");
        System.out.println("   • Example: [7,1,5,4] → 4 (5-1=4)");
        System.out.println("   • Example: [9,4,3,2] → -1 (no increasing pair)");
    }
}