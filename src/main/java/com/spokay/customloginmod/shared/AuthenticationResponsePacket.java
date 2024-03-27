package com.spokay.customloginmod.shared;

import com.spokay.customloginmod.client.ClientPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record AuthenticationResponsePacket(String authenticationResponse, String userId) implements Packet{
    public static void encode(AuthenticationResponsePacket msg, FriendlyByteBuf buffer) {
        buffer.writeUtf(msg.authenticationResponse());
        buffer.writeUtf(msg.userId());
    }

    public static AuthenticationResponsePacket decode(FriendlyByteBuf buffer) {
        return new AuthenticationResponsePacket(
                buffer.readUtf(),
                buffer.readUtf()
        );
    }

    public static void handle(AuthenticationResponsePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
                // Make sure it's only executed on the physical client
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handlePacket(msg, ctx))
        );
        ctx.get().setPacketHandled(true);
    }
}
