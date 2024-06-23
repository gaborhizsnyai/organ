package org.codex.organ.app.service;

import org.codex.organ.app.dto.EmployeeRecord;
import org.codex.organ.app.mapper.EmployeeImportMapper;
import org.codex.organ.app.port.out.EmployeeDataSource;
import org.codex.organ.domain.model.Employee;
import org.codex.organ.domain.model.Name;
import org.codex.organ.domain.port.EmployeeRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ImportEmployeesServiceTest {

    @Test
    void exec() {
        var mapper = new EmployeeImportMapper();
        var repository = new MockEmployeeRepository();
        var dataSource = new MockEmployeeDataSource();

        dataSource.records.add(new EmployeeRecord(1L, new Name("John", "Doe"), 1000, null));
        dataSource.records.add(new EmployeeRecord(2L, new Name("Jane", "Doe"), 2000, 1L));

        var service = new ImportEmployeesService(repository, dataSource, mapper);

        service.exec("source");

        assertEquals("source", dataSource.capturedSource);
        assertEquals(2, repository.capturedEmployees.size());
        assertEquals(new Employee(1L, new Name("John", "Doe"), 1000, null), repository.capturedEmployees.get(0));
        assertEquals(new Employee(2L, new Name("Jane", "Doe"), 2000, 1L), repository.capturedEmployees.get(1));
    }

    private static class MockEmployeeRepository implements EmployeeRepository {
        List<Employee> capturedEmployees = new ArrayList<>();

        @Override
        public void save(Employee employee) {
            capturedEmployees.add(employee);
        }

        @Override
        public Integer countAll() {
            return capturedEmployees.size();
        }
    }

    private static class MockEmployeeDataSource implements EmployeeDataSource {
        List<EmployeeRecord> records = new ArrayList<>();
        String capturedSource;

        @Override
        public Stream<EmployeeRecord> stream(String source) {
            capturedSource = source;
            return records.stream();
        }
    }
}