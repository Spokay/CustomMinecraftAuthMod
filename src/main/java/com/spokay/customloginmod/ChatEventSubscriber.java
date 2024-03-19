package com.spokay.customloginmod;

import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.FORGE)
public class ChatEventSubscriber {

    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public void onChatMessage(ClientChatEvent event) {
        if (!AuthenticationContextHolder.isAuthenticated()) {
            String message = event.getMessage();
            LOGGER.debug("message send : " + event.getMessage());
            if (message.startsWith("/login")) {
                String[] parts = message.split(" ");
                String pwd = parts.length == 2 ? parts[1] : "";
                if (LoginService.login(pwd)) {
                    AuthenticationContextHolder.setAuthenticated(true);
                }
                event.setCanceled(true); // Prevent the /login command from being sent to the server
            } else {
                event.setCanceled(true); // Prevent other commands from being sent to the server
            }
        }
    }

}
