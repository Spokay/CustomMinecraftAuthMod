package com.spokay.customloginmod;

public class AuthenticationContextHolder {
    private static boolean isAuthenticated = false;

    public static boolean isAuthenticated() {
        return AuthenticationContextHolder.isAuthenticated;
    }

    public static void setAuthenticated(boolean isAuthenticated) {
        AuthenticationContextHolder.isAuthenticated = isAuthenticated;
    }
}
