package org.leetcode;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.*;

public class LC268 implements Problem<int[], Integer> {

    static { new TestCases(new LC268()); }

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
        return new Gson().fromJson(rawInput, new TypeToken<List<Integer>>() {}.getType());
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
     */
    @Override
    public void runTestCases() {
        System.out.println("Running test cases for: " + getId());
        Map<List<Integer>, Integer> allTestCases = new LinkedHashMap<>();
        allTestCases.putAll(fetchTestCases(TestCases.visible));
        allTestCases.putAll(fetchTestCases(TestCases.hidden));

        for (Map.Entry<List<Integer>, Integer> entry : allTestCases.entrySet()) {
            int[] input = toIntArray(entry.getKey());
            int expected = entry.getValue();
            int actual = solve(input);
            System.out.printf("Input: %s | Expected: %d | Got: %d | %s%n",
                    entry.getKey(), expected, actual, (expected == actual) ? "✅" : "❌");
        }
    }

    /**
     * Accepts input from the user, processes it, and evaluates the solution.
     */
    @Override
    public void processUserInput() {
        Scanner scanner = new Scanner(System.in);
        Gson gson = new Gson();

        System.out.println("🔹 Enter the array of numbers in JSON format (e.g., [3,0,1]):");

        String inputJson = scanner.nextLine().trim();

        int[] nums;
        try {
            nums = gson.fromJson(inputJson, int[].class);
        } catch (Exception e) {
            System.out.println("❌ Invalid JSON input for array: " + e.getMessage());
            return;
        }

        int result = solve(nums);
        System.out.println("✅ Output: " + result);
    }


    /**
     * Converts List[Integer] to int[].
     */
    private int[] toIntArray(List<Integer> list) {
        return list.stream().mapToInt(Integer::intValue).toArray();
    }
}
