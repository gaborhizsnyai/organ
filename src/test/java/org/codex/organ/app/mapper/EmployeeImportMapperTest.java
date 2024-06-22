package org.codex.organ.app.mapper;

import org.codex.organ.app.dto.EmployeeRecord;
import org.codex.organ.domain.model.Name;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmployeeImportMapperTest {

    @Test
    void convertValidRecordToEntity() {
        var source = new EmployeeRecord(1L, new Name("John", "Doe"), 1000, 2L);

        var result = new EmployeeImportMapper().toEntity(source);

        assertEquals(1, result.getId());
        assertEquals("John", result.getName().first());
        assertEquals("Doe", result.getName().last());
        assertEquals(1000, result.getSalary());
        assertEquals(2L, result.getManagerId());
    }

    @Test
    void failToConvertRecordWithNullIdToEntity() {
        var source = new EmployeeRecord(null, new Name("John", "Doe"), 1000, 2L);

        var mapper = new EmployeeImportMapper();

        assertThrows(IllegalArgumentException.class, () -> mapper.toEntity(source));
    }
}