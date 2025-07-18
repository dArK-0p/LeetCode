package org.leetcode.ui.cli;

import org.leetcode.core.LC;
import org.leetcode.core.ProblemMetadataLoader;
import org.leetcode.core.TestCases;

import java.util.Scanner;

/**
 * A basic command-line runner for LeetCode problems.
 * Interacts with the user through text menus to run test cases or print problem information.
 */
@SuppressWarnings("unused")
public class CliRunner {

    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Entry point for running the LeetCode CLI interface.
     * Displays all available problems and prompts the user to choose one.
     * Allows test execution and metadata printing.
     */
    public static void run() {
        System.out.printf(
                "%s%n%s%n",
                "Welcome to LeetCode CLI!",
                "========================"
        );

        while(true) {
            printAvailableProblems();

            int problemId = getInput();

            if (problemId == 0) {
                System.out.println("👋 Exiting CLI.");
                break;
            }

            LC<?, ?, ?> lc = LC.getInstance(String.valueOf(problemId));

            if (lc == null) {
                System.out.println("Error loading problem.");
                break;
            }
            launch(lc);
        }
    }

    /// Displays all available problems and their IDs from metadata.
    private static void printAvailableProblems() {
        System.out.printf(
                "%n%s%n%s%n",
                "Available Problems:",
                "-------------------"
        );

        ProblemMetadataLoader.showAvailableProblems();
    }

    /**
     * Prompts the user to enter a valid problem ID.
     * Only accepts known IDs or 0 to exit.
     *
     * @return the selected problem ID
     */
    private static int getInput() {
        int choice;

        while(true) {
            System.out.printf(
                    "%n%s%n%s",
                    "Type a problem ID to run a test case, or '0' to exit.",
                    "    > "
            );

            choice = SCANNER.nextInt(); SCANNER.nextLine();

            if (choice == 0 || ProblemMetadataLoader.getKeys().contains(choice))
                break;

            System.out.println("Invalid ID. Please try again.");
        }
        return choice;
    }

    /**
     * Launches the CLI for a specific problem instance.
     * Displays details and offers test/print menu options.
     *
     * @param lc the problem instance to work with
     */

    private static void launch(LC<?, ?, ?> lc) {
        System.out.printf(
                "%n📘 LC%s: %s%n%s",
                lc.getId(),
                lc.getTitle(),
                "----------------------------"
        );
        lc.printDetails();

        while (true) {
            showMainMenu();
            char choice = readChoice();
            if (choice == '0') {
                System.out.println("👋 Exiting to available problems.");
                break;
            }
            handleMainChoice(choice, lc);
        }
    }

    /// Prints the main interaction menu for running tests or opening the print menu.
    private static void showMainMenu() { System.out.printf(
            "%n%s%n%s%n%s%n%s%n%s%n%s",
            "====== MAIN MENU ======",
            "0. Exit",
            "1. Run Predefined Test Cases",
            "2. Run Custom Test Cases",
            "3. Open Print Menu",
            "   > "
    );}

    /**
     * Handles menu input for test execution and navigation.
     *
     * @param choice menu choice character
     * @param lc     problem instance
     */
    private static void handleMainChoice(char choice, LC<?, ?, ?> lc) {
        switch (choice) {
            case '1' -> lc.runTestCases();
            case '2' -> lc.runUserTestCases();
            case '3' -> printMenu(lc);
            default -> System.out.println("⚠️ Invalid option.");
        }
    }

    /**
     * Displays the print menu and handles user input for it.
     *
     * @param lc the problem instance
     */
    private static void printMenu(LC<?, ?, ?> lc) {
        while (true) {
            showPrintMenu();
            char choice = readChoice();
            if (choice == '0') break;
            handlePrintChoice(choice, lc);
        }
    }

    /// Displays print options for the problem such as metadata and test cases.
    private static void showPrintMenu() { System.out.printf(
            "%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s",
            "------ PRINT MENU ------",
            "0. Back to Main Menu",
            "1. Print Problem Details",
            "2. Print All Test Cases",
            "3. Print Visible Test Cases",
            "4. Print Hidden Test Cases",
            "5. Print Last Custom Test Cases",
            "   > "
    );}

    /**
     * Executes print menu options like displaying metadata or test cases.
     *
     * @param choice the user's print menu selection
     * @param lc     the current problem instance
     */
    private static void handlePrintChoice(char choice, LC<?, ?, ?> lc) {
        switch (choice) {
            case '1' -> lc.printDetails();
            case '2' -> {
                lc.printTestCases(TestCases.visible, "Visible");
                lc.printTestCases(TestCases.hidden, "Hidden");
            }
            case '3' -> lc.printTestCases(TestCases.visible, "Visible");
            case '4' -> lc.printTestCases(TestCases.hidden, "Hidden");
            case '5' -> {
                if(TestCases.user != null && !TestCases.user.isEmpty())
                    lc.printTestCases(TestCases.user, "Custom");
            }
            default -> System.out.println("⚠️ Invalid option.");
        }
    }

    /**
     * Reads a single character choice from the user.
     *
     * @return the first character of the trimmed input, or a space if empty
     */
    private static char readChoice() {
        String input = SCANNER.nextLine().trim();
        return input.isEmpty() ? ' ' : input.charAt(0);
    }
}
