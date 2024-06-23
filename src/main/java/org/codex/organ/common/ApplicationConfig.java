package org.codex.organ.common;

import org.codex.organ.app.mapper.EmployeeImportMapper;
import org.codex.organ.app.port.in.ImportEmployeesUseCase;
import org.codex.organ.app.port.in.ReportManagersWithHighEarningsUseCase;
import org.codex.organ.app.port.in.ReportManagersWithLowEarningsUseCase;
import org.codex.organ.app.port.in.ReportTooLongReportingLineUseCase;
import org.codex.organ.app.service.ImportEmployeesService;
import org.codex.organ.app.service.ReportManagersWithTooHighEarningsService;
import org.codex.organ.app.service.ReportManagersWithTooLowEarningsService;
import org.codex.organ.app.service.ReportTooLongReportingLineService;
import org.codex.organ.domain.port.EmployeeRepository;
import org.codex.organ.infra.csv.CSVEmployeeDataSource;
import org.codex.organ.infra.memdb.InMemoryEmployeeRepository;

/**
 * A basic configuration class for the application using the CSV data source and in-memory repository.
 */
public class ApplicationConfig {
    private final ImportEmployeesUseCase importEmployeesUseCase;
    private final ReportManagersWithHighEarningsUseCase reportManagersWithHighEarningsUseCase;
    private final ReportManagersWithLowEarningsUseCase reportManagersWithLowEarningsUseCase;
    private final ReportTooLongReportingLineUseCase reportTooLongReportingLineUseCase;

    private final EmployeeRepository employeeRepository;

    public ApplicationConfig() {
        var employeeDataSource = new CSVEmployeeDataSource();
        var employeeImportMapper = new EmployeeImportMapper();

        this.employeeRepository = new InMemoryEmployeeRepository();

        this.importEmployeesUseCase = new ImportEmployeesService(employeeRepository, employeeDataSource, employeeImportMapper);
        this.reportManagersWithHighEarningsUseCase = new ReportManagersWithTooHighEarningsService(employeeRepository);
        this.reportManagersWithLowEarningsUseCase = new ReportManagersWithTooLowEarningsService(employeeRepository);
        this.reportTooLongReportingLineUseCase = new ReportTooLongReportingLineService(employeeRepository);
    }

    public ImportEmployeesUseCase importEmployeesUseCase() {
        return importEmployeesUseCase;
    }

    public ReportManagersWithHighEarningsUseCase reportManagersWithHighEarningsUseCase() {
        return reportManagersWithHighEarningsUseCase;
    }

    public ReportManagersWithLowEarningsUseCase reportManagersWithLowEarningsUseCase() {
        return reportManagersWithLowEarningsUseCase;
    }

    public ReportTooLongReportingLineUseCase reportTooLongReportingLineUseCase() {
        return reportTooLongReportingLineUseCase;
    }
}
