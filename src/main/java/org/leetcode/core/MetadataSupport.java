package org.leetcode.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * Interface for retrieving metadata associated with a LeetCode problem, such as ID, title, URL, and description.
 * <p>
 * This interface assumes that the implementing class name follows the format {@code LC<id>} (e.g., {@code LC7}, {@code LC268}).
 */
interface MetadataSupport {

    /**
     * Retrieves the LeetCode problem ID based on the implementing class name.
     *
     * @return the numeric problem ID as a string, or {@code "Unknown"} if extraction fails.
     */
    default String getId() {
        int id = extractIdFromClassName();

        return id == -1 ? "Unknown" : String.valueOf(id);
    }

    /**
     * Retrieves the official problem title from the metadata file.
     *
     * @return the problem title, or {@code "Unknown Problem Title"} if not found or invalid.
     */
    default String getTitle() {
        try {
            int id = Integer.parseInt(getId());
            return ProblemMetadataLoader.getMetadataField(
                    id,
                    Metadata::title,
                    "Unknown Problem Title"
            );
        } catch (NumberFormatException e) {
            return "Unknown Problem Title";
        }
    }

    /**
     * Constructs the canonical LeetCode problem URL using the problem title.
     *
     * @return the full LeetCode URL, or a fallback error message if the title is unavailable.
     */
    default String getLink() {
        String title = getTitle();

        if (title == null || title.equals("Unknown Problem Title")) {
            return "❌ Problem not found.";
        }
        return String.format("https://leetcode.com/problems/%s/description/", slugifyTitle(title));
    }

    /**
     * Loads and returns the problem description from a file defined in the metadata.
     *
     * @return a string containing the full problem description, or a fallback error message.
     */
    default String getDescription() {
        try {
            int id = Integer.parseInt(getId());
            String fileName = ProblemMetadataLoader.getMetadataField(
                    id,
                    Metadata::descriptionFile,
                    null
            );

            if (fileName == null) return "❌ Description file not specified for ID " + id;

            try ( InputStream is = getClass()
                    .getClassLoader()
                    .getResourceAsStream("descriptions/" + fileName) ) {

                if (is == null) return "❌ Description file not found: " + fileName;

                try ( BufferedReader reader = new BufferedReader(new InputStreamReader(is)) ) {
                    return reader.lines().collect(Collectors.joining("\n"));
                }
            }
        } catch (Exception e) {
            return "❌ Failed to load description: " + e.getMessage();
        }
    }

    /// Prints metadata info.
    default void printMetadata() {
        System.out.printf(
                "%n%s %s%n%s %s%n%s %s%n%s%n%s%n %s%n",
                "Problem ID: ", getId(),
                "Title: ", getTitle(),
                "Link: ", getLink(),
                "---------------------------------",
                "Description:", getDescription()
        );
    }

    /**
     * Converts a raw title into a URL-safe slug format.
     * For example, {@code "Count of Matches in Tournament"} becomes {@code "count-of-matches-in-tournament"}.
     *
     * @param title the title string to be converted
     * @return a slugified version of the title
     */
    private String slugifyTitle(String title) {
        return title
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-");
    }

    /**
     * Extracts the numeric ID from the implementing class name.
     * Class must follow the format {@code LC<digits>} (e.g., {@code LC7}, {@code LC1234}).
     *
     * @return the extracted integer ID, or {@code -1} if extraction fails
     */
    private int extractIdFromClassName() {
        String className = getClass().getSimpleName();

        if (className.matches("LC\\d+")) {
            try {
                return Integer.parseInt(className.substring(2));
            } catch (NumberFormatException e) {
                System.err.println("⚠️ Error parsing ID from class name: " + className);
            }
        }

        return -1;
    }
}