package org.codex.organ.infra.memdb;

import org.codex.organ.common.Relation;
import org.codex.organ.domain.model.Employee;
import org.codex.organ.domain.port.EmployeeRepository;
import org.codex.organ.common.Predicate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryEmployeeRepository implements EmployeeRepository {
    private final Map<Long, Employee> employees = new HashMap<>();

    @Override
    public Optional<Employee> findById(Long id) {
        return Optional.ofNullable(employees.get(id));
    }

    @Override
    public void save(Employee employee) {
        employees.put(employee.getId(), employee);
    }

    @Override
    public void resolveRelation(Relation<Long, Long> relation) {
        var manager = findById(relation.parent())
                .orElseThrow(() -> new IllegalArgumentException("Manager not found"));

        var employee = findById(relation.child())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        manager.addSubordinate(employee);
    }

    @Override
    public List<Employee> findBy(Predicate<Employee> predicate) {
        return employees.values().stream()
                .filter(predicate::test)
                .toList();
    }
}
