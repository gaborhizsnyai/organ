package org.codex.organ.common;

import org.codex.organ.app.mapper.EmployeeImportMapper;
import org.codex.organ.app.usecase.ImportEmployeesUseCase;
import org.codex.organ.app.usecase.ReportManagersWithHighEarningsUseCase;
import org.codex.organ.app.usecase.ReportManagersWithLowEarningsUseCase;
import org.codex.organ.app.usecase.ReportTooLongReportingLineUseCase;
import org.codex.organ.app.service.ImportEmployeesService;
import org.codex.organ.app.service.ReportManagersWithTooHighEarningsService;
import org.codex.organ.app.service.ReportManagersWithTooLowEarningsService;
import org.codex.organ.app.service.ReportTooLongReportingLineService;
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

    public ApplicationConfig() {
        var employeeRepository = new InMemoryEmployeeRepository();
        var employeeDataSource = new CSVEmployeeDataSource();
        var employeeImportMapper = new EmployeeImportMapper();

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
