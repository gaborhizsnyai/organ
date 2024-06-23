package org.codex.organ.common;

/**
 * A generic exception that wraps any other exception as a runtime exception.
 */
public class WrappingException extends RuntimeException {
    public WrappingException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return getCause().toString();
    }

    @Override
    public String getMessage() {
        return toString();
    }
}
