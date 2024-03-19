package com.spokay.customloginmod;

import com.spokay.customloginmod.client.LoginPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class CustomLoginPacketHandler {
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
                LoginPacket.class,
                CustomLoginPacketHandler::encode,
                CustomLoginPacketHandler::decode,
                CustomLoginPacketHandler::handle
        );
    }

    private static void encode(LoginPacket msg, FriendlyByteBuf buffer) {
        buffer.writeUtf(msg.getUserId());
        buffer.writeUtf(msg.getPassword());
    }

    private static LoginPacket decode(FriendlyByteBuf buffer) {
        String userId = buffer.readUtf();
        String password = buffer.readUtf();
        return new LoginPacket(userId, password);
    }

    private static void handle(LoginPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (LoginService.login(msg.getPassword())) {
                INSTANCE.reply("login_success", ctx.get());
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
