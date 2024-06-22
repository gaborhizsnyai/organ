package org.codex.organ.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private final Long id;
    private final Name name;
    private final Integer salary;
    private final Long managerId;

    private Integer level = 0;
    private final List<Employee> subordinates = new ArrayList<>();

    public Employee(Long id, Name name, Integer salary, Long managerId) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.managerId = managerId;
    }

    public Long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public Integer getSalary() {
        return salary;
    }

    public Long getManagerId() {
        return managerId;
    }

    /**
     * @return the average salary of the subordinates of this manager or NaN if there are no subordinates
     */
    public Double getSubordinatesAverageSalary() {
        return subordinates.stream()
                .mapToInt(Employee::getSalary)
                .average()
                .orElse(Double.NaN);
    }

    /**
     * @return the number of managers between this employee and the CEO
     */
    public Integer getReportingLine() {
        return level - 1;
    }

    public List<Employee> getSubordinates() {
        return subordinates;
    }

    public boolean hasSubordinates() {
        return !subordinates.isEmpty();
    }

    public void setManager(Employee manager) {
        manager.addSubordinate(this);
        level = manager.level + 1;
    }

    private void addSubordinate(Employee employee) {
        subordinates.add(employee);
    }

    @Override
    public String toString() {
        return "%s (%d)".formatted(name, id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Employee employee = (Employee) obj;

        return id.toString().equals(employee.id.toString());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
