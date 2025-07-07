package org.leetcode.solutions;

import org.leetcode.core.Problem;
import org.leetcode.core.TestCases;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

@SuppressWarnings({"InstantiationOfUtilityClass", "unused"})
public class LC1688 implements Problem<Integer, Integer> {

    static {
        new TestCases(new LC1688());
    }

    /**
     * Calculates the number of matches in a tournament.
     * In a tournament with n teams, there are exactly n-1 matches needed
     * to determine the winner (each match eliminates exactly one team).
     *
     * @param n the number of teams in the tournament
     * @return the number of matches needed
     */
    @Override
    public Integer solve(Integer n) {
        return n - 1;
    }

    /**
     * Processes user input with enhanced error handling and validation.
     */
    @Override
    public void processUserInput() {
        try (Scanner scanner = new Scanner(System.in)) {
            displayInputHelp();

            System.out.println("🔹 Enter the number of teams in the tournament:");
            System.out.print(INPUT_ARROW);

            String input = scanner.nextLine().trim();

            try {
                int n = parseInteger(input);

                if (n < 1) {
                    System.out.println(OUTPUT_ERROR + " Number of teams must be at least 1.");
                    return;
                }

                if (n > 200) {
                    System.out.println(OUTPUT_ERROR + " Number of teams should not exceed 200 for practical tournaments.");
                    return;
                }

                int result = solve(n);
                System.out.println(OUTPUT_SUCCESS + result);

            } catch (NumberFormatException e) {
                System.out.println(OUTPUT_ERROR + " " + e.getMessage());
            }
        }
    }

    /**
     * Executes all predefined test cases with enhanced statistics.
     */
    @Override
    public void runTestCases() {
        System.out.println("📘 Running test cases for: LC" + getId());

        if (hasAnyTestCases()) {
            System.out.println(EMPTY_TEST_CASES_MESSAGE);
            return;
        }

        Map<Integer, Integer> allTestCases = new LinkedHashMap<>();
        allTestCases.putAll(fetchTestCases(TestCases.visible));
        allTestCases.putAll(fetchTestCases(TestCases.hidden));

        for (Map.Entry<Integer, Integer> entry : allTestCases.entrySet()) {
            int input = entry.getKey();
            int expected = entry.getValue();
            int actual = solve(input);
            System.out.printf("Input: %-5d | Expected: %-5d | Got: %-5d | %s%n",
                    input, expected, actual, (expected == actual) ? "✅" : "❌");
        }

        printTestCaseStatistics();
    }

    /**
     * Converts raw test case strings into typed integer pairs.
     */
    @Override
    public Map<Integer, Integer> fetchTestCases(Map<String, String> testCases) {
        Map<Integer, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : testCases.entrySet()) {
            try {
                Integer input = parseInteger(entry.getKey());
                Integer output = parseInteger(entry.getValue());
                result.put(input, output);
            } catch (NumberFormatException e) {
                System.err.printf("⚠️  Skipping invalid test case: '%s' → '%s'%n",
                        entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    /**
     * Displays problem-specific input help.
     */
    @Override
    public void displayInputHelp() {
        System.out.println("ℹ️  Count of Matches in Tournament Help:");
        System.out.println("   • Enter a positive integer (number of teams)");
        System.out.println("   • Valid range: 1 to 200 teams");
        System.out.println("   • Formula: n teams require exactly (n-1) matches");
        System.out.println("   • Logic: Each match eliminates exactly one team");
        System.out.println("   • Example: 7 teams → 6 matches");
        System.out.println("   • Example: 14 teams → 13 matches");
    }
}