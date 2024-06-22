package org.codex.organ.app.service;

import org.codex.organ.app.dto.Report;
import org.codex.organ.app.port.in.ReportManagersWithHighEarningsUseCase;
import org.codex.organ.domain.model.Employee;
import org.codex.organ.domain.port.EmployeeRepository;

/**
 * Service that reports managers with earnings higher than expected.
 */
public class ReportManagersWithTooHighEarningsService implements ReportManagersWithHighEarningsUseCase {

    private final EmployeeRepository repository;

    public ReportManagersWithTooHighEarningsService(EmployeeRepository repository) {
        this.repository = repository;
    }

    /**
     * Creates a report of managers with earnings higher than expected.
     * @param thresholdPercentage the percentage above the average salary of subordinates.
     * @return the report.
     */
    @Override
    public Report exec(Integer thresholdPercentage) {
        var items = repository.streamAll()
                .filter(Employee::hasSubordinates)
                .map(employee -> Finding.of(employee, thresholdPercentage))
                .filter(Finding::isValid)
                .map(Finding::toString)
                .toList();

        return new Report("Managers with too high earnings", items);
    }

    /**
     * Report item.
     * @param employee
     * @param surplus the difference by which the earnings are higher than expected.
     */
    public record Finding(Employee employee, Integer surplus) {
        public static Finding of(Employee employee, Integer thresholdPercentage) {
            var threshold = employee.getSubordinatesAverageSalary() * (100 + thresholdPercentage) / 100;
            var surplus = employee.getSalary() - threshold;

            return new Finding(employee, (int) Math.ceil(surplus));
        }

        public boolean isValid() {
            return surplus > 0;
        }

        @Override
        public String toString() {
            return "%s has earnings higher than expected by %d".formatted(employee, surplus);
        }
    }
}
