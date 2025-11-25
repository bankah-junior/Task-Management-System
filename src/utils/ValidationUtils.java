package utils;

public class ValidationUtils {

    public static boolean isValidNumber(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidRange(double min, double max) {
        return min <= max;
    }

    // new: validate status input
    public static boolean isValidStatus(String input) {
        try {
            TaskStatus.valueOf(input.toUpperCase());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        return email != null &&
                email.contains("@") &&
                email.contains(".") &&
                email.length() >= 5;
    }

    public static void requireNonEmpty(String input, String fieldName) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
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
            return false;
        }
    }

    public static boolean isValidTaskName(String name) {
        return name != null && !name.trim().isEmpty();
    }
}

