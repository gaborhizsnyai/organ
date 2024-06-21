package org.codex.organ.app.port;

import org.codex.organ.domain.model.Name;

import java.util.Map;

public record EmployeeRecord(
    Long id,
    Name name,
    Integer salary,
    Long managerId
) {
    public EmployeeRecord {
        if (id == null) {
            throw new IllegalArgumentException("id is required");
        }
        if (name == null) {
            throw new IllegalArgumentException("name is required");
        }
        if (salary == null) {
            throw new IllegalArgumentException("salary is required");
        }
    }

    public static EmployeeRecord of(Map<String, String> values) {
        return new EmployeeRecord(
            Long.parseLong(values.get("Id")),
            new Name(values.get("firstName"), values.get("lastName")),
            Integer.parseInt(values.get("salary")),
            Long.parseLong(values.get("managerId"))
        );
    }
}
