package org.leetcode.core;

/**
 * Interface for handling interactive user input for a LeetCode problem.
 */
public interface UserInputSupport {

    /**
     * Accepts input from the user via the console, parses it into the appropriate type,
     * invokes the solution logic, and displays the result.
     * <p>
     * Implementations are encouraged to handle invalid input and parsing errors gracefully.
     */
    void processUserInput();
}
