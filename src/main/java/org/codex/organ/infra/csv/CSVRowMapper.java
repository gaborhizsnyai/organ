package org.codex.organ.infra.csv;

import org.codex.organ.common.Strings;

import java.util.HashMap;
import java.util.Map;

/**
 * Maps a CSV row to a map of column names to values
 */
public class CSVRowMapper {
    private final String[] header;

    public CSVRowMapper(String[] header) {
        this.header = Strings.normalize(header);
    }

    public CSVRowMapper(String header) {
        this(header.split(","));
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
