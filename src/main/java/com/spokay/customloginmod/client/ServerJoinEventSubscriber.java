package com.spokay.customloginmod.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = "customloginmod", bus=Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ServerJoinEventSubscriber {
    @SubscribeEvent
    public static void onServerJoin(ClientPlayerNetworkEvent.LoggingIn event) {
        // when the player joins the server get his UUID
        String playerUUID = event.getPlayer().getUUID().toString();

        // Check that the password holder is updated
        ClientPasswordHolder.instance().updateState();
        // get the stored password from the password holder
        String password = ClientPasswordHolder.instance().getPassword();
        // send the user info to the server
        ClientMessageService.sendUserInfoToServer(playerUUID, password);
    }
}
