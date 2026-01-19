package com.tntgroup.testdatn.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tntgroup.testdatn.utils.KeyGeneratorUtils;

/**
 * REST Controller for key generation operations.
 * Provides endpoints to generate various types of random keys.
 */
@RestController
@RequestMapping("/api/keys")
public class KeyGeneratorController {

    /**
     * Generate an alphanumeric key.
     *
     * @param length Length of the key (default: 32)
     * @return Generated key
     */
    @GetMapping("/alphanumeric")
    public ResponseEntity<Map<String, Object>> generateAlphanumericKey(
            @RequestParam(defaultValue = "32") int length) {
        try {
            String key = KeyGeneratorUtils.generateAlphanumericKey(length);
            return ResponseEntity.ok(buildResponse("alphanumeric", key, length));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(buildErrorResponse(e.getMessage()));
        }
    }

    /**
     * Generate a hexadecimal key.
     *
     * @param length Length of the key (default: 64)
     * @return Generated key
     */
    @GetMapping("/hex")
    public ResponseEntity<Map<String, Object>> generateHexKey(
            @RequestParam(defaultValue = "64") int length) {
        try {
            String key = KeyGeneratorUtils.generateHexKey(length);
            return ResponseEntity.ok(buildResponse("hex", key, length));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(buildErrorResponse(e.getMessage()));
        }
    }

    /**
     * Generate a Base64-encoded key.
     *
     * @param bytes Number of random bytes (default: 32)
     * @return Generated key
     */
    @GetMapping("/base64")
    public ResponseEntity<Map<String, Object>> generateBase64Key(
            @RequestParam(defaultValue = "32") int bytes) {
        try {
            String key = KeyGeneratorUtils.generateBase64Key(bytes);
            return ResponseEntity.ok(buildResponse("base64", key, bytes));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(buildErrorResponse(e.getMessage()));
        }
    }

    /**
     * Generate a URL-safe key.
     *
     * @param bytes Number of random bytes (default: 32)
     * @return Generated key
     */
    @GetMapping("/url-safe")
    public ResponseEntity<Map<String, Object>> generateUrlSafeKey(
            @RequestParam(defaultValue = "32") int bytes) {
        try {
            String key = KeyGeneratorUtils.generateUrlSafeKey(bytes);
            return ResponseEntity.ok(buildResponse("url-safe", key, bytes));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(buildErrorResponse(e.getMessage()));
        }
    }

    /**
     * Generate a UUID-based key.
     *
     * @return Generated UUID key
     */
    @GetMapping("/uuid")
    public ResponseEntity<Map<String, Object>> generateUuidKey() {
        String key = KeyGeneratorUtils.generateUuidKey();
        return ResponseEntity.ok(buildResponse("uuid", key, 32));
    }

    /**
     * Generate an API key with prefix.
     *
     * @param prefix Prefix for the key (default: "api")
     * @param length Length of random part (default: 32)
     * @return Generated API key
     */
    @GetMapping("/api-key")
    public ResponseEntity<Map<String, Object>> generateApiKey(
            @RequestParam(defaultValue = "api") String prefix,
            @RequestParam(defaultValue = "32") int length) {
        try {
            String key = KeyGeneratorUtils.generateApiKey(prefix, length);
            return ResponseEntity.ok(buildResponse("api-key", key, length));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(buildErrorResponse(e.getMessage()));
        }
    }

    /**
     * Generate a webhook secret.
     *
     * @return Generated webhook secret
     */
    @GetMapping("/webhook-secret")
    public ResponseEntity<Map<String, Object>> generateWebhookSecret() {
        String key = KeyGeneratorUtils.generateWebhookSecret();
        return ResponseEntity.ok(buildResponse("webhook-secret", key, 64));
    }

    /**
     * Generate a session token.
     *
     * @return Generated session token
     */
    @GetMapping("/session-token")
    public ResponseEntity<Map<String, Object>> generateSessionToken() {
        String key = KeyGeneratorUtils.generateSessionToken();
        return ResponseEntity.ok(buildResponse("session-token", key, 32));
    }

    /**
     * Generate multiple keys at once.
     *
     * @param type   Key type (alphanumeric, hex, base64, url-safe, uuid)
     * @param length Length/bytes for the key
     * @param count  Number of keys to generate (max 100)
     * @return List of generated keys
     */
    @GetMapping("/batch")
    public ResponseEntity<Map<String, Object>> generateBatchKeys(
            @RequestParam(defaultValue = "alphanumeric") String type,
            @RequestParam(defaultValue = "32") int length,
            @RequestParam(defaultValue = "5") int count) {

        if (count <= 0 || count > 100) {
            return ResponseEntity.badRequest()
                    .body(buildErrorResponse("Count must be between 1 and 100"));
        }

        try {
            String[] keys = new String[count];
            for (int i = 0; i < count; i++) {
                keys[i] = switch (type.toLowerCase()) {
                    case "hex" -> KeyGeneratorUtils.generateHexKey(length);
                    case "base64" -> KeyGeneratorUtils.generateBase64Key(length);
                    case "url-safe" -> KeyGeneratorUtils.generateUrlSafeKey(length);
                    case "uuid" -> KeyGeneratorUtils.generateUuidKey();
                    default -> KeyGeneratorUtils.generateAlphanumericKey(length);
                };
            }

            Map<String, Object> response = new HashMap<>();
            response.put("type", type);
            response.put("count", count);
            response.put("keys", keys);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(buildErrorResponse(e.getMessage()));
        }
    }

    private Map<String, Object> buildResponse(String type, String key, int length) {
        Map<String, Object> response = new HashMap<>();
        response.put("type", type);
        response.put("key", key);
        response.put("length", length);
        return response;
    }

    private Map<String, Object> buildErrorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", message);
        return response;
    }
}
