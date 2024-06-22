package org.codex.organ.infra.csv;

import org.codex.organ.common.Strings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Maps a CSV row to a map of column names to values
 */
public class CSVRowMapper {
    private final String[] header;

    public CSVRowMapper(String[] header) {
        this.header = header;
    }

    /**
     * Create a row mapper from the header row of a CSV file
     *
     * @param source the path to the CSV file
     * @return a row mapper
     * @throws IOException if an I/O error occurs
     */
    public static CSVRowMapper fromCSVHeader(Path source) throws IOException {
        try (Stream<String> lines = Files.lines(source)) {
            return lines.findFirst()
                .map(line -> line.split(","))
                .map(Strings::normalize)
                .map(CSVRowMapper::new)
                .orElseThrow(() -> new IllegalArgumentException("Empty file: " + source));
        }
    }

    /**
     * Map a CSV row to a map of column names to values
     *
     * @param line the CSV row in raw string form
     * @return a map of column names to values
     */
    public Map<String, String> map(String line) {
        Map<String, String> result = new HashMap<>();

        var values = Strings.normalize(line.split(","));
        for (int i = 0; i < Math.min(header.length, values.length); i++) {
            result.put(header[i], values[i]);
        }

        return result;
    }
}
