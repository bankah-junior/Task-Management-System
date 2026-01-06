package com.amalitech.utils;

import com.amalitech.utils.exceptions.InvalidInputException;

public class ValidationUtils {

    /**
     * Validates if a given range is valid (min <= max).
     * @param min The minimum value of the range.
     * @param max The maximum value of the range.
     * @return true if the range is valid, false otherwise.
     */
    public static boolean isValidRange(double min, double max) {
        return min <= max;
    }

    /**
     * Validates if a given name is valid (not null or empty).
     * @param name The name to validate.
     * @return true if the name is valid, false otherwise.
     */
    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    /**
     * Validates if a given email is in a valid format.
     * @param email The email to validate.
     * @return true if the email is valid, false otherwise.
     * @throws InvalidInputException if the email format is invalid.
     */
    public static boolean isValidEmail(String email) throws InvalidInputException {
        if (email == null || email.trim().isEmpty()) return false;

        try {
            int atIndex = email.indexOf('@');
            int dotIndex = email.lastIndexOf('.');

            return atIndex > 0 && dotIndex > atIndex + 1 && dotIndex < email.length() - 1;
        } catch (Exception e) {
            throw new InvalidInputException("Invalid email format: " + email);
        }
    }

     /**
     * Validates if a given input is a valid integer.
     * @param input The input to validate.
     * @return true if the input is a valid integer, false otherwise.
      * @throws InvalidInputException if the integer format is invalid.
     */
    public static boolean isInteger(String input) {
        if (input == null || input.trim().isEmpty()) return false;

        try {
            Integer.parseInt(input.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validates if a given input is a valid double.
     * @param input The input to validate.
     * @return true if the input is a valid double, false otherwise.
     * @throws InvalidInputException if the double format is invalid.
     */
    public static boolean isDouble(String input) throws InvalidInputException {
        if (input == null || input.trim().isEmpty()) return false;

        try {
            Double.parseDouble(input.trim());
            return true;
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid double format: " + input);
        }
    }
}

