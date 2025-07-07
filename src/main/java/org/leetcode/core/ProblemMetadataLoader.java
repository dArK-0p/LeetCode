package org.leetcode.core;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Loads and provides access to metadata for LeetCode problems.
 * Metadata is loaded from: resources/availableProblems.json
 */
public final class ProblemMetadataLoader {

    /**
     * Name of the metadata file in the resources directory.
     */
    private static final String METADATA_FILE = "availableProblems.json";

    /**
     * Cache to store problem metadata after it's loaded from JSON.
     * Key: Problem ID (e.g., 268), Value: Metadata object (title, descriptionFile, testcaseFile)
     */
    private static final Map<Integer, Metadata> cache = loadMetadata();

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private ProblemMetadataLoader() {
        // Utility class – no object creation allowed
    }

    /**
     * Retrieves the problem title for a given problem ID.
     *
     * @param id the numeric LeetCode problem ID
     * @return the title string if found; otherwise a placeholder
     */
    static String getTitle(int id) {
        Metadata metadata = cache.get(id);
        if (metadata != null) {
            return metadata.title;
        }
        return "Unknown Problem Title";
    }

    /**
     * Retrieves the description file name for the given problem ID.
     *
     * @param id the numeric LeetCode problem ID
     * @return the filename string (e.g., "DES268.txt") or null if not found
     */
    static String getDescriptionPath(int id) {
        Metadata metadata = cache.get(id);
        if (metadata != null) {
            return metadata.descriptionFile;
        }
        return null;
    }

    /**
     * Retrieves the test case file name for the given problem ID.
     *
     * @param id the numeric LeetCode problem ID
     * @return the filename string (e.g., "TC268.json") or null if not found
     */
    static String getTestcasePath(int id) {
        Metadata metadata = cache.get(id);
        if (metadata != null) {
            return metadata.testcaseFile;
        }
        return null;
    }

    /**
     * Loads test cases for a given problem ID from the associated JSON file.
     *
     * @param id      the problem ID whose test cases to load
     * @param visible if {@code true}, loads from the "visible" section;
     *                if {@code false}, loads from the "hidden" section
     * @return a map where each entry represents one test case: input → expected output
     */
    public static Map<String, String> getTestCases(int id, boolean visible) {
        String filename = getTestcasePath(id);
        if (filename == null) return Map.of();

        String path = "testcases/" + filename;
        try (InputStream is = ProblemMetadataLoader.class.getClassLoader().getResourceAsStream(path)) {
            if (is == null) throw new IllegalStateException("❌ Testcase file not found: " + path);

            JsonObject root = JsonParser.parseReader(new InputStreamReader(is)).getAsJsonObject();
            JsonArray array = root.getAsJsonArray(visible ? "visible" : "hidden");

            Map<String, String> result = new LinkedHashMap<>();
            for (int i = 0; i < array.size(); i++) {
                JsonObject obj = array.get(i).getAsJsonObject();
                result.put(obj.get("input").toString(), obj.get("output").toString());
            }

            return result;
        } catch (Exception e) {
            System.err.println("❌ Failed to load test cases: " + e.getMessage());
            return Map.of();
        }
    }


    /**
     * Checks whether metadata exists for a given problem ID.
     *
     * @param id the problem ID
     * @return true if metadata is available; false otherwise
     */
    static boolean isAvailable(int id) {
        return cache.containsKey(id);
    }

    /**
     * Loads and parses the availableProblems.json file into a map.
     *
     * @return a populated ConcurrentHashMap or an empty map if loading fails
     */
    private static Map<Integer, Metadata> loadMetadata() {
        try (InputStream is = ProblemMetadataLoader.class.getClassLoader().getResourceAsStream(METADATA_FILE)) {
            if (is == null) {
                throw new IllegalStateException("❌ Metadata file not found: " + METADATA_FILE);
            }

            return new Gson().fromJson(
                    new InputStreamReader(is),
                    new TypeToken<ConcurrentHashMap<Integer, Metadata>>() {}.getType()
            );
        } catch (Exception e) {
            System.err.println("❌ Failed to load metadata: " + e.getMessage());
            return Map.of(); // Return empty immutable map
        }
    }

    /**
     * Internal class representing the structure of a single problem’s metadata.
     * Matches JSON structure: { "title": ..., "descriptionFile": ..., "testcaseFile": ... }
     */
    private static class Metadata {
        String title;
        String descriptionFile;
        String testcaseFile;
    }
}
