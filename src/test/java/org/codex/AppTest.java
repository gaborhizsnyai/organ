package org.codex;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void testCreate() {
        assertDoesNotThrow(App::new);
    }
}
