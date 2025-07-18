package org.leetcode.solutions;


import org.jetbrains.annotations.NotNull;
import org.leetcode.core.LC;

@SuppressWarnings("unused")
public class LC7 extends LC<Integer, Integer, Integer> {

    /**
     * Singleton instance of {@code LC7}, automatically managed for centralized access.
     * <p>
     * This pattern ensures that each LeetCode problem class has exactly one active instance,
     * which is reused across the CLI, test runner, and other components.
     */
    private static final LC7 INSTANCE = new LC7();

    /**
     * Returns the singleton instance of this LeetCode problem class.
     *
     * @return the unique {@code LC7} instance
     */
    public static LC7 getInstance() {
        return INSTANCE;
    }

    /**
     * Private constructor to enforce singleton instantiation via {@link #getInstance()}.
     * Initializes any metadata or test cases as needed by the {@code LC} framework.
     */
    private LC7() {
        // Initialize any custom state here if needed
    }

    /**
     * Reverses the digits of a given integer.
     * <p>
     * If the reversed integer exceeds the bounds of a 32-bit signed integer,
     * the method returns 0 to indicate overflow.
     *
     * @param input the integer to be reversed
     * @return the reversed integer or 0 if overflow occurs
     */
    @Override
    public Integer solve(Integer input) {
        long reversedValue = 0;
        int workingInput = input;

        while (workingInput != 0) {
            reversedValue = reversedValue * 10 + workingInput % 10;
            workingInput /= 10;
        }

        return (reversedValue < Integer.MIN_VALUE || reversedValue > Integer.MAX_VALUE) ? 0 : (int) reversedValue;
    }

    /**
     * Parses a raw input string representing an integer value.
     * <p>
     * This method is used to convert input defined in JSON or metadata files
     * into the internal type used by the solver.
     *
     * @param rawInput the raw string input (e.g., "123")
     * @return the parsed integer input
     * @throws NumberFormatException if the input is not a valid integer
     */
    @Override
    public Integer parseInput(String rawInput) {
        return Integer.valueOf(rawInput);
    }

    /**
     * Parses the expected output string into an integer.
     * <p>
     * Used during test case evaluation to convert expected outputs from
     * metadata files into the internal output type.
     *
     * @param rawInput the expected output string (e.g., "321")
     * @return the parsed expected output as an integer
     * @throws NumberFormatException if the string is not a valid integer
     */
    @Override
    public Integer parseExpectedOutput(String rawInput) {
        return Integer.valueOf(rawInput);
    }

    /**
     * Converts parsed raw input into solver-usable format.
     * <p>
     * Since the raw input is already of type {@code Integer}, this method
     * simply returns the same value without modification.
     *
     * @param rawInput the parsed input value
     * @return the unchanged input value
     */
    @Override
    public Integer convert(Integer rawInput) {
        return rawInput;
    }

    /**
     * Compares the expected and actual integer outputs for equality.
     * <p>
     * This implementation performs a null-safe check using {@code equals()}.
     *
     * @param expected the expected output value
     * @param actual   the actual output value from {@code solve}
     * @return {@code true} if both values are equal, {@code false} otherwise
     */

    @Override
    public boolean compare(Integer expected, Integer actual) {
        return expected != null && expected.equals(actual);
    }

    /**
     * Validates user-provided input and output values to ensure they conform
     * to the expected format for this problem.
     * <p>
     * This problem requires both the input and output to be valid integers,
     * represented as digit-only strings.
     *
     * @param userInput a string array where index 0 is input and index 1 is the expected output
     * @return {@code true} if both values are digit-only strings, {@code false} otherwise
     */
    @Override
    public boolean validateUserInput(String @NotNull [] userInput) {
        return userInput[0].matches("^\\d+$") && userInput[1].matches("^\\d+$");
    }

    /**
     * Returns the user input pair without modification.
     * <p>
     * Since the problem uses simple integer types, no normalization or
     * transformation is required on user input.
     *
     * @param userInput an array with input at index 0 and expected output at index 1
     * @return the same input-output pair
     */
    @Override
    public String[] formatUserInput(String[] userInput) {
        return userInput;
    }
}