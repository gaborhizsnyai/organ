package org.codex.organ.infra.csv;

import org.codex.organ.app.port.out.EmployeeDataSource;
import org.codex.organ.app.dto.EmployeeRecord;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * CSVEmployeeDataSource is an implementation of {@link EmployeeDataSource}
 * that reads employee records from a CSV file.
 */
public class CSVEmployeeDataSource implements EmployeeDataSource {
    private final CSVParser parser;

    public CSVEmployeeDataSource(CSVParser parser) {
        this.parser = parser;
    }

    @Override
    public Stream<EmployeeRecord> stream(String source) throws IOException {
        return parser.parse(Path.of(source))
                .map(EmployeeRecord::of);
    }
}
