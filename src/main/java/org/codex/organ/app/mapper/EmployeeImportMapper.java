package org.codex.organ.app.mapper;

import org.codex.organ.app.port.EmployeeRecord;
import org.codex.organ.common.Relation;
import org.codex.organ.domain.model.Employee;

public class EmployeeImportMapper {

    public Employee toEntity(EmployeeRecord source) {
        return new Employee(
            source.id(),
            source.name(),
            source.salary()
        );
    }

    public Relation<Long, Long> toRelation(EmployeeRecord source) {
        return new Relation<>(source.managerId(), source.id());
    }
}
