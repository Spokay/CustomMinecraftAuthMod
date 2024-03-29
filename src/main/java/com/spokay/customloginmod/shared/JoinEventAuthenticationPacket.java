package com.spokay.customloginmod.shared;

import com.spokay.customloginmod.CustomLoginMod;
import com.spokay.customloginmod.config.CustomLoginModConfig;
import com.spokay.customloginmod.server.ServerAuthenticationHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.server.commands.KickCommand;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public record JoinEventAuthenticationPacket(String userId, String password) implements Packet {

    public static void encode(JoinEventAuthenticationPacket msg, FriendlyByteBuf buffer) {
        buffer.writeUtf(msg.userId());
        buffer.writeUtf(msg.password());
    }

    public static JoinEventAuthenticationPacket decode(FriendlyByteBuf buffer) {
        String userId = buffer.readUtf();
        String password = buffer.readUtf();
        return new JoinEventAuthenticationPacket(userId, password);
    }

    public static void handle(JoinEventAuthenticationPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
                    AuthenticationResponse response = ServerAuthenticationHandler.handleAuthentication(msg.userId(), msg.password());
                    // Kick the player if he is using a fake account
                    if (response == AuthenticationResponse.DENIED) {
                        ServerPlayer player = ctx.get().getSender();
                        if (player != null) {
                            player.connection.disconnect(
                                    Component.literal(CustomLoginModConfig.DENIED_ACCESS_MESSAGE)
                            );
                        }
                    }

                }
        );
        // Mark the packet as handled
        ctx.get().setPacketHandled(true);
    }
}
