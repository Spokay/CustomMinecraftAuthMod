package com.spokay.customloginmod.utils;

import java.util.UUID;

public class PasswordGenerator {
    public static String generateRandomPassword() {
        // Generate a random password in the form of a UUID
        return UUID.randomUUID().toString();
    }
}
