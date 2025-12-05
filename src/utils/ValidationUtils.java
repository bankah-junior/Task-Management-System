package utils;

import utils.exceptions.InvalidInputException;

public class ValidationUtils {

    public static boolean isValidRange(double min, double max) {
        return min <= max;
    }

    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) return false;

        try {
            int atIndex = email.indexOf('@');
            int dotIndex = email.lastIndexOf('.');

            return atIndex > 0 && dotIndex > atIndex + 1 && dotIndex < email.length() - 1;
        } catch (Exception e) {
            throw new InvalidInputException("Invalid email format: " + email);
        }
    }

    public static boolean isInteger(String input) {
        if (input == null || input.trim().isEmpty()) return false;

        try {
            Integer.parseInt(input.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String input) {
        if (input == null || input.trim().isEmpty()) return false;

        try {
            Double.parseDouble(input.trim());
            return true;
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid double format: " + input);
        }
    }
}

