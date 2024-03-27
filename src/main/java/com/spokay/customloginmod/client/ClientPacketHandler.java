package com.spokay.customloginmod.client;

import com.spokay.customloginmod.CustomLoginMod;
import com.spokay.customloginmod.shared.AuthenticationResponsePacket;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientPacketHandler {
    public static void handlePacket(AuthenticationResponsePacket msg, Supplier<NetworkEvent.Context> ctx) {
        CustomLoginMod.LOGGER.info("Received authentication response from server: " + msg.authenticationResponse());
    }
}
