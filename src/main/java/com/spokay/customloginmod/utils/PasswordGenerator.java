package com.spokay.customloginmod.utils;

import java.util.UUID;

public class PasswordGenerator {
    public static String generateRandomPassword() {
        return UUID.randomUUID().toString();
    }
}
