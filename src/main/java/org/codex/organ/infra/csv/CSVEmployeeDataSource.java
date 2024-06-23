package org.codex.organ.infra.csv;

import org.codex.organ.app.dto.EmployeeRecord;
import org.codex.organ.app.port.out.EmployeeDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * CSVEmployeeDataSource is an implementation of {@link EmployeeDataSource}
 * that reads employee records from a CSV file.
 */
public class CSVEmployeeDataSource implements EmployeeDataSource {
    @Override
    public Stream<EmployeeRecord> stream(InputStream source) throws IOException {
        var split = new CSVSpliterator(source);
        return StreamSupport.stream(split, false)
                .map(EmployeeRecord::of);
    }
}
