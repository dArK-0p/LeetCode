package org.leetcode.solutions;


import org.jetbrains.annotations.NotNull;
import org.leetcode.core.LC;

@SuppressWarnings("unused")
public class LC1688 extends LC<Integer, Integer, Integer> {

    /**
     * Calculates the number of matches in a tournament.
     * In a tournament with n teams, there are exactly n-1 matches needed
     * to determine the winner (each match eliminates exactly one team).
     *
     * @param input the number of teams in the tournament
     * @return the number of matches needed
     */
    @Override
    public Integer solve(Integer input) {
        return input - 1;
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
     * @param userInput a string array where index 0 is input and index 1 is expected output
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