package com.spokay.customloginmod.client;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod.EventBusSubscriber(modid = "customloginmod", bus=Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class LoginScreenEventSubscriber {
    private static final Logger LOGGER = LogUtils.getLogger();
    @SubscribeEvent
    public static void onServerSelection(ScreenEvent.Opening event) {
        LOGGER.info("onServerSelection called");
        LOGGER.info("Current Screen: " + event.getCurrentScreen());
        LOGGER.info("Minecraft.getInstance().screen: " + Minecraft.getInstance().screen);
        LOGGER.info("new screen : " + event.getNewScreen());
        if (event.getNewScreen() instanceof JoinMultiplayerScreen && !ClientPasswordHolder.instance().isPasswordInitialized()) {
            LOGGER.info("JoinMultiplayerScreen is open");
            ChoosePasswordScreen loginScreen = new ChoosePasswordScreen(Component.literal("Enter Password"), event.getNewScreen());
            event.setNewScreen(loginScreen);
            event.setResult(ScreenEvent.Result.ALLOW);
        }
    }
}
