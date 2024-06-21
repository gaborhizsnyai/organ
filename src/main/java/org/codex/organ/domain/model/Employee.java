package org.codex.organ.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private final Long id;
    private final Name name;
    private final Integer salary;

    private Integer level = 0;
    private final List<Employee> subordinates = new ArrayList<>();

    public Employee(Long id, Name name, Integer salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<Employee> getSubordinates() {
        return subordinates;
    }

    public void addSubordinate(Employee employee) {
        subordinates.add(employee);
    }

    @Override
    public String toString() {
        return "%s (%d)".formatted(name, id);
    }

    public static class Builder {
        private Long id;
        private Name name;
        private Integer salary;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(Name name) {
            this.name = name;
            return this;
        }

        public Builder salary(Integer salary) {
            this.salary = salary;
            return this;
        }

        public Employee build() {
            return new Employee(id, name, salary);
        }
    }
}
