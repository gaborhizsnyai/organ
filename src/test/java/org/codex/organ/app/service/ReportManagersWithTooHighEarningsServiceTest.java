package org.codex.organ.app.service;

import org.codex.organ.domain.model.Employee;
import org.codex.organ.domain.model.Name;
import org.codex.organ.domain.port.EmployeeRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ReportManagersWithTooHighEarningsServiceTest {

    @Test
    void managerWithTooHighEarning() {
        var repository = new MockEmployeeRepository();
        var service = new ReportManagersWithTooHighEarningsService(repository);

        var manager = new Employee(1L, new Name("John", "Doe"), 3800, null);
        var employee1 = new Employee(2L, new Name("Jane", "Doe"), 2000, 1L);
        var employee2 = new Employee(3L, new Name("Jack", "Doe"), 3000, 1L);

        employee1.setManager(manager);
        employee2.setManager(manager);

        repository.employees.add(manager);
        repository.employees.add(employee1);
        repository.employees.add(employee2);

        var report = service.exec(50);

        assertEquals("Managers with too high earnings", report.title());
        assertEquals(1, report.items().size());
        assertEquals("John Doe (1) has earnings higher than expected by 50", report.items().getFirst().toString());
    }

    @Test
    void managerWithNoSubordinates() {
        var repository = new MockEmployeeRepository();
        var service = new ReportManagersWithTooHighEarningsService(repository);

        var manager = new Employee(1L, new Name("John", "Doe"), 3800, null);

        repository.employees.add(manager);

        var report = service.exec(50);

        assertEquals("Managers with too high earnings", report.title());
        assertTrue(report.items().isEmpty());
    }

    @Test
    void managerWithSubordinatesEarningLess() {
        var repository = new MockEmployeeRepository();
        var service = new ReportManagersWithTooHighEarningsService(repository);

        var manager = new Employee(1L, new Name("John", "Doe"), 3700, null);
        var employee1 = new Employee(2L, new Name("Jane", "Doe"), 2000, 1L);
        var employee2 = new Employee(3L, new Name("Jack", "Doe"), 3000, 1L);

        employee1.setManager(manager);
        employee2.setManager(manager);

        repository.employees.add(manager);
        repository.employees.add(employee1);
        repository.employees.add(employee2);

        var report = service.exec(50);

        assertEquals("Managers with too high earnings", report.title());
        assertTrue(report.items().isEmpty());
    }

    @Test
    void managerWithSubordinatesEarningEqual() {
        var repository = new MockEmployeeRepository();
        var service = new ReportManagersWithTooHighEarningsService(repository);

        var manager = new Employee(1L, new Name("John", "Doe"), 2500, null);
        var employee1 = new Employee(2L, new Name("Jane", "Doe"), 2000, 1L);
        var employee2 = new Employee(3L, new Name("Jack", "Doe"), 3000, 1L);

        employee1.setManager(manager);
        employee2.setManager(manager);

        repository.employees.add(manager);
        repository.employees.add(employee1);
        repository.employees.add(employee2);

        var report = service.exec(0);

        assertEquals("Managers with too high earnings", report.title());
        assertTrue(report.items().isEmpty());
    }

    private static class MockEmployeeRepository implements EmployeeRepository {
        List<Employee> employees = new ArrayList<>();

        @Override
        public Stream<Employee> streamAll() {
            return employees.stream();
        }
    }
}