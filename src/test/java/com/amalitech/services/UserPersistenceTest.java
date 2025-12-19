package com.amalitech.services;

import com.amalitech.models.User;
import org.junit.jupiter.api.*;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests user persistence using JSON files.
 */
class UserPersistenceTest {

    private UserService userService;
    private static final Path USER_FILE =
            Path.of("src/main/resources/data/users_data.json");

    @BeforeEach
    void setUp() throws Exception {
        Files.deleteIfExists(USER_FILE);
        userService = new UserService();
    }

    @Test
    @DisplayName("Should persist and reload users correctly")
    void testShouldPersistAndReloadUsersCorrectly() {
        userService.createUser("Alice", "alice@mail.com", "ADMIN");
        userService.createUser("Bob", "bob@mail.com", "REGULAR_USER");

        userService.saveUsers();

        UserService reloaded = new UserService();
        reloaded.loadUsers();

        assertEquals(2, reloaded.getUsers().size());
        assertEquals("Alice", reloaded.getUsers().getFirst().getName());
    }
}
