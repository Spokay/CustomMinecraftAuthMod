package com.spokay.customloginmod.server;

import com.spokay.customloginmod.shared.AuthenticationResponse;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.DEDICATED_SERVER)
public class ServerAuthenticationHandler {
    public static AuthenticationResponse handleAuthentication(String userId, String password) {
        if (!ServerAuthenticationHolder.instance().isUserIdInStorage(userId)) {
            ServerAuthenticationHolder.instance().registerUser(userId, password);
            return AuthenticationResponse.REGISTERED;
        }
        if (ServerAuthenticationHolder.instance().isPasswordMatchingUserIdInStorage(userId, password)){
            return AuthenticationResponse.AUTHENTICATED;
        }
        return AuthenticationResponse.DENIED;
    }
}
