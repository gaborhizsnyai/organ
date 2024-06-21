package org.codex.organ.app;

import org.codex.organ.domain.model.Employee;
import org.codex.organ.common.Predicate;

public class ManagerWithTooLowEarningPredicate implements Predicate<Employee> {

    private final Integer minEarningPercentage;

    public ManagerWithTooLowEarningPredicate(Integer minEarningPercentage) {
        this.minEarningPercentage = minEarningPercentage;
    }

    @Override
    public boolean test(Employee employee) {
        var average = employee.getSubordinates()
                .stream()
                .mapToInt(Employee::getSalary)
                .average()
                .orElse(0);
        return average > (employee.getSalary() * minEarningPercentage / 100.0);
    }
}
