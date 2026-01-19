package com.tntgroup.testdatn.utils;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

/**
 * Utility class for generating random keys and tokens.
 * Uses cryptographically secure random number generator.
 */
public final class KeyGeneratorUtils {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String HEX_CHARS = "0123456789abcdef";

    // Prevent instantiation
    private KeyGeneratorUtils() {
    }

    /**
     * Generate a random alphanumeric key of specified length.
     *
     * @param length Length of the key (must be positive)
     * @return Random alphanumeric string
     * @throws IllegalArgumentException if length is not positive
     */
    public static String generateAlphanumericKey(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be positive");
        }

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = SECURE_RANDOM.nextInt(ALPHANUMERIC.length());
            sb.append(ALPHANUMERIC.charAt(index));
        }
        return sb.toString();
    }

    /**
     * Generate a random hexadecimal key of specified length.
     *
     * @param length Length of the key (must be positive)
     * @return Random hexadecimal string
     * @throws IllegalArgumentException if length is not positive
     */
    public static String generateHexKey(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be positive");
        }

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = SECURE_RANDOM.nextInt(HEX_CHARS.length());
            sb.append(HEX_CHARS.charAt(index));
        }
        return sb.toString();
    }

    /**
     * Generate a Base64-encoded random key.
     *
     * @param byteLength Number of random bytes to generate
     * @return Base64-encoded string
     * @throws IllegalArgumentException if byteLength is not positive
     */
    public static String generateBase64Key(int byteLength) {
        if (byteLength <= 0) {
            throw new IllegalArgumentException("Byte length must be positive");
        }

        byte[] bytes = new byte[byteLength];
        SECURE_RANDOM.nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Generate a URL-safe Base64-encoded random key.
     *
     * @param byteLength Number of random bytes to generate
     * @return URL-safe Base64-encoded string
     * @throws IllegalArgumentException if byteLength is not positive
     */
    public static String generateUrlSafeKey(int byteLength) {
        if (byteLength <= 0) {
            throw new IllegalArgumentException("Byte length must be positive");
        }

        byte[] bytes = new byte[byteLength];
        SECURE_RANDOM.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    /**
     * Generate a UUID-based key.
     *
     * @return Random UUID string (without hyphens)
     */
    public static String generateUuidKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * Generate an API key with a prefix.
     * Format: prefix_randomKey (e.g., "api_x8k2m9p4...")
     *
     * @param prefix    Prefix for the API key
     * @param keyLength Length of the random part
     * @return Prefixed API key
     * @throws IllegalArgumentException if prefix is null/empty or keyLength is not
     *                                  positive
     */
    public static String generateApiKey(String prefix, int keyLength) {
        if (prefix == null || prefix.isBlank()) {
            throw new IllegalArgumentException("Prefix cannot be null or empty");
        }
        if (keyLength <= 0) {
            throw new IllegalArgumentException("Key length must be positive");
        }

        return prefix + "_" + generateAlphanumericKey(keyLength);
    }

    /**
     * Generate a webhook secret key.
     * Creates a 256-bit (32 bytes) secure key encoded in hexadecimal.
     *
     * @return 64-character hexadecimal string
     */
    public static String generateWebhookSecret() {
        return generateHexKey(64);
    }

    /**
     * Generate a session token.
     * Creates a URL-safe token suitable for session management.
     *
     * @return URL-safe session token
     */
    public static String generateSessionToken() {
        return generateUrlSafeKey(32);
    }
}
