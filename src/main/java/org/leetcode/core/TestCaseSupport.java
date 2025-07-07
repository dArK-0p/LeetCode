package org.leetcode.core;

import java.util.Map;

/**
 * Interface representing support for test case management and execution.
 * Intended to be implemented by problem classes that load and run test cases.
 */
public interface TestCaseSupport {

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
        String label = visible ? "Visible Test Cases:" : "Hidden Test Cases:";

        System.out.println(label);
        for (Map.Entry<String, String> entry : testCases.entrySet()) {
            System.out.printf(" - %s → %s%n", entry.getKey(), entry.getValue());
        }
    }
}
