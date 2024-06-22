package org.codex.organ.infra.csv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Simple CSV parser
 * <br/>
 * File format:
 * <ul
 *    <li>First row is the header row. It contains the names of the columns.</li>
 *    <li>Subsequent rows are data rows. Each row contains the values for each column.</li>
 *    <li>Each row is a comma-separated list of values.</li>
 *    <li>Values are not quoted.</li>
 * </ul>
 */
public class CSVParser {

    /**
     * Parse a CSV file
     *
     * @param source the path to the CSV file
     * @return a stream of maps, where each map represents a row in the CSV file. The keys are the column names.
     * @throws IOException if an I/O error occurs
     */
    public Stream<Map<String, String>> parse(Path source) throws IOException {
        validate(source);

        var rowMapper = CSVRowMapper.fromCSVHeader(source);

        return Files.lines(source)
                .skip(1)
                .map(rowMapper::map);
    }

    private void validate(Path path) {
        if (!Files.exists(path))
            throw new IllegalArgumentException("File does not exist: " + path);

        if (!Files.isRegularFile(path))
            throw new IllegalArgumentException("Not a file: " + path);

        if (!Files.isReadable(path))
            throw new IllegalArgumentException("File is not readable: " + path);
    }
}
