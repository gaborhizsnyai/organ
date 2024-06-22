package org.codex.organ.app.mapper;

import org.codex.organ.app.dto.EmployeeRecord;
import org.codex.organ.domain.model.Employee;

/**
 * Maps an {@link EmployeeRecord} to an {@link Employee}.
 */
public class EmployeeImportMapper {

    /**
     * Maps an {@link EmployeeRecord} to an {@link Employee}.
     *
     * @param source the source {@link EmployeeRecord}
     * @throws IllegalArgumentException if the source is not valid
     * @return the mapped {@link Employee}
     */
    public Employee toEntity(EmployeeRecord source) {
        source.validate();

        return new Employee(
            source.id(),
            source.name(),
            source.salary(),
            source.managerId()
        );
    }
}
