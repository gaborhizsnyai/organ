package org.codex.organ.app.port.in;

import java.io.InputStream;

/**
 * Use case to import employees from a data source.
 */
public interface ImportEmployeesUseCase {

    /**
     * Imports employees from the given source.
     *
     * @param source the source to import employees from
     * @return the number of imported employees
     */
    Integer exec(InputStream source);
}
