package com.spokay.customloginmod.client;

public class LoginPacket {
    private final String userId;
    private final String password;

    public LoginPacket(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}
