package org.codex.organ.common;

import java.util.Arrays;

public class Strings {
    private Strings() {}

    /**
     * Normalize the array of string values by trimming each value.
     *
     * @param values the values to normalize
     * @return the normalized values
     */
    public static String[] normalize(String[] values) {
        return Arrays.stream(values)
                .map(s -> s == null ? null : s.trim())
                .toArray(String[]::new);
    }

    /**
     * Parse the string value to a long value or return null if the value is null or empty.
     *
     * @param value the value to parse
     * @return the parsed long value or null
     * @throws NumberFormatException if the value is present and is not a valid long
     */
    public static Long parseLongOrNull(String value) {
        return value == null || value.isEmpty() ? null : Long.parseLong(value);
    }
}
