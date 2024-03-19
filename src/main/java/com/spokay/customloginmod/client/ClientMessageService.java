package com.spokay.customloginmod.client;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientMessageService {
    public static void sendUserInfoToServer(String userId, String password, MinecraftServer server) {
        System.out.println("Sending password to server");
        
    }
}
