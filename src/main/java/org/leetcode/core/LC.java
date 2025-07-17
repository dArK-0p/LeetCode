package org.leetcode.core;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Abstract base class for all LeetCode problems.
 * <p>
 * This class provides a consistent problem-solving structure by combining metadata handling,
 * test case execution, user input support, and I/O conversion logic.
 * <p>
 * On instantiation, it automatically loads test cases from the resources directory using the problem ID.
 *
 * @param <I> the internal input type used by the {@code solve} method
 * @param <T> the raw input type used in test cases (e.g., {@code List<Integer>}, {@code String}, etc.)
 * @param <O> the output type returned by the {@code solve} method
 * @see Problem
 * @see TestCases
 */
@SuppressWarnings("InstantiationOfUtilityClass")
public abstract class LC<I, T, O> implements Problem<I, T, O> {

    /**
     * Constructs a new problem instance and loads its associated test cases
     * based on metadata resolved from the class name (e.g., {@code LC268}).
     */
    protected LC() {
        new TestCases(this);
    }

    /// Constants
    protected static final Gson GSON = new Gson();
    protected static final Scanner SCANNER = new Scanner(System.in);

    private static final String RUNNING_TEST_CASES_LABEL = "%n📘 Running %s-defined test cases for: LC%s";
    private final List<TestCaseResult<T, O>> evaluatedTestCases = new ArrayList<>();

    /**
     * Evaluates a single test case by solving the problem for the given input,
     * comparing it with the expected output, and recording the result.
     *
     * @param rawInput the raw input provided for the test case (before conversion)
     * @param expected the expected output for the test case
     */
    private void evaluate(@NotNull T rawInput, @NotNull O expected) {
        O actual = solve(convert(rawInput));
        boolean isCorrect = compare(expected, actual);
        evaluatedTestCases.add(new TestCaseResult<>(rawInput, expected, actual, isCorrect));
    }

    /**
     * Runs all provided test cases by evaluating each input-output pair and
     * collecting the results. Each result is stored in {@code evaluatedTestCases}.
     * Prints a summary after execution.
     *
     * @param testCases a map where the key is the raw test input and the value is the expected output
     */
    protected void run(@NotNull Map<T, O> testCases) {
        testCases.forEach(this::evaluate);
        printResult();
    }

    /**
     * Prints the evaluation summary for all test cases.
     * If all tests pass, a success message is shown.
     * Otherwise, failed and passed results are displayed individually.
     */
    private void printResult() {
        int total = TestCaseResult.getTotalCount();
        int failed = TestCaseResult.getFailedCount();

        if (total > 0 && failed == 0) {
            System.out.println("✅ All test cases passed!");
        } else {
            System.out.printf("❌ %d of %d test cases failed:%n%n", failed, total);
            evaluatedTestCases.forEach(System.out::println);
        }
    }


    /**
     * Executes all available test cases (visible and hidden) for the current problem instance.
     * <p>
     * If no test cases are found, a message is printed and execution is skipped.
     * Otherwise, the solution is run against each test case, and statistics are printed.
     */
    @Override
    public void runTestCases() {
        System.out.printf(RUNNING_TEST_CASES_LABEL, "pre", getId());

        run(getAllTestCases());
    }

    /// Processes user input with enhanced error handling and help information.
    @Override
    public void runUserTestCases() {
        System.out.printf(RUNNING_TEST_CASES_LABEL, "user", getId());

        getUserInput();
        run(fetchTestCases(TestCases.user));

        System.out.println("👋 Thank you for using the problem solver!");
    }

    public void printDetails() {
        printMetadata();
        printTestCases(VISIBLE_TEST_CASES);

        System.out.println("Print Hidden Test Cases? (y/N): ");
        if (SCANNER.next().charAt(0) == 'y')
            printTestCases(HIDDEN_TEST_CASES);
    }

    /**
     * Converts a raw input value of type {@code T} (typically parsed from a test case)
     * into the internal type {@code I} expected by the {@code solve} method.
     * <p>
     * This abstraction enables decoupling between the raw representation of input data
     * (e.g., JSON strings, parsed lists) and the type used within the solution logic.
     *
     * @param rawInput the test case input in its raw parsed form
     * @return the converted input ready to be passed to {@code solve}
     */
    protected abstract I convert(T rawInput);
}
