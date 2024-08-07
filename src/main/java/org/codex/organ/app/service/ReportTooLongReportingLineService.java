package org.codex.organ.app.service;

import org.codex.organ.app.dto.Report;
import org.codex.organ.app.usecase.ReportTooLongReportingLineUseCase;
import org.codex.organ.domain.model.Employee;
import org.codex.organ.domain.repository.EmployeeRepository;

/**
 * Service that provides a report of employees with reporting line longer than expected.
 */
public class ReportTooLongReportingLineService implements ReportTooLongReportingLineUseCase {

    private final EmployeeRepository repository;

    public ReportTooLongReportingLineService(EmployeeRepository repository) {
        this.repository = repository;
    }

    /**
     * Creates a report of employees with reporting line longer than expected.
     * @param threshold the maximum number of managers in the reporting line between an employee and the CEO
     * @return the report
     */
    @Override
    public Report exec(Integer threshold) {
        var items = repository.streamAll()
                .map(employee -> Finding.of(employee, threshold))
                .filter(Finding::hasAnythingToReport)
                .map(Finding::reportItem)
                .toList();

        return new Report("Employees with too long reporting line", items);
    }

    /**
     * An item in the report.
     * @param employee
     * @param difference the difference by which the reporting line is longer than expected.
     */
    public record Finding(Employee employee, Integer difference) {
        public static Finding of(Employee employee, Integer threshold) {
            return new Finding(employee, employee.getReportingLine() - threshold);
        }

        public boolean hasAnythingToReport() {
            return difference > 0;
        }

        public Report.Item reportItem() {
            return new Report.Item(
                    employee,
                    "has reporting line longer than expected by " + difference
            );
        }
    }

}
