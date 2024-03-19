package com.spokay.customloginmod.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "customloginmod", bus=Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ServerJoinEventSubscriber {
    @SubscribeEvent
    public static void onServerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        System.out.println("Player logged in");

        String playerUUID = event.getEntity().getUUID().toString();
        String password = ClientPasswordHolder.instance().getPassword();
        ClientMessageService.sendUserInfoToServer(playerUUID, password, event.getEntity().getServer());
    }
}
