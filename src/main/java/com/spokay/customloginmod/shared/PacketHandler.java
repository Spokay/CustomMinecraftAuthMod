package com.spokay.customloginmod.shared;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class PacketHandler {
    private static int channelIdCounter = 0;
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
      new ResourceLocation("customloginmod", "main"),
      () -> PROTOCOL_VERSION,
      PROTOCOL_VERSION::equals,
      PROTOCOL_VERSION::equals
    );

    public static void registerPackets() {
        INSTANCE.registerMessage(
                channelIdCounter++,
                JoinEventAuthenticationPacket.class,
                JoinEventAuthenticationPacket::encode,
                JoinEventAuthenticationPacket::decode,
                JoinEventAuthenticationPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );
        INSTANCE.registerMessage(
                channelIdCounter++,
                AuthenticationResponsePacket.class,
                AuthenticationResponsePacket::encode,
                AuthenticationResponsePacket::decode,
                AuthenticationResponsePacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );
    }
}
