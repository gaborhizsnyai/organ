package org.codex.organ.infra.csv;

import org.codex.organ.common.Strings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class CSVRowMapper {
    private final String[] header;

    public CSVRowMapper(String[] header) {
        this.header = header;
    }

    public static CSVRowMapper fromCSVHeader(Path source) throws IOException {
        try (Stream<String> lines = Files.lines(source)) {
            return lines.findFirst()
                .map(line -> line.split(","))
                .map(Strings::normalize)
                .map(CSVRowMapper::new)
                .orElseThrow(() -> new IllegalArgumentException("Empty file: " + source));
        }
    }

    public Map<String, String> map(String[] values) {
        Map<String, String> result = new HashMap<>();

        for (int i = 0; i < header.length; i++) {
            result.put(header[i], values[i]);
        }

        return result;
    }
}
