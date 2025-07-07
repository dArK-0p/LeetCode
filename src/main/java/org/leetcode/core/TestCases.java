package org.leetcode.core;

import java.util.Map;

/**
 * Utility class responsible for holding and loading test cases (visible and hidden)
 * for a specific LeetCode problem. This class must be initialized per problem instance.
 *
 * <p>Usage: Create an instance in a static block to populate the static fields:
 * <pre>
 * static {
 *     new TestCases(new LC7()); // Load visible and hidden test cases
 * }
 * </pre>
 *
 * <p>Then access the test cases via the public static fields:
 * <pre>
 * Map&lt;String, String&gt; visibleTests = TestCases.visible;
 * Map&lt;String, String&gt; hiddenTests = TestCases.hidden;
 * </pre>
 */
public final class TestCases {

    private static final String ERROR_PREFIX = "❌ ";
    private static final String INVALID_ID_FORMAT_MESSAGE = "Invalid problem ID format: ";

    /**
     * Public static maps storing visible and hidden test cases.
     */
    public static Map<String, String> visible;
    public static Map<String, String> hidden;

    /**
     * Initializes the static test case maps using metadata associated with the given problem.
     * Should be invoked once per problem instance (typically in the constructor or static block).
     *
     * @param caller the problem instance whose test cases are to be loaded
     * @throws IllegalArgumentException if the caller is null or has an invalid ID format
     */
    public TestCases(Problem<?, ?> caller) {
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
    private static int parseProblemId(Problem<?, ?> caller) {
        try {
            return Integer.parseInt(caller.getId());
        } catch (NumberFormatException e) {
            String errorMessage = ERROR_PREFIX + INVALID_ID_FORMAT_MESSAGE + caller.getId();
            System.err.println(errorMessage);
            throw new IllegalArgumentException(errorMessage, e);
        }
    }

    /**
     * Loads either visible or hidden test cases from the JSON metadata file.
     *
     * @param problemId the numeric problem ID
     * @param visible   whether to load visible test cases ({@code true}) or hidden ones ({@code false})
     * @return a map of string-formatted input-output pairs
     */
    private static Map<String, String> loadTestCases(int problemId, boolean visible) {
        try {
            return ProblemMetadataLoader.getTestCases(problemId, visible);
        } catch (Exception e) {
            System.err.println(ERROR_PREFIX + "Failed to load " +
                    (visible ? "visible" : "hidden") +
                    " test cases for problem ID " + problemId + ": " + e.getMessage());
            return Map.of(); // empty immutable map
        }
    }
}