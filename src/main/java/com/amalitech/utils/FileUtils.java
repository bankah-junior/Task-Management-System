package com.amalitech.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for handling file operations using NIO.2.
 */
public final class FileUtils {

    private FileUtils() {}

    /**
     * Reads all lines from a file.
     *
     * @param filePath the path to the file
     * @return list of lines from the file
     * @throws IOException if reading fails
     */
    public static List<String> readAllLines(String filePath) throws IOException {
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
        }

        return Files.readAllLines(path);
    }

    /**
     * Writes lines to a file, overwriting existing content.
     *
     * @param filePath the path to the file
     * @param lines the lines to write
     * @throws IOException if writing fails
     */
    public static void writeAllLines(String filePath, List<String> lines) throws IOException {
        Path path = Paths.get(filePath);

        Files.createDirectories(path.getParent());

        try (var writer = Files.newBufferedWriter(path)) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    /**
     * Parses a JSON object block into a map.
     *
     * @param jsonBlock lines representing a single JSON object
     * @return map of field names to values
     */
    public static Map<String, String> parseJsonObject(String jsonBlock) {
        Map<String, String> values = new HashMap<>();

        jsonBlock = jsonBlock.replace("{", "")
                .replace("}", "")
                .trim();

        String[] lines = jsonBlock.split(",");

        for (String line : lines) {
            String[] pair = line.split(":");
            if (pair.length == 2) {
                String key = pair[0].replace("\"", "").trim();
                String value = pair[1].replace("\"", "").trim();
                values.put(key, value);
            }
        }
        return values;
    }
}
