package org.codex.organ.domain.model;

public record Name(String first, String last) {

    @Override
    public String toString() {
        return "%s %s".formatted(first, last);
    }
}