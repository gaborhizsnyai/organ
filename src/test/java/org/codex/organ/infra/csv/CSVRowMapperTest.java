package org.codex.organ.infra.csv;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSVRowMapperTest {

    @Test
    void mapFullRecord() {
        var header = new String[] { "Id", "firstName", "lastName", "salary", "managerId" };
        var rowMapper = new CSVRowMapper(header);

        var line = "1, John,Doe,100000 ,  0";

        var result = rowMapper.map(line);

        assertEquals(5, result.size());
        assertEquals("1", result.get("Id"));
        assertEquals("John", result.get("firstName"));
        assertEquals("Doe", result.get("lastName"));
        assertEquals("100000", result.get("salary"));
        assertEquals("0", result.get("managerId"));
    }

    @Test
    void mapPartialRecord() {
        var header = new String[] { "Id", "firstName", "lastName", "salary", "managerId" };
        var rowMapper = new CSVRowMapper(header);

        var line = "1, John,Doe,100000";

        var result = rowMapper.map(line);

        assertEquals(4, result.size());
        assertEquals("1", result.get("Id"));
        assertEquals("John", result.get("firstName"));
        assertEquals("Doe", result.get("lastName"));
        assertEquals("100000", result.get("salary"));
        assertNull(result.get("managerId"));
    }

    @Test
    void mapEmptyRecord() {
        var header = new String[] { "Id", "firstName", "lastName", "salary", "managerId" };
        var rowMapper = new CSVRowMapper(header);

        var line = "";

        var result = rowMapper.map(line);

        assertEquals(1, result.size());
        assertEquals("", result.get("Id"));
    }

    @Test
    void mapWithEmptyManagerId() {
        var header = new String[] { "Id", "firstName", "lastName", "salary", "managerId" };
        var rowMapper = new CSVRowMapper(header);

        var line = "1, John,Doe,100000,";

        var result = rowMapper.map(line);

        assertEquals(4, result.size());
        assertEquals("1", result.get("Id"));
        assertEquals("John", result.get("firstName"));
        assertEquals("Doe", result.get("lastName"));
        assertEquals("100000", result.get("salary"));
        assertNull(result.get("managerId"));
    }
}