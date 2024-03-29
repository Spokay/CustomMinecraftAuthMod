package com.spokay.customloginmod.server;

import com.spokay.customloginmod.CustomLoginMod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import oshi.util.tuples.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@OnlyIn(Dist.DEDICATED_SERVER)
public class ServerAuthenticationHolder {
    private static ServerAuthenticationHolder INSTANCE;

    public static final Path passwordFilePath = Paths.get(".", "/.authenticationstorage");

    public final List<Pair<String, String>> authenticationPairs = new ArrayList<>();

    public synchronized static ServerAuthenticationHolder instance() {
        if (INSTANCE == null) {
            INSTANCE = new ServerAuthenticationHolder();
        }
        return INSTANCE;
    }

    private ServerAuthenticationHolder() {
        if (Files.exists(passwordFilePath)) {
            fillAuthenticationPairs();
        }else{
            try {
                Files.createFile(passwordFilePath);
            } catch (IOException e) {
                CustomLoginMod.LOGGER.error("Failed to create authentication storage file", e);
            }
        }
    }

    public boolean isUserIdInStorage(String userId) {
        return authenticationPairs.stream().anyMatch(pair -> pair.getA().equals(userId));
    }

    public boolean isPasswordMatchingUserIdInStorage(String userId, String password) {
        return authenticationPairs.stream().anyMatch(pair -> pair.getA().equals(userId) && pair.getB().equals(password));
    }

    private synchronized String readAuthenticationStorage() {
        try {
            return Files.readString(passwordFilePath);
        } catch (IOException e) {
            CustomLoginMod.LOGGER.error("Failed to read authentication storage file", e);
        }
        return null;
    }

    private void fillAuthenticationPairs() {
        String[] lines = Objects.requireNonNull(readAuthenticationStorage()).split("\n");
        for (String line : lines) {
            String[] parts = line.split(";");
            if (parts.length == 2){
                authenticationPairs.add(new Pair<>(parts[0], parts[1]));
            }
        }
    }

    private void saveFileWithNewAuthentication(Pair<String, String> newAuthentication) {
        String authLine = newAuthentication.getA() + ";" + newAuthentication.getB() + "\n";
        try {
            Files.writeString(passwordFilePath, authLine, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            CustomLoginMod.LOGGER.error("Failed to write authentication storage file", e);
        }
    }

    public void registerUser(String userId, String password) {
        saveFileWithNewAuthentication(new Pair<>(userId, password));
        authenticationPairs.add(new Pair<>(userId, password));
    }
}

