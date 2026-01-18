package com.tntgroup.examplepoject.utils;

import java.util.regex.Pattern;

/**
 * Utility class for validation functions.
 * All methods are pure functions - no side effects, no dependencies.
 * Perfect for automated testing with TestFlow!
 */
public final class ValidationUtils {

    // Prevent instantiation
    private ValidationUtils() {
    }

    // Email regex pattern
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    // Vietnamese phone pattern: starts with 0, 10-11 digits
    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^0[3-9][0-9]{8,9}$");

    /**
     * Validate email format.
     * 
     * @param email Email to validate
     * @return true if valid email format, false otherwise
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    /**
     * Validate password strength.
     * Requirements: min 8 chars, at least 1 uppercase, 1 lowercase, 1 digit.
     * 
     * @param password Password to validate
     * @return true if password meets requirements
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c))
                hasUppercase = true;
            if (Character.isLowerCase(c))
                hasLowercase = true;
            if (Character.isDigit(c))
                hasDigit = true;
        }

        return hasUppercase && hasLowercase && hasDigit;
    }

    /**
     * Validate username format.
     * Requirements: 3-20 chars, alphanumeric and underscore only.
     * 
     * @param username Username to validate
     * @return true if valid username
     */
    public static boolean isValidUsername(String username) {
        if (username == null || username.length() < 3 || username.length() > 20) {
            return false;
        }

        for (char c : username.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && c != '_') {
                return false;
            }
        }
        return true;
    }

    /**
     * Validate Vietnamese phone number format.
     * 
     * @param phone Phone number to validate
     * @return true if valid Vietnamese phone format
     */
    public static boolean isValidPhoneNumber(String phone) {
        if (phone == null || phone.isBlank()) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone.trim()).matches();
    }

    /**
     * Validate age range.
     * 
     * @param age Age to validate
     * @return true if age is between 1 and 150
     */
    public static boolean isValidAge(int age) {
        return age >= 1 && age <= 150;
    }

    /**
     * Validate credit card number using Luhn algorithm.
     * 
     * @param cardNumber Credit card number (digits only)
     * @return true if valid according to Luhn algorithm
     */
    public static boolean isValidCreditCard(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 13 || cardNumber.length() > 19) {
            return false;
        }

        // Check all digits
        for (char c : cardNumber.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        // Luhn algorithm
        int sum = 0;
        boolean alternate = false;

        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = cardNumber.charAt(i) - '0';

            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }

            sum += n;
            alternate = !alternate;
        }

        return sum % 10 == 0;
    }
}
