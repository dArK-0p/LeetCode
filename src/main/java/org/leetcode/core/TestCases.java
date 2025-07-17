package org.leetcode.core;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Utility class for loading and storing visible, hidden, and user-defined test cases
 * for a specific LeetCode problem. Automatically initialized by the {@link LC} superclass.
 *
 * <p>Test cases are accessible via the static maps:
 * <pre>
 * TestCases.visible   // predefined examples
 * TestCases.hidden    // internal validation cases
 * TestCases.user      // user-provided input/output
 * </pre>
 */
final class TestCases {

    /// Public static maps storing visible, hidden, and user-defined test cases.
    public static Map<String, String> visible;
    public static Map<String, String> hidden;
    public static Map<String, String> user;

    /**
     * Initializes the static test case maps using metadata associated with the given problem.
     * Should be invoked once per problem instance (typically in the constructor or static block).
     *
     * @param caller the problem instance whose test cases are to be loaded
     * @throws IllegalArgumentException if the caller is null or has an invalid ID format
     */
    public TestCases(Problem<?, ?, ?> caller) {
        if (caller == null) {
            throw new IllegalArgumentException("Problem caller cannot be null");
        }

        int problemId = parseProblemId(caller);
        visible = loadTestCases(problemId, true);
        hidden = loadTestCases(problemId, false);
    }

    /**
     * Parses the problem ID from the caller's ID string.
     *
     * @param caller the problem instance
     * @return the parsed problem ID
     * @throws IllegalArgumentException if the ID format is invalid
     */
    private static int parseProblemId(@NotNull Problem<?, ?, ?> caller) {
        try {
            return Integer.parseInt(caller.getId());
        } catch (NumberFormatException e) {
            String errorMessage = "❌ Invalid problem ID format: " + caller.getId();
            System.err.println(errorMessage);
            throw new IllegalArgumentException(errorMessage, e);
        }
    }

    /**
     * Loads either visible or hidden test cases from the JSON metadata file.
     *
     * @param problemId the numeric problem ID
     * @param testCase  whether to load visible test cases ({@code true}) or hidden ones ({@code false})
     * @return a map of string-formatted input-output pairs
     */
    private static Map<String, String> loadTestCases(int problemId, boolean testCase) {
        try {
            return ProblemMetadataLoader.getTestCases(problemId, testCase);
        } catch (Exception e) {
            System.err.println("❌ Failed to load " + (testCase ? "visible" : "hidden") + " test cases for problem ID " + problemId + ": " + e.getMessage());
            return Map.of(); // empty immutable map
        }
    }
}