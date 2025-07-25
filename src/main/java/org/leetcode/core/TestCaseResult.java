package org.leetcode.core;

import org.jetbrains.annotations.NotNull;

/**
 * Represents the result of executing a test case on a LeetCode problem.
 * Stores the test input, expected output, actual output, and pass/fail status.
 * <p>
 * Also tracks global test statistics across all instances via static counters.
 *
 * @param <T> the type of the test case input
 * @param <O> the type of the output (both expected and actual)
 */
record TestCaseResult<T, O>(T input, O expectedOutput, O actualOutput, boolean isCorrect) {

    // Static counters for global test statistics
    private static int TOTAL = 0;
    private static int FAILED = 0;

    /**
     * Constructs a new {@code TestCaseResult} with the given input, expected output,
     * actual output, and test status.
     *
     * @param input          the raw input used in the test case
     * @param expectedOutput the expected result for the given input
     * @param actualOutput   the actual output generated by the solution
     * @param isCorrect      {@code true} if the test passed; {@code false} otherwise
     */
    TestCaseResult(T input, O expectedOutput, O actualOutput, boolean isCorrect) {
        this.input = input;
        this.expectedOutput = expectedOutput;
        this.actualOutput = actualOutput;
        this.isCorrect = isCorrect;

        // Update statistics
        TOTAL++;
        if (!isCorrect) FAILED++;
    }

    /// @return {@code true} if the test passed; {@code false} otherwise
    @Override
    public boolean isCorrect() {
        return isCorrect;
    }

    /// @return the total number of test cases executed so far
    public static int getTotalCount() {
        return TOTAL;
    }

    /// @return the number of test cases that failed
    public static int getFailedCount() {
        return FAILED;
    }

    /**
     * Returns a formatted string representation of the test case result,
     * showing input, expected output, actual output, and pass/fail status.
     *
     * @return a readable formatted string
     */
    @Override
    public @NotNull String toString() {
        return String.format(
                "Input: %-30s | Expected: %-20s | Actual: %-20s | Status: %s",
                input,
                expectedOutput,
                actualOutput,
                isCorrect ? "✅" : "❌"
        );
    }
}