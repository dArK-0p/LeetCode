package org.leetcode.core;

/**
 * Primary interface to be implemented by all LeetCode problem classes.
 * <p>
 * This interface unifies solving logic, test case integration, input handling,
 * and metadata access by extending the following specialized interfaces:
 * <ul>
 *     <li>{@link Solver} – for implementing the solution logic</li>
 *     <li>{@link TestCaseSupport} – for managing visible and hidden test cases</li>
 *     <li>{@link UserInputSupport} – for interactive user input processing</li>
 *     <li>{@link MetadataSupport} – for accessing problem metadata and description</li>
 * </ul>
 *
 * @param <I> Input type
 * @param <O> Output type
 */
public interface Problem<I, O> extends
        Solver<I, O>,
        TestCaseSupport,
        UserInputSupport,
        MetadataSupport {

    /**
     * Prints a complete summary of the problem including metadata and test cases.
     */
    default void printDetails() {
        System.out.println("Problem ID: " + getId());
        System.out.println("Title: " + getTitle());
        System.out.println("Link: " + getLink());
        System.out.println("---------------------------------");
        System.out.println("Description:\n" + getDescription() + "\n");
        printTestCases(true);   // Visible test cases
        printTestCases(false);  // Hidden test cases
    }
}
