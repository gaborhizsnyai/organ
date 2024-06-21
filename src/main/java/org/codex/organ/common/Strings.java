package org.codex.organ.common;

import java.util.Arrays;

public class Strings {
    private Strings() {}

    public static String[] normalize(String[] values) {
        return Arrays.stream(values)
                .map(String::trim)
                .toArray(String[]::new);
    }
}
