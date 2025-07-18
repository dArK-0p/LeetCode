package org.leetcode.core;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Loads and provides access to metadata for LeetCode problems.
 * Metadata is loaded from: resources/availableProblems.json
 */
public final class ProblemMetadataLoader {

    /**
     * Cache to store problem metadata after it's loaded from JSON.
     * Key: Problem ID (e.g., 268), Value: Metadata object (title, descriptionFile, testcaseFile)
     */
    private static final Map<Integer, Metadata> cache = loadMetadata();

    /// Private constructor to prevent instantiation of this utility class.
    private ProblemMetadataLoader() {

    }

    /**
     * Loads and parses the availableProblems.json file into a map.
     *
     * @return a populated ConcurrentHashMap or an empty map if loading fails
     */
    static Map<Integer, Metadata> loadMetadata() {
        try (InputStream is = ProblemMetadataLoader.class
                .getClassLoader().
                getResourceAsStream("availableProblems.json")) {

            if (is == null) throw new IllegalStateException(
                        "❌ Metadata file not found: " + "availableProblems.json");

            return new Gson().fromJson(
                    new InputStreamReader(is),
                    new TypeToken<ConcurrentHashMap<Integer, Metadata>>()
                    {}.getType()
            );
        } catch (Exception e) {
            System.err.println("❌ Failed to load metadata: " + e.getMessage());

            return Map.of(); // Return an empty immutable map
        }
    }

    /**
     * Loads test cases for a given problem ID from the associated JSON file.
     *
     * @param id       the problem ID whose test cases to load
     * @param testCase if {@code true}, loads from the "visible" section;
     *                 if {@code false}, loads from the "hidden" section
     * @return a map where each entry represents one test case: input → expected output
     */
    static @NotNull Map<String, String> getTestCases(int id, boolean testCase) {
        String filename = getMetadataField(id, Metadata::testcaseFile, null);

        if (filename == null) return Map.of();

        try (InputStream is = ProblemMetadataLoader.class
                .getClassLoader()
                .getResourceAsStream("testcases/" + filename)) {

            if (is == null) throw new IllegalStateException("❌ Testcase file not found: " + "testcases/" + filename);

            JsonObject root = JsonParser.parseReader(new InputStreamReader(is)).getAsJsonObject();
            JsonArray array = root.getAsJsonArray(testCase ? "visible" : "hidden");

            Map<String, String> result = new LinkedHashMap<>();

            for (int i = 0; i < array.size(); i++) {
                JsonObject obj = array.get(i).getAsJsonObject();
                result.put(
                        obj.get("input").toString(),
                        obj.get("output").toString()
                );
            }

            return result;
        } catch (Exception e) {
            System.err.println("❌ Failed to load test cases: " + e.getMessage());
            return Map.of();
        }
    }

    /**
     * Common method to retrieve a specific field from metadata.
     *
     * @param id             the problem ID
     * @param fieldExtractor function to extract the desired field from metadata
     * @param defaultValue   value to return if metadata is not found
     * @param <T>            the type of the field
     * @return the field value or default value if not found
     */
    static <T> T getMetadataField(int id, Function<Metadata, T> fieldExtractor, T defaultValue) {
        Metadata metadata = cache.get(id);

        if (metadata != null) return fieldExtractor.apply(metadata);

        return defaultValue;
    }

    ///  Prints all available problems.
    public static void showAvailableProblems() {
        cache.keySet().stream().sorted().forEach(id -> {
            String title = getMetadataField(id, Metadata::title, null);
            System.out.printf("%d. %s%n", id, title);
        });
    }

    /**
     * Returns the set of problem IDs currently available in the metadata cache.
     * <p>
     * Each ID corresponds to a LeetCode problem defined in the {@code availableProblems.json} file.
     *
     * @return a {@code Set<Integer>} containing all available problem IDs
     */
    public static Set<Integer> getKeys() {
        return cache.keySet();
    }
}