package com.spokay.customloginmod.shared;

import com.spokay.customloginmod.CustomLoginMod;
import com.spokay.customloginmod.server.ServerAuthenticationHandler;
import net.minecraft.network.FriendlyByteBuf;
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
                    CustomLoginMod.LOGGER.info("Authentication response : " + response);
                    if (response == AuthenticationResponse.DENIED) {
                        CustomLoginMod.LOGGER.info("Disconnecting player");
                        Objects.requireNonNull(ctx.get().getSender()).disconnect();
                    }

                }
        );
        ctx.get().setPacketHandled(true);
    }
}
