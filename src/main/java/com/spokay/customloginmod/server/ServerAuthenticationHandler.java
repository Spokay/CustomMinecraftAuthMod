package com.spokay.customloginmod.server;

import com.spokay.customloginmod.shared.AuthenticationResponse;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.DEDICATED_SERVER)
public class ServerAuthenticationHandler {
    public static AuthenticationResponse handleAuthentication(String userId, String password) {
        // Check if user is already registered
        if (!ServerAuthenticationHolder.instance().isUserIdInStorage(userId)){
            // if not register user and return REGISTERED authentication response
            ServerAuthenticationHolder.instance().registerUser(userId, password);
            return AuthenticationResponse.REGISTERED;
        } else if (ServerAuthenticationHolder.instance().isPasswordMatchingUserIdInStorage(userId, password)){
            // if user is registered and password matches the registered user return AUTHENTICATED authentication response
            return AuthenticationResponse.AUTHENTICATED;
        }
        // otherwise return DENIED authentication response
        return AuthenticationResponse.DENIED;
    }
}
