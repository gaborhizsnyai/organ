package org.codex.organ.app.dto;

import org.codex.organ.common.Strings;
import org.codex.organ.domain.model.Name;

import java.util.Map;

/**
 * EmployeeRecord is a public record that represents the data of an employee.
 * It is to transfer data between the data source the application during the import process.
 */
public record EmployeeRecord(
    Long id,
    Name name,
    Integer salary,
    Long managerId
) {
    /**
     * Create an EmployeeRecord from a map of values.
     * The map must contain the following keys:
     * <br/>
     * Id, firstName, lastName, salary, managerId
     * <br/>
     * managerId is optional and can be null.
     * @param values the map of values
     * @throws NumberFormatException if the numeric values are not in the expected format
     * @return the EmployeeRecord
     */
    public static EmployeeRecord of(Map<String, String> values) {
        return new EmployeeRecord(
            Long.parseLong(values.get("Id")),
            new Name(values.get("firstName"), values.get("lastName")),
            Integer.parseInt(values.get("salary")),
            Strings.parseLongOrNull(values.get("managerId"))
        );
    }

    /**
     * Validate the record externally.
     * @throws IllegalArgumentException if the record is not valid
     */
    public void validate() {
        if (id == null) {
            throw new IllegalArgumentException("id is required");
        }
        if (name == null) {
            throw new IllegalArgumentException("name is required");
        }
        if (salary == null || salary < 0) {
            throw new IllegalArgumentException("salary is required and must be positive");
        }
    }
}
