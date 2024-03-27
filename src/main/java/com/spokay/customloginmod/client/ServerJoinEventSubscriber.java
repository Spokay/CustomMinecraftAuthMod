package com.spokay.customloginmod.client;

import com.spokay.customloginmod.CustomLoginMod;
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
        CustomLoginMod.LOGGER.debug("Player logged in");
        CustomLoginMod.LOGGER.info("Sending user info to server");

        String playerUUID = event.getPlayer().getUUID().toString();
        String password = ClientPasswordHolder.instance().getPassword();
        ClientMessageService.sendUserInfoToServer(playerUUID, password);
    }
}
