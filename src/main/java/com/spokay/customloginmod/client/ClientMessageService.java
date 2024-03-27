package com.spokay.customloginmod.client;

import com.spokay.customloginmod.CustomLoginMod;
import com.spokay.customloginmod.shared.JoinEventAuthenticationPacket;
import com.spokay.customloginmod.shared.PacketHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientMessageService {
    public static void sendUserInfoToServer(String userId, String password) {
        CustomLoginMod.LOGGER.info("Sending password to server");
        PacketHandler.INSTANCE.sendToServer(new JoinEventAuthenticationPacket(userId, password));
    }
}
