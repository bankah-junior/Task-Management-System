package utils;

public class ValidationUtils {

    public static boolean isValidRange(double min, double max) {
        return min <= max;
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
}

