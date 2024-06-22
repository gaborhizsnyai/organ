package org.codex.organ.app.service;

import org.codex.organ.app.dto.Report;
import org.codex.organ.app.port.in.ReportTooLongReportingLineUseCase;
import org.codex.organ.domain.model.Employee;
import org.codex.organ.domain.port.EmployeeRepository;

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
                .filter(Finding::isValid)
                .map(Finding::toString)
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

        public boolean isValid() {
            return difference > 0;
        }

        @Override
        public String toString() {
            return "%s has reporting line longer than expected by %d".formatted(employee, difference);
        }
    }

}
