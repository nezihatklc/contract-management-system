package com.project.cms.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utility class providing secure password hashing and verification
 * using SHA-256 with a randomly generated salt. The hashed password
 * is stored in the format: Base64(hash) : Base64(salt).
 * @author Zeynep Sıla Şimşek
 */

public class PasswordHasher {

    private static final int SALT_LENGTH = 16;
    private static final String DELIMITER = ":";

    /**
     * Generates a salted SHA-256 hash for the given password.
     * <p>
     * A new random salt is produced for every call, and both the hash
     * and the salt are encoded using Base64. If the password is null,
     * the method returns null.
     *
     * @param password the plaintext password to hash
     * @return a combined hash string in the format "hash:salt",
     *         or null if the password is null
     */

    public static String hashPassword(String password) {

        
        if (password == null) {
            return null;
        }

        try {
            byte[] salt = generateSalt();
            byte[] hashBytes = sha256(password, salt);

            String hashStr = Base64.getEncoder().encodeToString(hashBytes);
            String saltStr = Base64.getEncoder().encodeToString(salt);

            return hashStr + DELIMITER + saltStr;

        } catch (Exception e) {
            throw new RuntimeException("Password hashing failed.", e);
        }
    }

     /**
     * Verifies whether the provided plaintext password matches
     * the previously stored salted hash.
     * <p>
     * The stored value must be in the format "hash:salt". The method
     * recomputes the hash using the extracted salt and compares it
     * to the stored hash.
     *
     * @param password the password entered by the user
     * @param stored   the stored hash string in "hash:salt" format
     * @return true if the password is correct, false otherwise
     */

    public static boolean verifyPassword(String password, String stored) {

        if (password == null || stored == null || !stored.contains(DELIMITER)) {
            return false;
        }

        try {
            String[] parts = stored.split(DELIMITER);
            if (parts.length != 2) return false;

            String storedHash = parts[0];
            byte[] salt = Base64.getDecoder().decode(parts[1]);

            byte[] enteredHash = sha256(password, salt);
            String enteredHashStr = Base64.getEncoder().encodeToString(enteredHash);

            return enteredHashStr.equals(storedHash);

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Generates a cryptographically secure random salt.
     *
     * @return a byte array containing the generated salt
     */

    private static byte[] generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    /**
     * Computes the SHA-256 hash of the password combined with the given salt.
     *
     * @param password the plaintext password
     * @param salt     the salt to apply before hashing
     * @return the resulting SHA-256 hash as a byte array
     * @throws Exception if SHA-256 algorithm is unavailable
     */

    private static byte[] sha256(String password, byte[] salt) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        return md.digest(password.getBytes(StandardCharsets.UTF_8));
    }
}




