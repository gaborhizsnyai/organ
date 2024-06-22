package org.codex.organ.infra.memdb;

import org.codex.organ.domain.model.Employee;
import org.codex.organ.domain.model.Name;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryEmployeeRepositoryTest {

    @Test
    void findById() {
        var repository = new InMemoryEmployeeRepository();
        var cto = new Employee(1L, new Name("John", "Doe"), 1000, null);

        repository.save(cto);

        var found = repository.findById(1L);

        assertTrue(found.isPresent());
        assertEquals(cto, found.get());
    }

    @Test
    void findById_notFound() {
        var repository = new InMemoryEmployeeRepository();
        var found = repository.findById(1L);

        assertTrue(found.isEmpty());
    }

    @Test
    void saveCtoAndEmployee() {
        var repository = new InMemoryEmployeeRepository();
        var cto = new Employee(1L, new Name("John", "Doe"), 1000, null);
        var employee = new Employee(2L, new Name("Jane", "Doe"), 500, 1L);

        repository.save(cto);
        repository.save(employee);

        var foundCto = repository.findById(1L);
        var foundEmployee = repository.findById(2L);

        assertTrue(foundCto.isPresent());
        assertEquals(cto, foundCto.get());

        assertTrue(foundEmployee.isPresent());
        assertEquals(employee, foundEmployee.get());

        assertEquals(1, cto.getSubordinates().size());
        assertEquals(employee, cto.getSubordinates().getFirst());

        assertEquals(0, employee.getReportingLine());
    }

    @Test
    void saveEmployeeWithUnknownManager() {
        var repository = new InMemoryEmployeeRepository();
        var employee = new Employee(1L, new Name("John", "Doe"), 1000, 2L);

        assertThrows(IllegalArgumentException.class, () -> repository.save(employee));
    }

    @Test
    void saveEmployeeWithManagers() {
        var repository = new InMemoryEmployeeRepository();
        var cto = new Employee(1L, new Name("John", "Doe"), 1000, null);
        var manager = new Employee(2L, new Name("Jane", "Doe"), 500, 1L);
        var employee = new Employee(3L, new Name("Jack", "Doe"), 300, 2L);

        repository.save(cto);
        repository.save(manager);
        repository.save(employee);

        assertEquals(1, cto.getSubordinates().size());
        assertEquals(manager, cto.getSubordinates().getFirst());

        assertEquals(1, manager.getSubordinates().size());
        assertEquals(employee, manager.getSubordinates().getFirst());

        assertEquals(1, employee.getReportingLine());
    }
}