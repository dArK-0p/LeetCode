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

    String PROBLEM_ID_LABEL = "Problem ID: ";
    String TITLE_LABEL = "Title: ";
    String LINK_LABEL = "Link: ";
    String SEPARATOR_LINE = "---------------------------------";
    String DESCRIPTION_LABEL = "Description:\n";
    String DESCRIPTION_SUFFIX = "\n";

    /**
     * Prints a complete summary of the problem, including metadata and test cases.
     */
    default void printDetails() {
        System.out.println(PROBLEM_ID_LABEL + getId());
        System.out.println(TITLE_LABEL + getTitle());
        System.out.println(LINK_LABEL + getLink());
        System.out.println(SEPARATOR_LINE);
        System.out.println(DESCRIPTION_LABEL + getDescription() + DESCRIPTION_SUFFIX);
        printAllTestCases();
    }
}