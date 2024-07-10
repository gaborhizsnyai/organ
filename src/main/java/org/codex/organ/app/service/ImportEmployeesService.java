package org.codex.organ.app.service;

import org.codex.organ.app.mapper.EmployeeImportMapper;
import org.codex.organ.app.usecase.ImportEmployeesUseCase;
import org.codex.organ.app.port.EmployeeDataSource;
import org.codex.organ.common.WrappingException;
import org.codex.organ.domain.repository.EmployeeRepository;

import java.io.InputStream;

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

    public Integer exec(InputStream source) {
        try (var stream = dataSource.stream(source)) {
            stream.map(mapper::toEntity)
                    .forEach(repository::save);
            return repository.countAll();
        } catch (Exception e) {
            throw new WrappingException(e);
        }
    }
}
