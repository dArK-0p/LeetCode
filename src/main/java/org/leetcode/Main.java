package org.leetcode;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.leetcode.core.Problem;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Enhanced resource-driven entry point for LeetCode problem-solving.
 * Dynamically loads problems, descriptions, and test cases from resources.
 */
public class Main {

    private static final String SEPARATOR = "=".repeat(60);
    private static final String MINI_SEPARATOR = "─".repeat(40);
    private static final String WELCOME_MESSAGE = "🚀 Welcome to LeetCode Problem Solver!";
    private static final String GOODBYE_MESSAGE = "👋 Thank you for using LeetCode Problem Solver!";
    private static final Gson gson = new Gson();

    // Resource paths
    private static final String AVAILABLE_PROBLEMS_PATH = "/availableProblems.json";
    private static final String DESCRIPTIONS_PATH = "/descriptions/";
    private static final String TESTCASES_PATH = "/testcases/";

    private static final Map<String, ProblemConfig> availableProblems = new LinkedHashMap<>();

    static {
        loadAvailableProblems();
    }

    public static void main(String[] args) {
        displayWelcome();

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                Problem<?, ?> selectedProblem = selectProblem(scanner);

                if (selectedProblem == null) {
                    break; // User chose to quit
                }

                interactWithProblem(selectedProblem, scanner);

                if (!askToContinue(scanner)) {
                    break;
                }
            }
        }

        System.out.println("\n" + SEPARATOR);
        System.out.println(GOODBYE_MESSAGE);
        System.out.println(SEPARATOR);
    }

    /**
     * Displays welcome message and system information.
     */
    private static void displayWelcome() {
        System.out.println(SEPARATOR);
        System.out.println(WELCOME_MESSAGE);
        System.out.println(SEPARATOR);
        System.out.printf("📋 Available Problems: %d%n", availableProblems.size());
        System.out.printf("🔗 Resource-driven configuration loaded%n");
        System.out.printf("🧪 Test cases and descriptions loaded from resources%n");
        System.out.println(SEPARATOR);
    }

    /**
     * Loads available problems configuration from resources.
     */
    private static void loadAvailableProblems() {
        try (InputStream is = Main.class.getResourceAsStream(AVAILABLE_PROBLEMS_PATH)) {
            if (is == null) {
                System.err.println("❌ Failed to load availableProblems.json from resources");
                return;
            }

            String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            Map<String, ProblemConfig> problems = gson.fromJson(json,
                    new TypeToken<Map<String, ProblemConfig>>() {
                    }.getType());

            availableProblems.putAll(problems);
            System.out.printf("✅ Loaded %d problem configurations%n", problems.size());

        } catch (IOException e) {
            System.err.println("❌ Error loading problem configurations: " + e.getMessage());
        }
    }

    /**
     * Allows a user to select a problem from available options.
     */
    private static Problem<?, ?> selectProblem(Scanner scanner) {
        System.out.println("\n📋 Available Problems:");
        System.out.println(MINI_SEPARATOR);

        // Display available problems with enhanced formatting
        for (Map.Entry<String, ProblemConfig> entry : availableProblems.entrySet()) {
            String id = entry.getKey();
            ProblemConfig config = entry.getValue();

            // Load description preview
            String preview = loadDescriptionPreview(config.descriptionFile);

            System.out.printf("  🎯 LC%-4s - %s%n", id, config.title);
            System.out.printf("       %s%n", preview);
            System.out.println();
        }

        System.out.println(MINI_SEPARATOR);
        System.out.print("🔍 Enter problem ID (e.g., 268) or 'q' to quit: ");
        String input = scanner.nextLine().trim();

        if ("q".equalsIgnoreCase(input) || "quit".equalsIgnoreCase(input)) {
            return null;
        }

        if (!availableProblems.containsKey(input)) {
            System.out.println("❌ Problem LC" + input + " not found.");
            System.out.println("   Available IDs: " + String.join(", ", availableProblems.keySet()));
            return selectProblem(scanner); // Recursive retry
        }

        return createProblemInstance(input);
    }

    /**
     * Creates a problem instance using reflection.
     */
    private static Problem<?, ?> createProblemInstance(String problemId) {
        try {
            String className = "org.leetcode.solutions.LC" + problemId;
            Class<?> clazz = Class.forName(className);
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            return (Problem<?, ?>) constructor.newInstance();
        } catch (Exception e) {
            System.err.println("❌ Failed to create instance for LC" + problemId + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Loads a preview of the problem description.
     */
    private static String loadDescriptionPreview(String descriptionFile) {
        try (InputStream is = Main.class.getResourceAsStream(DESCRIPTIONS_PATH + descriptionFile)) {
            if (is == null) return "Description not available";

            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            // Return the first line or first 80 characters, whichever is shorter
            String firstLine = content.split("\n")[0];
            return firstLine.length() > 80 ? firstLine.substring(0, 77) + "..." : firstLine;

        } catch (IOException e) {
            return "Error loading description";
        }
    }

    /**
     * Main interaction loop with the selected problem.
     */
    private static void interactWithProblem(Problem<?, ?> problem, Scanner scanner) {
        String problemId = problem.getId();
        ProblemConfig config = availableProblems.get(problemId);

        System.out.println("\n" + SEPARATOR);
        System.out.printf("📊 Problem LC%s: %s%n", problemId, config.title);
        System.out.println(SEPARATOR);

        // Show enhanced problem details with resource data
        showEnhancedProblemDetails(problem, config);

        boolean continueInteraction = true;
        while (continueInteraction) {
            continueInteraction = showMainMenu(problem, scanner);
        }
    }

    /**
     * Shows enhanced problem details using resource files.
     */
    private static void showEnhancedProblemDetails(Problem<?, ?> problem, ProblemConfig config) {
        // Show full description from resource
        showFullDescription(config.descriptionFile);

        // Show metadata
        problem.printDetails();

        // Show resource statistics
        showResourceStatistics(config);
    }

    /**
     * Shows the full problem description from a resource file.
     */
    private static void showFullDescription(String descriptionFile) {
        try (InputStream is = Main.class.getResourceAsStream(DESCRIPTIONS_PATH + descriptionFile)) {
            if (is == null) {
                System.out.println("📝 Description: Not available");
                return;
            }

            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("📝 Problem Description:");
            System.out.println("   " + content.replace("\n", "\n   "));
            System.out.println();

        } catch (IOException e) {
            System.out.println("📝 Description: Error loading from " + descriptionFile);
        }
    }

    /**
     * Shows statistics about available resources.
     */
    private static void showResourceStatistics(ProblemConfig config) {
        System.out.println("📊 Resource Information:");
        System.out.printf("   📄 Description: %s%n", config.descriptionFile);
        System.out.printf("   🧪 Test Cases: %s%n", config.testcaseFile);

        // Try to load and show test case counts
        try (InputStream is = Main.class.getResourceAsStream(TESTCASES_PATH + config.testcaseFile)) {
            if (is != null) {
                String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                TestCaseData tcData = gson.fromJson(json, TestCaseData.class);

                int visibleCount = tcData.visible != null ? tcData.visible.size() : 0;
                int hiddenCount = tcData.hidden != null ? tcData.hidden.size() : 0;

                System.out.printf("   📈 Visible Test Cases: %d%n", visibleCount);
                System.out.printf("   🔒 Hidden Test Cases: %d%n", hiddenCount);
                System.out.printf("   📊 Total Test Cases: %d%n", visibleCount + hiddenCount);
            }
        } catch (IOException e) {
            System.out.println("   ⚠️  Could not load test case statistics");
        }
    }

    /**
     * Shows the main interaction menu and handles user choice.
     */
    private static boolean showMainMenu(Problem<?, ?> problem, Scanner scanner) {
        System.out.println("\n" + MINI_SEPARATOR);
        System.out.println("🎯 Choose an action:");
        System.out.println("  1️⃣  Run all test cases");
        System.out.println("  2️⃣  Enter custom input");
        System.out.println("  3️⃣  Run interactive session");
        System.out.println("  4️⃣  View detailed statistics");
        System.out.println("  5️⃣  View problem details again");
        System.out.println("  6️⃣  Return to problem selection");
        System.out.println("  7️⃣  Exit application");
        System.out.println(MINI_SEPARATOR);
        System.out.print("📝 Enter your choice (1-7): ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1" -> {
                System.out.println("\n🧪 Running all test cases...");
                System.out.println(MINI_SEPARATOR);
                problem.runTestCases();
            }
            case "2" -> {
                System.out.println("\n✏️  Custom Input Mode:");
                System.out.println(MINI_SEPARATOR);
                problem.processUserInput();
            }
            case "3" -> {
                System.out.println("\n🔄 Interactive Session Mode:");
                System.out.println(MINI_SEPARATOR);
                problem.runInteractiveSession();
            }
            case "4" -> {
                System.out.println("\n📈 Detailed Statistics:");
                System.out.println(MINI_SEPARATOR);
                showDetailedStatistics(problem);
            }
            case "5" -> {
                System.out.println("\n📋 Problem Details:");
                System.out.println(MINI_SEPARATOR);
                String problemId = problem.getId();
                ProblemConfig config = availableProblems.get(problemId);
                showEnhancedProblemDetails(problem, config);
            }
            case "6" -> {
                return false; // Return to problem selection
            }
            case "7" -> System.exit(0); // Exit application
            default -> System.out.println("❌ Invalid choice '" + choice + "'. Please enter 1-7.");
        }

        return true; // Continue interaction
    }

    /**
     * Shows detailed statistics about test cases and problem metrics.
     */
    private static void showDetailedStatistics(Problem<?, ?> problem) {
        int visibleCount = problem.getTestCaseCount(true);
        int hiddenCount = problem.getTestCaseCount(false);
        int totalCount = problem.getTotalTestCaseCount();

        System.out.printf("📊 Test Case Statistics:%n");
        System.out.printf("   📈 Visible test cases: %d%n", visibleCount);
        System.out.printf("   🔒 Hidden test cases:  %d%n", hiddenCount);
        System.out.printf("   📊 Total test cases:   %d%n", totalCount);

        if (totalCount > 0) {
            double visiblePercentage = (visibleCount * 100.0) / totalCount;
            System.out.printf("   📋 Visible percentage: %.1f%%%n", visiblePercentage);
        }

        System.out.printf("   ✅ Has visible tests:  %s%n", problem.hasTestCases(true) ? "Yes" : "No");
        System.out.printf("   🔍 Has hidden tests:   %s%n", problem.hasTestCases(false) ? "Yes" : "No");

        // Coverage analysis
        System.out.println("\n🎯 Test Coverage Analysis:");
        if (totalCount == 0) {
            System.out.println("   ❌ No test cases available");
        } else if (visibleCount > 0 && hiddenCount > 0) {
            System.out.println("   ✅ Excellent - Comprehensive test coverage");
        } else if (visibleCount > 0) {
            System.out.println("   ⚠️  Good - Visible tests only");
        } else {
            System.out.println("   ⚠️  Limited - Hidden tests only");
        }

        // Resource information
        String problemId = problem.getId();
        ProblemConfig config = availableProblems.get(problemId);
        if (config != null) {
            System.out.println("\n📁 Resource Files:");
            System.out.printf("   📄 Description: %s%n", config.descriptionFile);
            System.out.printf("   🧪 Test cases: %s%n", config.testcaseFile);
        }
    }

    /**
     * Asks user if they want to continue with another problem.
     */
    private static boolean askToContinue(Scanner scanner) {
        System.out.println("\n" + MINI_SEPARATOR);
        System.out.print("🔄 Would you like to try another problem? (y/n): ");
        String response = scanner.nextLine().trim().toLowerCase();
        return response.equals("y") || response.equals("yes");
    }

    /**
     * Configuration class for problem metadata.
     */
    private static class ProblemConfig {
        String title;
        String descriptionFile;
        String testcaseFile;
    }

    /**
     * Helper class for parsing test case JSON structure.
     */
    private static class TestCaseData {
        java.util.List<Object> visible;
        java.util.List<Object> hidden;
    }
}