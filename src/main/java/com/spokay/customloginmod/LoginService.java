package com.spokay.customloginmod;

import com.spokay.customloginmod.utils.PasswordEncoder;

public class LoginService {
    public static boolean login(String password) {
        return password.equals(PasswordEncoder.hashPassword("abcde"));
    }
}
