package com.amalitech.concurrency;

import com.amalitech.services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests thread safety of persistence logic.
 */
class ConcurrencyTest {

    @Test
    @DisplayName("Should save users safely under concurrent access")
    void testShouldSaveUsersSafelyUnderConcurrentAccess() {
        UserService service = new UserService();

        IntStream.range(0, 20).parallel().forEach(i ->
                service.createUser(
                        "User" + i,
                        "user" + i + "@mail.com",
                        "REGULAR_USER"
                )
        );

        assertDoesNotThrow(service::saveUsers);
    }
}
