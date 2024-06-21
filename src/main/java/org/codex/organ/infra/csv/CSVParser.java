package org.codex.organ.infra.csv;

import org.codex.organ.common.Strings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Stream;

public class CSVParser {

    public Stream<Map<String, String>> parse(Path source) throws IOException {
        validate(source);

        var rowMapper = CSVRowMapper.fromCSVHeader(source);

        return Files.lines(source)
                .skip(1)
                .map(line -> line.split(","))
                .map(Strings::normalize)
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
