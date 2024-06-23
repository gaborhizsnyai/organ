package org.codex.organ.domain.port;

import org.codex.organ.domain.model.Employee;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Repository for {@link Employee}.
 */
public interface EmployeeRepository {
    default Optional<Employee> findById(Long id) { throw new UnsupportedOperationException("Not implemented"); }

    default Stream<Employee> streamAll() { throw new UnsupportedOperationException("Not implemented"); }

    default Integer countAll() { throw new UnsupportedOperationException("Not implemented"); }

    default void save(Employee employee) { throw new UnsupportedOperationException("Not implemented"); }
}
