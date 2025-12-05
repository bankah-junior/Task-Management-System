package com.amalitech.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Test
    void testCreateAdminUser() {
        UserService userService = new UserService();
        var admin = userService.createUser("Alice", "alice@example.com", "ADMIN");
        assertEquals("Alice", admin.getName());
        assertEquals("ADMIN", admin.getRole());
    }

     @Test
    void testCreateRegularUser() {
        UserService userService = new UserService();
        var regular = userService.createUser("Bob", "bob@example.com", "REGULAR_USER");
        assertEquals("Bob", regular.getName());
        assertEquals("REGULAR_USER", regular.getRole());
    }

     @Test
    void testDeleteUser() {
        UserService userService = new UserService();
        var admin = userService.createUser("Alice", "alice@example.com", "ADMIN");
        var regular = userService.createUser("Bob", "bob@example.com", "REGULAR_USER");
        assertTrue(userService.deleteUser(1));
        assertFalse(userService.deleteUser(1));
    }

     @Test
    void testLoginUser() {
        UserService userService = new UserService();
        var admin = userService.createUser("Alice", "alice@example.com", "ADMIN");
        var regular = userService.createUser("Bob", "bob@example.com", "REGULAR_USER");
        assertEquals(admin, userService.login(1));
        assertEquals(regular, userService.login(2));
        assertNull(userService.login(3));
    }

    @Test
    void testGetUserById() {
        UserService userService = new UserService();
        var admin = userService.createUser("Alice", "alice@example.com", "ADMIN");
        var regular = userService.createUser("Bob", "bob@example.com", "REGULAR_USER");
        assertEquals(admin, userService.getUserById(admin.getId()));
        assertEquals(regular, userService.getUserById(2));
        assertNull(userService.getUserById(3));
    }

     @Test
    void testUpdateUser() {
        UserService userService = new UserService();
        var admin = userService.createUser("Alice", "alice@example.com", "ADMIN");
        userService.updateUser(1, "UpdatedAlice", "updated@example.com", "REGULAR_USER");
        assertEquals("UpdatedAlice", admin.getName());
        assertEquals("updated@example.com", admin.getEmail());
        assertEquals("REGULAR_USER", admin.getRole());
    }

}