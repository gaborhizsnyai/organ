package org.codex.organ.app.port;

import org.codex.organ.app.dto.EmployeeRecord;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

/**
 * A data source to read employee records from.
 */
public interface EmployeeDataSource {
    Stream<EmployeeRecord> stream(InputStream source) throws IOException;
}
