package org.codex.organ.app.port;

import java.io.IOException;
import java.util.stream.Stream;

public interface EmployeeDataSource {

    Stream<EmployeeRecord> stream(String source) throws IOException;
}
