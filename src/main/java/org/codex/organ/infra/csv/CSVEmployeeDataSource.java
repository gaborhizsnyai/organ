package org.codex.organ.infra.csv;

import org.codex.organ.app.port.EmployeeDataSource;
import org.codex.organ.app.port.EmployeeRecord;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

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
