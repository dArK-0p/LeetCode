package org.leetcode.core;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Interface representing support for test case management and execution.
 * Intended to be implemented by problem classes that load and run test cases.
 */
interface TestCaseSupport<I, O> {

    /// Constants
    boolean VISIBLE_TEST_CASES = true;
    boolean HIDDEN_TEST_CASES = false;

    /// Executes all predefined test cases (both visible and hidden) for the current problem.
    void runTestCases();

    /**
     * Parses the raw input string from a test case into a strongly typed input object.
     * <p>
     * Implementations should convert the input from its JSON or string representation
     * into the expected input type {@code I} used by the problem's solver method.
     *
     * @param rawInput the raw input string as retrieved from the test case definition
     * @return the parsed input object of type {@code I}
     * @throws RuntimeException if parsing fails due to malformed input
     */
    I parseInput(String rawInput);

    /**
     * Parses the raw expected output string from a test case into a strongly typed output object.
     * <p>
     * Implementations should convert the output from its JSON or string representation
     * into the expected output type {@code O} used for comparison with the actual result.
     *
     * @param rawInput the raw expected output string as defined in the test case
     * @return the parsed output object of type {@code O}
     * @throws RuntimeException if parsing fails due to malformed output
     */
    O parseExpectedOutput(String rawInput);

    /**
     * Compares the expected and actual outputs to determine if the solution is correct.
     * <p>
     * Implementations should define equality logic appropriate for the output type {@code O},
     * including null-safety and deep comparison where needed (e.g., for arrays, collections, or custom objects).
     *
     * @param expected the expected output value defined in the test case
     * @param actual   the output value produced by the {@code solve} method
     * @return {@code true} if the outputs match; {@code false} otherwise
     */
    boolean compare(O expected, O actual);

    /**
     * Converts raw test case strings into typed test data suitable for solving.
     *
     * @param testCases a map of raw input-output pairs (both as strings)
     * @return a map of typed input-output pairs for testing
     */
    default Map<I, O> fetchTestCases(Map<String, String> testCases) {
        Map<I, O> result = new LinkedHashMap<>();
        testCases.forEach((key, value) -> result.put(parseInput(key), parseExpectedOutput(value)));
        return result;
    }

    /**
     * Aggregates and returns all available test cases for the current problem, including both
     * visible and hidden ones, after converting them into their typed representations.
     * <p>
     * This method is a convenience wrapper around {@link #fetchTestCases(Map)} and ensures
     * that both visible and hidden test cases are evaluated consistently in a single map.
     *
     * @return a {@link Map} of input-output pairs in typed form, or an empty map if no test cases are available
     */
    default Map<I, O> getAllTestCases() {
        if (!hasAnyTestCases()) {
            System.out.println("No test cases available.");
            return Map.of(); // Immutable empty map
        }

        Map<I, O> allTestCases = new LinkedHashMap<>();
        allTestCases.putAll(fetchTestCases(TestCases.visible));
        allTestCases.putAll(fetchTestCases(TestCases.hidden));
        return allTestCases;
    }

    /**
     * Prints all test cases from the specified group (visible/hidden).
     *
     * @param isVisible {@code true} for visible test cases; {@code false} for hidden ones
     */
    default void printTestCases(boolean isVisible) {
        Map<String, String> testCases = isVisible ? TestCases.visible : TestCases.hidden;
        String label = isVisible ? "Visible Test Cases:" : "Hidden Test Cases:";

        System.out.println(label);
        System.out.println("=================================");

        if (!hasTestCases(isVisible)) {
            System.out.println("No test cases available.");
            return;
        }

        testCases.forEach((key, value) -> System.out.printf(" - %s → %s%n", key, value));
        System.out.println(); // Add spacing between groups
    }

    /// Prints statistics about the available test cases.
    default void printTestCaseStatistics() {
        int visibleCount = getTestCaseCount(VISIBLE_TEST_CASES);
        int hiddenCount = getTestCaseCount(HIDDEN_TEST_CASES);
        int totalCount = visibleCount + hiddenCount;

        System.out.printf("📊 Statistics: %d visible, %d hidden, %d total test cases%n", visibleCount, hiddenCount, totalCount);
    }

    /**
     * Gets the count of test cases for the specified type.
     *
     * @param isVisible {@code true} for visible test cases; {@code false} for hidden ones
     * @return the number of test cases, or 0 if none available
     */
    default int getTestCaseCount(boolean isVisible) {
        Map<String, String> testCases = isVisible ? TestCases.visible : TestCases.hidden;
        return (testCases != null) ? testCases.size() : 0;
    }

    /**
     * Checks if test cases are available for the specified type.
     *
     * @param isVisible {@code true} for visible test cases; {@code false} for hidden ones
     * @return {@code true} if test cases are available and not empty; {@code false} otherwise
     */
    default boolean hasTestCases(boolean isVisible) {
        return getTestCaseCount(isVisible) > 0; // Leverages `getTestCaseCount` for consistency
    }

    /**
     * Checks if any test cases are available (either visible or hidden).
     *
     * @return {@code true} if any test cases are available; {@code false} otherwise
     */
    default boolean hasAnyTestCases() {
        return hasTestCases(VISIBLE_TEST_CASES) || hasTestCases(HIDDEN_TEST_CASES); // Reuses `hasTestCases`
    }


}