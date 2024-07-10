package org.codex.organ.infra.memdb;

import org.codex.organ.domain.model.Employee;
import org.codex.organ.domain.repository.EmployeeRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * In-memory implementation of {@link EmployeeRepository}.
 */
public class InMemoryEmployeeRepository implements EmployeeRepository {
    private final Map<Long, Employee> employees = new HashMap<>();

    @Override
    public Optional<Employee> findById(Long id) {
        return Optional.ofNullable(employees.get(id));
    }

    @Override
    public Integer countAll() {
        return employees.size();
    }

    @Override
    public Stream<Employee> streamAll() {
        return employees.values().stream();
    }

    private Employee findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found: " + id));
    }

    @Override
    public void save(Employee employee) {
        Optional.ofNullable(employee.getManagerId())
                .map(this::findByIdOrElseThrow)
                .ifPresent(employee::setManager);

        employees.put(employee.getId(), employee);
    }
}
