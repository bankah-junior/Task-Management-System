package com.amalitech.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegexValidatorTest {
    @Test
    void testShouldValidateEmailsCorrectly() {
        assertTrue(RegexValidator.isValidEmail("test@mail.com"));
        assertFalse(RegexValidator.isValidEmail("invalid-email"));
    }

    @Test
    void testShouldValidateRolesCorrectly() {
        assertTrue(RegexValidator.isValidRole("ADMIN"));
        assertTrue(RegexValidator.isValidRole("REGULAR_USER"));
        assertFalse(RegexValidator.isValidRole("GUEST"));
    }

    @Test
    void testShouldValidateNumericValuesCorrectly() {
        assertTrue(RegexValidator.isNumeric("12345"));
        assertFalse(RegexValidator.isNumeric("123a45"));
    }
}