package org.codex.organ.app.dto;

import org.codex.organ.domain.model.Name;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeRecordTest {

    @Test
    void createWithWellFormedMap() {
        var values = Map.of(
            "Id", "1",
            "firstName", "John",
            "lastName", "Doe",
            "salary", "1000",
            "managerId", "2"
        );

        var data = EmployeeRecord.of(values);

        assertEquals(1, data.id());
        assertEquals("John", data.name().first());
        assertEquals("Doe", data.name().last());
        assertEquals(1000, data.salary());
        assertEquals(2, data.managerId());
    }

    @Test
    void createWithoutManagerId() {
        var values = Map.of(
            "Id", "1",
            "firstName", "John",
            "lastName", "Doe",
            "salary", "1000"
        );

        var data = EmployeeRecord.of(values);

        assertNull(data.managerId());
    }

    @Test
    void createWithInvalidId() {
        var values = Map.of(
            "Id", "invalid",
            "firstName", "John",
            "lastName", "Doe",
            "salary", "1000"
        );

        assertThrows(NumberFormatException.class, () -> EmployeeRecord.of(values));
    }

    @Test
    void createWithInvalidSalary() {
        var values = Map.of(
            "Id", "1",
            "firstName", "John",
            "lastName", "Doe",
            "salary", "invalid"
        );

        assertThrows(NumberFormatException.class, () -> EmployeeRecord.of(values));
    }

    @Test
    void validateCompleteRecord() {
        var data = new EmployeeRecord(1L, new Name("John", "Doe"), 1000, 2L);
        assertDoesNotThrow(data::validate);
    }

    @Test
    void validateFailsWithNoId() {
        var data = new EmployeeRecord(null, new Name("John", "Doe"), 1000, 2L);
        assertThrows(IllegalArgumentException.class, data::validate);
    }

    @Test
    void validateFailsWithNoName() {
        var data = new EmployeeRecord(1L, null, 1000, 2L);
        assertThrows(IllegalArgumentException.class, data::validate);
    }

    @Test
    void validateFailsWithNegativeSalary() {
        var data = new EmployeeRecord(1L, new Name("John", "Doe"), -1000, 2L);
        assertThrows(IllegalArgumentException.class, data::validate);
    }
}