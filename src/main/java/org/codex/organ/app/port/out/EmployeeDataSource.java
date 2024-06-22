package org.codex.organ.app.port.out;

import org.codex.organ.app.dto.EmployeeRecord;

import java.io.IOException;
import java.util.stream.Stream;

/**
 * A data source to read employee records from.
 */
public interface EmployeeDataSource {
    Stream<EmployeeRecord> stream(String source) throws IOException;
}
