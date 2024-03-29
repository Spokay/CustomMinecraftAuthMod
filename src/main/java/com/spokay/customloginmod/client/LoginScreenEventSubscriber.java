package com.spokay.customloginmod.client;

import com.spokay.customloginmod.config.CustomLoginModConfig;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid=CustomLoginModConfig.MOD_ID, bus=Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class LoginScreenEventSubscriber {
    @SubscribeEvent
    public static void onServerSelection(ScreenEvent.Opening event) {
        // when the user is entering the multiplayer screen, check if the password is initialized
        if (event.getNewScreen() instanceof JoinMultiplayerScreen && !ClientPasswordHolder.instance().isPasswordInitialized()) {
            // if the password is not initialized, show the password screen to initialize the password
            ChoosePasswordScreen loginScreen = new ChoosePasswordScreen(Component.literal("Enter Password"), event.getNewScreen());
            // set the new screen to the password screen
            event.setNewScreen(loginScreen);
            // set the result to allow the screen to be opened
            event.setResult(ScreenEvent.Result.ALLOW);
        }
    }
}
