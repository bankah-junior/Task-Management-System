package com.amalitech.utils;

import java.util.regex.Pattern;

/**
 * Utility class for validating user input using regular expressions.
 * <p>
 * This class centralizes all regex-based validation logic.
 * </p>
 */
public final class RegexValidator {

    private RegexValidator() {
        // Prevent instantiation
    }

    /**
     * Validates whether the input matches the provided regex pattern.
     *
     * @param input the value to validate
     * @param regex the regular expression
     * @return true if input matches the regex, false otherwise
     */
    public static boolean isValid(String input, String regex) {
        return input != null && Pattern.matches(regex, input);
    }

    /**
     * Validates an email address.
     *
     * @param email the email to validate
     * @return true if the email format is valid
     */
    public static boolean isValidEmail(String email) {
        return isValid(email, "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    /**
     * Validates a numeric input.
     *
     * @param value the string value
     * @return true if the value contains only digits
     */
    public static boolean isNumeric(String value) {
        return isValid(value, "\\d+");
    }

    /**
     * Validates a user role.
     *
     * @param role the role to validate
     * @return true if the role is either ADMIN or REGULAR_USER
     */    public static boolean isValidRole(String role) {
        return isValid(role, "^(ADMIN|REGULAR_USER)$");
    }
}

