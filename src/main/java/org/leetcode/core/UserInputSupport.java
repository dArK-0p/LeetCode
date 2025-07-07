package org.leetcode.core;

import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Interface for handling interactive user input for a LeetCode problem.
 */
@SuppressWarnings("unused")
public interface UserInputSupport {

    /**
     * Constants for common prompts and messages
     */
    String DEFAULT_INPUT_PROMPT = "🔹 Enter your input:";
    String INPUT_ARROW = "↪️  Input: ";
    String OUTPUT_SUCCESS = "✅ Output: ";
    String OUTPUT_ERROR = "❌ Invalid input. Please try again.";
    String CONTINUE_PROMPT = "🔄 Would you like to try another input? (y/n): ";
    String GOODBYE_MESSAGE = "👋 Thank you for using the problem solver!";

    /**
     * Accepts input from the user via the console, parses it into the appropriate type,
     * invokes the solution logic, and displays the result.
     * <p>
     * Implementations are encouraged to handle invalid input and parsing errors gracefully.
     */
    void processUserInput();

    /**
     * Processes user input with custom prompts and validation.
     * This default implementation provides a framework for input handling.
     *
     * @param scanner   the Scanner to read input from
     * @param prompt    the prompt message to display to the user
     * @param parser    function to parse the input string into the desired type
     * @param validator predicate to validate the parsed input
     * @param solver    function to solve the problem with the parsed input
     * @param <T>       the input type for the solver
     * @param <R>       the output type from the solver
     * @return the result of the solver, or null if input was invalid
     */
    default <T, R> R processInputWithValidation(
            Scanner scanner,
            String prompt,
            Function<String, T> parser,
            Predicate<T> validator,
            Function<T, R> solver) {

        System.out.println(prompt);
        System.out.print(INPUT_ARROW);

        String userInput = scanner.nextLine().trim();

        try {
            T parsedInput = parser.apply(userInput);

            if (validator.test(parsedInput)) {
                R result = solver.apply(parsedInput);
                System.out.println(OUTPUT_SUCCESS + result);
                return result;
            } else {
                System.out.println(OUTPUT_ERROR + " Input validation failed.");
                return null;
            }
        } catch (Exception e) {
            System.out.println(OUTPUT_ERROR + " " + e.getMessage());
            return null;
        }
    }

    /**
     * Runs an interactive session that allows multiple inputs until the user chooses to quit.
     * This method calls processUserInput() repeatedly based on user choice.
     */
    default void runInteractiveSession() {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean continueSession = true;

            while (continueSession) {
                processUserInput();

                System.out.print(CONTINUE_PROMPT);
                String response = scanner.nextLine().trim().toLowerCase();

                continueSession = response.equals("y") || response.equals("yes");
            }

            System.out.println(GOODBYE_MESSAGE);
        }
    }

    /**
     * Utility method to safely parse an integer from user input.
     *
     * @param input the input string to parse
     * @return the parsed integer
     * @throws NumberFormatException if the input is not a valid integer
     */
    default int parseInteger(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new NumberFormatException("Input cannot be null or empty");
        }
        return Integer.parseInt(input.trim());
    }

    /**
     * Utility method to safely parse along from user input.
     *
     * @param input the input string to parse
     * @return the parsed long
     * @throws NumberFormatException if the input is not a valid long
     */
    default long parseLong(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new NumberFormatException("Input cannot be null or empty");
        }
        return Long.parseLong(input.trim());
    }

    /**
     * Utility method to safely parse a double from user input.
     *
     * @param input the input string to parse
     * @return the parsed double
     * @throws NumberFormatException if the input is not a valid double
     */
    default double parseDouble(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new NumberFormatException("Input cannot be null or empty");
        }
        return Double.parseDouble(input.trim());
    }

    /**
     * Utility method to parse an array of integers from comma-separated input.
     *
     * @param input the comma-separated string of integers
     * @return an array of parsed integers
     * @throws NumberFormatException if any element is not a valid integer
     */
    default int[] parseIntegerArray(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new int[0];
        }

        String[] parts = input.trim().split(" ");
        int[] result = new int[parts.length];

        for (int i = 0; i < parts.length; i++) {
            result[i] = Integer.parseInt(parts[i].trim());
        }

        return result;
    }

    /**
     * Displays helpful information about the expected input format.
     * Implementations can override this to provide problem-specific guidance.
     */
    default void displayInputHelp() {
        System.out.println("ℹ️  Input Help:");
        System.out.println("   • Enter your input when prompted");
        System.out.println("   • For arrays, use space-separated values (e.g., 1 2 3 4)");
        System.out.println("   • For strings, enter the text directly");
        System.out.println("   • For numbers, enter the numeric value");
    }
}