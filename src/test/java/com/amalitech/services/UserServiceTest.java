package com.amalitech.services;

import com.amalitech.models.User;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link UserService}.
 */
@DisplayName("UserService Tests")
class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    /**
     * Helper method to create an admin user.
     */
    private User createAdmin() {
        return userService.createUser(
                "Alice",
                "alice@example.com",
                "ADMIN"
        );
    }

    /**
     * Helper method to create a regular user.
     */
    private User createRegular() {
        return userService.createUser(
                "Bob",
                "bob@example.com",
                "REGULAR_USER"
        );
    }

    @Nested
    @DisplayName("Create User")
    class CreateUserTests {

        @Test
        @DisplayName("Should create an ADMIN user")
        void testShouldCreateAdminUser() {
            // Act
            User admin = createAdmin();

            // Assert
            assertAll(
                    () -> assertEquals("Alice", admin.getName()),
                    () -> assertEquals("ADMIN", admin.getRole())
            );
        }

        @Test
        @DisplayName("Should create a REGULAR user")
        void testShouldCreateRegularUser() {
            // Act
            User regular = createRegular();

            // Assert
            assertAll(
                    () -> assertEquals("Bob", regular.getName()),
                    () -> assertEquals("REGULAR_USER", regular.getRole())
            );
        }
    }

    @Nested
    @DisplayName("Delete User")
    class DeleteUserTests {

        @Test
        @DisplayName("Should delete user and prevent double deletion")
        void testShouldDeleteUser() {
            // Arrange
            User admin = createAdmin();
            createRegular();

            // Act & Assert
            assertAll(
                    () -> assertTrue(userService.deleteUser(admin.getId())),
                    () -> assertFalse(userService.deleteUser(admin.getId()))
            );
        }
    }

    @Nested
    @DisplayName("Login User")
    class LoginUserTests {

        @Test
        @DisplayName("Should login valid users and return null for invalid ID")
        void testShouldLoginUsersCorrectly() {
            // Arrange
            User admin = createAdmin();
            User regular = createRegular();

            // Assert
            assertAll(
                    () -> assertEquals(admin, userService.login(admin.getId())),
                    () -> assertEquals(regular, userService.login(regular.getId())),
                    () -> assertNull(userService.login(999))
            );
        }
    }

    @Nested
    @DisplayName("Get User By ID")
    class GetUserByIdTests {

        @Test
        @DisplayName("Should return correct user by ID")
        void testShouldGetUserById() {
            // Arrange
            User admin = createAdmin();
            User regular = createRegular();

            // Assert
            assertAll(
                    () -> assertEquals(admin, userService.getUserById(admin.getId())),
                    () -> assertEquals(regular, userService.getUserById(regular.getId())),
                    () -> assertNull(userService.getUserById(999))
            );
        }
    }

    @Nested
    @DisplayName("Update User")
    class UpdateUserTests {

        @Test
        @DisplayName("Should update user details successfully")
        void testShouldUpdateUser() {
            // Arrange
            User admin = createAdmin();

            // Act
            userService.updateUser(
                    admin.getId(),
                    "UpdatedAlice",
                    "updated@example.com",
                    "REGULAR_USER"
            );

            // Assert
            assertAll(
                    () -> assertEquals("UpdatedAlice", admin.getName()),
                    () -> assertEquals("updated@example.com", admin.getEmail()),
                    () -> assertEquals("REGULAR_USER", admin.getRole())
            );
        }
    }
}
