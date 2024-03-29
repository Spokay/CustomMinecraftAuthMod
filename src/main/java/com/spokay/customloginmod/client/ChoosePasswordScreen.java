package com.spokay.customloginmod.client;

import com.spokay.customloginmod.utils.PasswordGenerator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ExtendedButton;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ChoosePasswordScreen extends Screen {
    int inputFieldWidth = 200;
    int inputFieldHeight = 20;
    int showHideButtonWidth = 50;
    ExtendedButton randomPasswordButton;
    Screen nextScreen;
    EditBox editBox;
    PlainTextButton showHideButton;
    ExtendedButton submitButton;

    boolean isPasswordHidden = true;

    protected ChoosePasswordScreen(Component title, Screen nextScreen) {
        super(title);
        this.nextScreen = nextScreen;
    }

    protected Font getFont(){
        return Minecraft.getInstance().font;
    }

    protected PlainTextButton createShowHideButton(){
        int buttonHeight = 20;
        int x = (this.width - inputFieldWidth) / 2 + inputFieldWidth + 10;
        int y = (this.height - buttonHeight + (inputFieldHeight / 2)) / 2;

        return new PlainTextButton(
            x,
            y,
            showHideButtonWidth,
            buttonHeight,
            Component.literal("Show/Hide"),
            (button) -> {
                // Toggle the isPasswordHidden flag and set the focus to the edit box
                isPasswordHidden = !isPasswordHidden;
                this.setFocused(this.editBox);
            },
                getFont()
        );
    }

    ExtendedButton createSubmitButton(){
        int buttonHeight = 20;
        int x = (this.width - inputFieldWidth) / 2;
        int y = (this.height - buttonHeight + (inputFieldHeight / 2)) / 2 + 30;
        return new ExtendedButton(
            x,
            y,
            inputFieldWidth,
            buttonHeight,
            Component.literal("Submit"),
            (button) -> {
                // If the user has entered a password, initialize the password
                if (!editBox.getValue().isEmpty()){
                    ClientPasswordHolder.instance().initializePassword(editBox.getValue());
                }
                // If the password is initialized, move to the next screen
                if (ClientPasswordHolder.instance().isPasswordInitialized()) {
                    Minecraft.getInstance().setScreen(nextScreen);
                }
            }
        );
    }

    ExtendedButton createRandomPasswordButton(){
        int buttonHeight = 20;
        int x = (this.width - inputFieldWidth) / 2;
        int y = (this.height - buttonHeight + (inputFieldHeight / 2)) / 2 + 60;
        return new ExtendedButton(
                x,
                y,
                inputFieldWidth,
                buttonHeight,
                Component.literal("Generate Random Password"),
                (button) -> {
                    // Generate a random password and set it in the edit box
                    var randomPassword = PasswordGenerator.generateRandomPassword();
                    editBox.setValue(randomPassword);
                }
        );
    }

    EditBox getEditBox() {
        int boxHeight = 20;
        int x = (this.width - inputFieldWidth) / 2;
        int y = (this.height - boxHeight) / 2;
        EditBox editBox = new EditBox(
                getFont(),
                x,
                y,
                inputFieldWidth,
                boxHeight,
                Component.literal("Enter a Password for you account")
        ) {
            // Override the render method to hide the password according to the isPasswordHidden flag
            @Override
            public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
                String originalValue = getValue();
                if (isPasswordHidden) {
                    setValue("*".repeat(originalValue.length()));
                }
                super.render(graphics, mouseX, mouseY, partialTick);
                if (isPasswordHidden) {
                    setValue(originalValue);
                }
            }
        };
        editBox.setCanLoseFocus(false);
        return editBox;
    }

    protected void init() {
        super.init();

        // create the components and set them as renderable widgets
        this.editBox = this.getEditBox();
        this.showHideButton = this.createShowHideButton();
        this.submitButton = this.createSubmitButton();
        this.randomPasswordButton = this.createRandomPasswordButton();
        this.addRenderableWidget(this.editBox);
        this.addRenderableWidget(this.showHideButton);
        this.addRenderableWidget(this.submitButton);
        this.addRenderableWidget(this.randomPasswordButton);
    }

    @Override
    public void tick() {
        super.tick();
        // Tick the edit box to update the cursor
        this.editBox.tick();
    }

    @Override
    public void render(@NotNull GuiGraphics graphics,  int mouseX, int mouseY, float partialTick) {
        super.renderDirtBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        graphics.drawCenteredString(
            getFont(),
            title.getString(),
            this.width / 2,
            this.height / 2 - 40,
            16777215
        );
        this.editBox.render(graphics, mouseX, mouseY, partialTick);
        this.setInitialFocus(this.editBox);
        this.showHideButton.render(graphics, mouseX, mouseY, partialTick);
        this.submitButton.render(graphics, mouseX, mouseY, partialTick);
        this.randomPasswordButton.render(graphics, mouseX, mouseY, partialTick);
    }


}
