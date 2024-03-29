package com.spokay.customloginmod.client;

import com.spokay.customloginmod.CustomLoginMod;
import com.spokay.customloginmod.utils.PasswordEncoder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@OnlyIn(Dist.CLIENT)
public class ClientPasswordHolder {
    private static ClientPasswordHolder INSTANCE;
    private String password;

    private boolean passwordInitialized = false;
    public static final Path passwordFilePath = Paths.get(".", ".customloginstorage");

    public static ClientPasswordHolder instance() {
        if (INSTANCE == null) {
            INSTANCE = new ClientPasswordHolder();
        }
        return INSTANCE;
    }

    private ClientPasswordHolder() {
        if (Files.exists(passwordFilePath)) {
            readPasswordStorage();
        }else{
            try {
                Files.createFile(passwordFilePath);
            } catch (IOException e) {
                CustomLoginMod.LOGGER.error("Failed to create password file", e);
            }
        }
    }

    public boolean isPasswordInitialized() {
        return passwordInitialized && !this.password.isEmpty();
    }
    public void initializePassword(String newPassword) {
        if (!isPasswordInitialized()) {
            this.password = PasswordEncoder.hashPassword(newPassword);
            passwordInitialized = true;
            this.saveFileWithNewPassword(this.password);
        }
    }


    private void readPasswordStorage() {
        try {
            this.password = Files.readString(passwordFilePath);
            if (!this.password.isEmpty()) {
                passwordInitialized = true;
            }
        } catch (IOException e) {
            CustomLoginMod.LOGGER.error("Failed to read password file", e);
        }
    }

    private void saveFileWithNewPassword(String password) {
        try {
            Files.writeString(passwordFilePath, password, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            CustomLoginMod.LOGGER.error("Failed to write password file", e);
        }
    }

    public String getPassword() {
        return password;
    }

    public void updateState(){
        readPasswordStorage();
    }
}
