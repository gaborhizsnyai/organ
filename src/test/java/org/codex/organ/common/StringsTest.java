package org.codex.organ.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringsTest {

    @Test
    void normalizeWithPaddedStrings() {
        var values = new String[] { "  a", "b  ", "  c  ", "  " };

        var result = Strings.normalize(values);

        assertArrayEquals(new String[] { "a", "b", "c", "" }, result);
    }

    @Test
    void normalizeWithNullValues() {
        var values = new String[] { null, "n ull  ", null };

        var result = Strings.normalize(values);

        assertArrayEquals(new String[] { null, "n ull", null }, result);
    }

    @Test
    void parseLongOrNullWithNullValue() {
        var result = Strings.parseLongOrNull(null);

        assertNull(result);
    }

    @Test
    void parseLongOrNullWithEmptyValue() {
        var result = Strings.parseLongOrNull("");

        assertNull(result);
    }

    @Test
    void parseLongOrNullWithValidValue() {
        var result = Strings.parseLongOrNull("123");

        assertEquals(123L, result);
    }

    @Test
    void parseLongOrNullWithInvalidValue() {
        assertThrows(NumberFormatException.class, () -> Strings.parseLongOrNull("abc"));
    }
}