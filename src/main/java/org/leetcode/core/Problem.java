package org.leetcode.core;

/**
 * Primary interface to be implemented by all LeetCode problem classes.
 *
 * <p>This interface unifies solution logic, test case management, input conversion,
 * and problem metadata access by extending:</p>
 * <ul>
 *     <li>{@link TestCaseSupport} – for handling visible/hidden test cases</li>
 *     <li>{@link UserInputSupport} – for reading custom input</li>
 *     <li>{@link MetadataSupport} – for metadata like ID, title, description</li>
 * </ul>
 *
 * @param <I> Converted input type used by the solver
 * @param <T> Raw test case input type (e.g., JSON or string representation)
 * @param <O> Output type
 */
interface Problem<I, T, O> extends
        TestCaseSupport<T, O>,
        UserInputSupport,
        MetadataSupport {

    /**
     * Computes and returns the solution for the given input.
     *
     * @param input the input data
     * @return the computed output result
     */
    O solve(I input);
}