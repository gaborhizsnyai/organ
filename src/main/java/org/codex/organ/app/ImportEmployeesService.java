package org.codex.organ.app;

import org.codex.organ.app.mapper.EmployeeImportMapper;
import org.codex.organ.app.port.EmployeeDataSource;
import org.codex.organ.app.port.EmployeeRecord;
import org.codex.organ.common.Relation;
import org.codex.organ.domain.port.EmployeeRepository;

public class ImportEmployeesService {

    private final EmployeeRepository repository;
    private final EmployeeDataSource dataSource;
    private final EmployeeImportMapper mapper;

    public ImportEmployeesService(EmployeeRepository repository,
                                  EmployeeDataSource dataSource,
                                  EmployeeImportMapper mapper) {
        this.repository = repository;
        this.dataSource = dataSource;
        this.mapper = mapper;
    }

    public void exec(String source) {
        try (var stream = dataSource.stream(source)) {
            stream.map(this::saveEmployee)
                    .toList()
                    .forEach(repository::resolveRelation);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error importing employees", e);
        }
    }

    private Relation<Long, Long> saveEmployee(EmployeeRecord data) {
        repository.save(mapper.toEntity(data));
        return mapper.toRelation(data);
    }
}
