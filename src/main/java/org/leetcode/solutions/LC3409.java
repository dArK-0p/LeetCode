package org.leetcode.solutions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.leetcode.core.Problem;
import org.leetcode.core.TestCases;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@SuppressWarnings({"InstantiationOfUtilityClass", "unused"})
public class LC3409 implements Problem<int[], Integer> {

    static {
        new TestCases(new LC3409());
    }

    private static final Gson gson = new Gson();

    /**
     * Finds the length of the longest decreasing subsequence.
     * Uses dynamic programming to track the longest subsequence ending at each value.
     *
     * @param nums the input array
     * @return the length of the longest decreasing subsequence
     */
    @Override
    public Integer solve(int[] nums) {
        Map<Integer, Integer> dp = new HashMap<>();
        int maxLen = 0;

        for (int num : nums) {
            int len = dp.getOrDefault(num + 1, 0) + 1;
            dp.put(num, Math.max(dp.getOrDefault(num, 0), len));
            maxLen = Math.max(maxLen, dp.get(num));
        }

        return maxLen;
    }

    /**
     * Processes user input with enhanced error handling and validation.
     */
    @Override
    public void processUserInput() {
        try (Scanner scanner = new Scanner(System.in)) {
            displayInputHelp();

            System.out.println("🔹 Enter space-separated integers for longest decreasing subsequence:");
            System.out.print(INPUT_ARROW);

            String input = scanner.nextLine().trim();

            try {
                int[] nums = parseIntegerArray(input);

                if (nums.length == 0) {
                    System.out.println(OUTPUT_ERROR + " Array cannot be empty.");
                    return;
                }

                if (nums.length > 1000) {
                    System.out.println(OUTPUT_ERROR + " Array size should not exceed 1000 for optimal performance.");
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
            System.out.printf("Input: %-25s | Expected: %-3d | Got: %-3d | %s%n",
                    formatArrayForDisplay(entry.getKey()), expected, actual,
                    (expected == actual) ? "✅" : "❌");
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
     * Formats array for display, truncating if too long.
     */
    private String formatArrayForDisplay(List<Integer> list) {
        if (list.size() <= 8) {
            return list.toString();
        }
        return list.subList(0, 5) + "..." + list.subList(list.size() - 2, list.size());
    }

    /**
     * Displays problem-specific input help.
     */
    @Override
    public void displayInputHelp() {
        System.out.println("ℹ️  Longest Decreasing Subsequence Help:");
        System.out.println("   • Enter space-separated integers (e.g., 5 3 8 2 1 4)");
        System.out.println("   • Array can contain 1 to 1000 elements");
        System.out.println("   • Finds longest subsequence where each element is smaller than previous");
        System.out.println("   • Subsequence doesn't need to be contiguous");
        System.out.println("   • Example: [5,3,8,2,1,4] → 4 (subsequence: 8→5→3→2 or 8→5→3→1)");
        System.out.println("   • Example: [1,2,3,4] → 1 (no decreasing subsequence longer than 1)");
        System.out.println("   • Example: [4,3,2,1] → 4 (entire array is decreasing)");
    }
}