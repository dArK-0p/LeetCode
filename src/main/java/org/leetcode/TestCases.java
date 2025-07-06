package org.leetcode;

import java.util.Map;

/**
 * Utility class responsible for holding and loading test cases (visible and hidden)
 * for a specific LeetCode problem. This class must be initialized per problem instance.
 */
final class TestCases {

    /** Public static maps storing visible and hidden test cases. */
    public static Map<String, String> visible;
    public static Map<String, String> hidden;

    /**
     * Initializes the static test case maps using metadata associated with the given problem.
     * Should be invoked once per problem instance (typically in the constructor or static block).
     *
     * @param caller the problem instance whose test cases are to be loaded
     */
    public TestCases(Problem<?, ?> caller) {
        visible = load(caller, true);
        hidden = load(caller, false);
    }

    /**
     * Loads either visible or hidden test cases from the JSON metadata file.
     *
     * @param caller  the problem instance
     * @param visible whether to load visible test cases ({@code true}) or hidden ones ({@code false})
     * @return a map of string-formatted input-output pairs
     */
    private static Map<String, String> load(Problem<?, ?> caller, boolean visible) {
        try {
            int id = Integer.parseInt(caller.getId());
            return ProblemMetadataLoader.getTestCases(id, visible);
        } catch (NumberFormatException e) {
            System.err.println("❌ Invalid problem ID format: " + caller.getId());
            return Map.of(); // empty immutable map
        }
    }
}
