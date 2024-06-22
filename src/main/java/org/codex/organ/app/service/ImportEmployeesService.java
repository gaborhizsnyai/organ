package org.codex.organ.app.service;

import org.codex.organ.app.mapper.EmployeeImportMapper;
import org.codex.organ.app.port.in.ImportEmployeesUseCase;
import org.codex.organ.app.port.out.EmployeeDataSource;
import org.codex.organ.domain.port.EmployeeRepository;

/**
 * Service to import employees from a data source and save them in the repository.
 */
public class ImportEmployeesService implements ImportEmployeesUseCase {

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
            stream.map(mapper::toEntity)
                    .forEach(repository::save);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error importing employees", e);
        }
    }
}
