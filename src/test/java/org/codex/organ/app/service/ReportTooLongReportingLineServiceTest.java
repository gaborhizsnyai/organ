package org.codex.organ.app.service;

import org.codex.organ.domain.model.Employee;
import org.codex.organ.domain.model.Name;
import org.codex.organ.domain.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ReportTooLongReportingLineServiceTest {

    @Test
    void tooLongReportingLine() {
        var repository = new MockEmployeeRepository();
        var service = new ReportTooLongReportingLineService(repository);

        var ceo = new Employee(1L, new Name("John", "Doe"), null, null);
        var manager = new Employee(2L, new Name("Jane", "Doe"), null, 1L);
        var employee = new Employee(3L, new Name("Jack", "Doe"), null, 2L);

        manager.setManager(ceo);
        employee.setManager(manager);

        repository.employees.add(ceo);
        repository.employees.add(manager);
        repository.employees.add(employee);

        var report = service.exec(0);

        assertEquals("Employees with too long reporting line", report.title());
        assertEquals(1, report.items().size());
        assertEquals("Jack Doe (3) has reporting line longer than expected by 1", report.items().getFirst().toString());
    }

    @Test
    void reportingLineWithinThreshold() {
        var repository = new MockEmployeeRepository();
        var service = new ReportTooLongReportingLineService(repository);

        var ceo = new Employee(1L, new Name("John", "Doe"), null, null);
        var manager = new Employee(2L, new Name("Jane", "Doe"), null, 1L);
        var employee = new Employee(3L, new Name("Jack", "Doe"), null, 2L);

        manager.setManager(ceo);
        employee.setManager(manager);

        repository.employees.add(ceo);
        repository.employees.add(manager);
        repository.employees.add(employee);

        var report = service.exec(1);

        assertEquals("Employees with too long reporting line", report.title());
        assertEquals(0, report.items().size());
    }

    private static class MockEmployeeRepository implements EmployeeRepository {
        List<Employee> employees = new ArrayList<>();

        @Override
        public Stream<Employee> streamAll() {
            return employees.stream();
        }
    }

}