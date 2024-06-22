package org.codex.organ.app.service;

import org.codex.organ.app.dto.Report;
import org.codex.organ.app.port.in.ReportManagersWithLowEarningsUseCase;
import org.codex.organ.domain.model.Employee;
import org.codex.organ.domain.port.EmployeeRepository;

/**
 * Service that generates a report of managers with earnings lower than expected.
 */
public class ReportManagersWithTooLowEarningsService implements ReportManagersWithLowEarningsUseCase {

    private final EmployeeRepository repository;

    public ReportManagersWithTooLowEarningsService(EmployeeRepository repository) {
        this.repository = repository;
    }

    /**
     * Generates a report of managers with earnings lower than expected.
     *
     * @param thresholdPercentage the percentage above the average salary of subordinates.
     * @return the report
     */
    @Override
    public Report exec(Integer thresholdPercentage) {
        var items = repository.streamAll()
                .filter(Employee::hasSubordinates)
                .map(employee -> Finding.of(employee, thresholdPercentage))
                .filter(Finding::isValid)
                .map(Finding::toString)
                .toList();

        return new Report("Managers with too low earnings", items);
    }

    /**
     * Report item.
     * @param employee
     * @param deficit the difference by which the earnings are lower than expected.
     */
    public record Finding(Employee employee, Integer deficit) {
        public static Finding of(Employee employee, Integer thresholdPercentage) {
            var threshold = employee.getSubordinatesAverageSalary() * (100 + thresholdPercentage) / 100;
            var deficit = threshold - employee.getSalary();

            return new Finding(employee, (int) Math.ceil(deficit));
        }

        public boolean isValid() {
            return deficit > 0;
        }

        @Override
        public String toString() {
            return "%s has earnings lower than expected by %d".formatted(employee, deficit);
        }
    }
}
