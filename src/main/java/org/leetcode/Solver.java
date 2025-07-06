package org.leetcode;

/**
 * Functional interface representing a generic solver for a computational problem.
 *
 * @param <I> the input type
 * @param <O> the output type
 */
@FunctionalInterface
public interface Solver<I, O> {

    /**
     * Computes and returns the solution for the given input.
     *
     * @param input the input data
     * @return the computed output result
     */
    O solve(I input);
}
