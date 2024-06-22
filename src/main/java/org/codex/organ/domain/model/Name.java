package org.codex.organ.domain.model;

/**
 * Represents a person's name.
 */
public record Name(String first, String last) {

    public Name {
        if (first == null || first.isBlank()) {
            throw new IllegalArgumentException("first name is required");
        }

        if (last == null || last.isBlank()) {
            throw new IllegalArgumentException("last name is required");
        }
    }

    @Override
    public String toString() {
        return "%s %s".formatted(first, last);
    }
}