package org.leetcode.core;

import java.util.Map;

/**
 * Interface representing support for test case management and execution.
 * Intended to be implemented by problem classes that load and run test cases.
 */
public interface TestCaseSupport {

    /**
     * Constants for formatting and labels
     */
    String VISIBLE_LABEL = "Visible Test Cases:";
    String HIDDEN_LABEL = "Hidden Test Cases:";
    String ALL_LABEL = "All Test Cases:";
    String TEST_CASE_FORMAT = " - %s → %s%n";
    String EMPTY_TEST_CASES_MESSAGE = "No test cases available.";
    String STATISTICS_FORMAT = "📊 Statistics: %d visible, %d hidden, %d total test cases%n";

    /**
     * Executes all predefined test cases (both visible and hidden) for the current problem.
     */
    void runTestCases();

    /**
     * Converts raw test case strings into typed test data suitable for solving.
     * The actual key-value types are defined by the implementing class.
     *
     * @param testCases a map of raw input-output pairs (both as strings)
     * @return a map of typed input-output pairs for testing
     */
    Map<?, ?> fetchTestCases(Map<String, String> testCases);

    /**
     * Prints all test cases from either the visible or hidden group.
     *
     * @param visible {@code true} to print visible test cases; {@code false} for hidden ones
     */
    default void printTestCases(boolean visible) {
        Map<String, String> testCases = visible ? TestCases.visible : TestCases.hidden;
        String label = visible ? VISIBLE_LABEL : HIDDEN_LABEL;

        System.out.println(label);

        if (testCases == null || testCases.isEmpty()) {
            System.out.println(EMPTY_TEST_CASES_MESSAGE);
            return;
        }

        for (Map.Entry<String, String> entry : testCases.entrySet()) {
            System.out.printf(TEST_CASE_FORMAT, entry.getKey(), entry.getValue());
        }
    }

    /**
     * Prints all test cases (both visible and hidden) with clear separation.
     */
    default void printAllTestCases() {
        System.out.println(ALL_LABEL);
        System.out.println("=" + "=".repeat(ALL_LABEL.length() - 1));

        printTestCases(true);
        System.out.println(); // Add spacing
        printTestCases(false);

        printTestCaseStatistics();
    }

    /**
     * Prints statistics about the available test cases.
     */
    default void printTestCaseStatistics() {
        int visibleCount = getTestCaseCount(true);
        int hiddenCount = getTestCaseCount(false);
        int totalCount = visibleCount + hiddenCount;

        System.out.printf(STATISTICS_FORMAT, visibleCount, hiddenCount, totalCount);
    }

    /**
     * Gets the count of test cases for the specified type.
     *
     * @param visible {@code true} to count visible test cases; {@code false} for hidden ones
     * @return the number of test cases, or 0 if none available
     */
    default int getTestCaseCount(boolean visible) {
        Map<String, String> testCases = visible ? TestCases.visible : TestCases.hidden;
        return (testCases != null) ? testCases.size() : 0;
    }

    /**
     * Gets the total count of all test cases (visible and hidden).
     *
     * @return the total number of test cases
     */
    default int getTotalTestCaseCount() {
        return getTestCaseCount(true) + getTestCaseCount(false);
    }

    /**
     * Checks if test cases are available for the specified type.
     *
     * @param visible {@code true} to check visible test cases; {@code false} for hidden ones
     * @return {@code true} if test cases are available and not empty; {@code false} otherwise
     */
    default boolean hasTestCases(boolean visible) {
        return getTestCaseCount(visible) > 0;
    }

    /**
     * Checks if any test cases are available (either visible or hidden).
     *
     * @return {@code true} if any test cases are available; {@code false} otherwise
     */
    default boolean hasAnyTestCases() {
        return !hasTestCases(true) && !hasTestCases(false);
    }
}