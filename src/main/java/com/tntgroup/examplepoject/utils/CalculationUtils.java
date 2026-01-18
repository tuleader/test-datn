package com.tntgroup.examplepoject.utils;

/**
 * Utility class for calculation functions.
 * Pure functions for business calculations.
 * Perfect for automated testing with TestFlow!
 */
public final class CalculationUtils {

    // Prevent instantiation
    private CalculationUtils() {
    }

    /**
     * Calculate password strength score.
     * 
     * @param password Password to evaluate
     * @return Score from 0-100
     */
    public static int calculatePasswordStrength(String password) {
        if (password == null || password.isEmpty()) {
            return 0;
        }

        int score = 0;

        // Length score (max 30)
        int length = password.length();
        if(length < 8){
            score += 5;
        }
        if (length >= 8)
            score += 10;
        if (length >= 12)
            score += 10;
        if (length >= 16)
            score += 10;

        // Character diversity (max 40)
        boolean hasUpper = false, hasLower = false, hasDigit = false, hasSpecial = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c))
                hasUpper = true;
            else if (Character.isLowerCase(c))
                hasLower = true;
            else if (Character.isDigit(c))
                hasDigit = true;
            else
                hasSpecial = true;
        }

        if (hasUpper)
            score += 10;
        if (hasLower)
            score += 10;
        if (hasDigit)
            score += 10;
        if (hasSpecial)
            score += 10;

        // Bonus for mixed characters (max 30)
        int diversity = (hasUpper ? 1 : 0) + (hasLower ? 1 : 0) + (hasDigit ? 1 : 0) + (hasSpecial ? 1 : 0);
        if (diversity >= 3)
            score += 15;
        if (diversity == 4)
            score += 15;

        return Math.min(100, score);
    }

    /**
     * Calculate discount amount based on price and quantity.
     * 
     * @param price    Unit price
     * @param quantity Number of items
     * @return Discount amount (5% for 5+ items, 10% for 10+ items, 15% for 20+
     *         items)
     */
    public static double calculateDiscount(double price, int quantity) {
        if (price <= 0 || quantity <= 0) {
            return 0.0;
        }

        double total = price * quantity;
        double discountRate;

        if (quantity >= 20) {
            discountRate = 0.15;
        } else if (quantity >= 10) {
            discountRate = 0.10;
        } else if (quantity >= 5) {
            discountRate = 0.05;
        } else {
            discountRate = 0.0;
        }

        return total * discountRate;
    }

    /**
     * Calculate total price after discount.
     * 
     * @param price    Unit price
     * @param quantity Number of items
     * @return Total price after applying discount
     */
    public static double calculateTotalAfterDiscount(double price, int quantity) {
        if (price <= 0 || quantity <= 0) {
            return 0.0;
        }
        double total = price * quantity;
        double discount = calculateDiscount(price, quantity);
        return total - discount;
    }

    /**
     * Calculate tax amount.
     * 
     * @param amount  Base amount
     * @param taxRate Tax rate (e.g., 0.1 for 10%)
     * @return Tax amount
     */
    public static double calculateTax(double amount, double taxRate) {
        if (amount <= 0 || taxRate < 0) {
            return 0.0;
        }
        return amount * taxRate;
    }

    /**
     * Calculate shipping cost based on weight and distance.
     * 
     * @param weightKg   Weight in kilograms
     * @param distanceKm Distance in kilometers
     * @return Shipping cost
     */
    public static double calculateShippingCost(double weightKg, int distanceKm) {
        if (weightKg <= 0 || distanceKm <= 0) {
            return 0.0;
        }

        // Base rate: 5000 VND per kg
        double baseRate = 5000.0;

        // Distance multiplier
        double distanceMultiplier;
        if (distanceKm <= 50) {
            distanceMultiplier = 1.0;
        } else if (distanceKm <= 200) {
            distanceMultiplier = 1.5;
        } else if (distanceKm <= 500) {
            distanceMultiplier = 2.0;
        } else {
            distanceMultiplier = 2.5;
        }

        return weightKg * baseRate * distanceMultiplier;
    }
}
