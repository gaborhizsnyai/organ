package org.codex.organ.domain.port;

import org.codex.organ.common.Predicate;
import org.codex.organ.common.Relation;
import org.codex.organ.domain.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {
    Optional<Employee> findById(Long id);

    void save(Employee employee);

    void resolveRelation(Relation<Long, Long> relation);

    List<Employee> findBy(Predicate<Employee> predicate);
}
