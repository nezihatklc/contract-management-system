package com.project.cms.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordHasher {

    private static final int SALT_LENGTH = 16;
    private static final String DELIMITER = ":";

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

    private static byte[] generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    private static byte[] sha256(String password, byte[] salt) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        return md.digest(password.getBytes(StandardCharsets.UTF_8));
    }
}




