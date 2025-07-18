package org.leetcode.solutions;

import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.leetcode.core.LC;

import java.util.List;

@SuppressWarnings("unused")
public class LC268 extends LC<int[], List<Integer>, Integer> {

    /**
     * Singleton instance of {@code LC268}, automatically managed for centralized access.
     * <p>
     * This pattern ensures that each LeetCode problem class has exactly one active instance,
     * which is reused across the CLI, test runner, and other components.
     */
    private static final LC268 INSTANCE = new LC268();

    /**
     * Returns the singleton instance of this LeetCode problem class.
     *
     * @return the unique {@code LC268} instance
     */
    public static LC268 getInstance() {
        return INSTANCE;
    }

    /**
     * Private constructor to enforce singleton instantiation via {@link #getInstance()}.
     * Initializes any metadata or test cases as needed by the {@code LC} framework.
     */
    private LC268() {
        // Initialize any custom state here if needed
    }


    /**
     * Solves the Missing Number problem using arithmetic sum formula.
     *
     * @param nums array of length n with one number missing from [0, n]
     * @return the missing number
     */
    @Override
    public Integer solve(int @NotNull [] nums) {
        int actualSum = 0;
        int expectedSum = (nums.length * (nums.length + 1)) / 2;
        for (int num : nums) actualSum += num;
        return expectedSum - actualSum;
    }

    /**
     * Validates the user-provided input and expected output pair.
     * <p>
     * This implementation checks if the input contains only digits and whitespace
     * (suitable for space-separated integers), and that the expected output is a single digit or integer.
     * <p>
     * Override this method to enforce problem-specific input validation rules.
     *
     * @param userInput an array where index 0 is the raw input, and index 1 is the expected output
     * @return {@code true} if both input and output match the expected format; {@code false} otherwise
     */

    @Override
    public boolean validateUserInput(String @NotNull [] userInput) {
        return userInput[0].matches("^[\\d\\s]+$") && userInput[1].matches("^\\d+$");
    }

    /**
     * Converts raw user input and expected output into valid JSON strings.
     * <p>
     * This implementation transforms space-separated values into JSON arrays,
     * enabling safe deserialization using libraries like Gson or Jackson.
     * Override for problem-specific formatting (e.g., nested arrays or objects).
     *
     * @param userInput a two-element array: index 0 is the input, index 1 is the expected output
     * @return a normalized {@code String[]} with both entries in valid JSON format
     */

    @Override
    public String[] formatUserInput(String @NotNull [] userInput) {
        userInput[0] = GSON.toJson(userInput[0].split("\\s"));
        return userInput;
    }

    /**
     * Parses a raw JSON string representing a list of integers.
     * <p>
     * Example input: {@code "[1, 2, 3, 4]"}
     *
     * @param rawInput the JSON-formatted string to parse
     * @return a {@link List} of {@link Integer} values extracted from the input
     * @throws com.google.gson.JsonSyntaxException if the input is not a valid JSON
     */
    @Override
    public List<Integer> parseInput(String rawInput) {
        return GSON.fromJson(rawInput, new TypeToken<List<Integer>>() {
        }.getType());
    }

    /**
     * Parses the raw expected output string into an {@link Integer}.
     * <p>
     * Example input: {@code "42"}
     *
     * @param rawInput the string representing the expected output
     * @return the parsed {@link Integer} value
     * @throws NumberFormatException if the input is not a valid integer
     */
    @Override
    public Integer parseExpectedOutput(String rawInput) {
        return Integer.valueOf(rawInput);
    }

    /**
     * Compares the expected and actual {@link Integer} values based on content.
     * This method ignores reference equality and performs a value-only comparison.
     *
     * @param expected the expected output value (maybe {@code null})
     * @param actual   the actual computed result (maybe {@code null})
     * @return {@code true} if both values are non-null and numerically equal; {@code false} otherwise
     */
    @Override
    public boolean compare(Integer expected, Integer actual) {
        return expected != null && expected.equals(actual);
    }

    /**
     * Converts a {@link List} of {@link Integer} objects into a primitive {@code int[]} array.
     * <p>
     * This method uses Java Streams to perform the conversion efficiently and concisely.
     * It assumes that the input list is non-null and contains only non-null elements.
     *
     * @param key the list of integers to convert
     * @return a primitive {@code int[]} array containing the same values as the input list
     * @throws NullPointerException if the input list is {@code null} or contains {@code null} elements
     */
    @Override
    public int[] convert(@NotNull List<Integer> key) {
        return key.stream().mapToInt(Integer::intValue).toArray();
    }
}