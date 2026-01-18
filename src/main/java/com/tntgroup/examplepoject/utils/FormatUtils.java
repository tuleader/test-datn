package com.tntgroup.examplepoject.utils;

/**
 * Utility class for string formatting and transformation.
 * Pure functions for text manipulation.
 * Perfect for automated testing with TestFlow!
 */
public final class FormatUtils {

    // Prevent instantiation
    private FormatUtils() {
    }

    /**
     * Format phone number to standard format.
     * 
     * @param phone Raw phone number
     * @return Formatted phone (e.g., "0912-345-678")
     */
    public static String formatPhoneNumber(String phone) {
        if (phone == null || phone.isBlank()) {
            return "";
        }

        // Remove all non-digits
        String digits = phone.replaceAll("\\D", "");

        if (digits.length() < 10) {
            return digits;
        }

        // Format: 0xxx-xxx-xxx or 0xxx-xxx-xxxx
        if (digits.length() == 10) {
            return digits.substring(0, 4) + "-" + digits.substring(4, 7) + "-" + digits.substring(7);
        } else if (digits.length() == 11) {
            return digits.substring(0, 4) + "-" + digits.substring(4, 7) + "-" + digits.substring(7);
        }

        return digits;
    }

    /**
     * Mask credit card number for display.
     * 
     * @param cardNumber Full card number
     * @return Masked card (e.g., "**** **** **** 1234")
     */
    public static String maskCreditCard(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return "****";
        }

        String digits = cardNumber.replaceAll("\\D", "");
        if (digits.length() < 4) {
            return "****";
        }

        String lastFour = digits.substring(digits.length() - 4);
        return "**** **** **** " + lastFour;
    }

    /**
     * Mask email address for privacy.
     * 
     * @param email Full email address
     * @return Masked email (e.g., "t***r@example.com")
     */
    public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) {
            return "***";
        }

        int atIndex = email.indexOf("@");
        String localPart = email.substring(0, atIndex);
        String domain = email.substring(atIndex);

        if (localPart.length() <= 2) {
            return localPart.charAt(0) + "***" + domain;
        }

        return localPart.charAt(0) + "***" + localPart.charAt(localPart.length() - 1) + domain;
    }

    /**
     * Convert string to slug format.
     * 
     * @param text Input text
     * @return Slug (e.g., "Hello World" -> "hello-world")
     */
    public static String toSlug(String text) {
        if (text == null || text.isBlank()) {
            return "";
        }

        return text.toLowerCase()
                .trim()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-");
    }

    /**
     * Capitalize first letter of each word.
     * 
     * @param text Input text
     * @return Title case text (e.g., "hello world" -> "Hello World")
     */
    public static String toTitleCase(String text) {
        if (text == null || text.isBlank()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = true;

        for (char c : text.toCharArray()) {
            if (Character.isWhitespace(c)) {
                capitalizeNext = true;
                result.append(c);
            } else if (capitalizeNext) {
                result.append(Character.toUpperCase(c));
                capitalizeNext = false;
            } else {
                result.append(Character.toLowerCase(c));
            }
        }

        return result.toString();
    }

    /**
     * Truncate text with ellipsis.
     * 
     * @param text      Input text
     * @param maxLength Maximum length
     * @return Truncated text with "..." if exceeded
     */
    public static String truncate(String text, int maxLength) {
        if (text == null || maxLength <= 0) {
            return "";
        }

        if (text.length() <= maxLength) {
            return text;
        }

        if (maxLength <= 3) {
            return text.substring(0, maxLength);
        }

        return text.substring(0, maxLength - 3) + "...";
    }
}
