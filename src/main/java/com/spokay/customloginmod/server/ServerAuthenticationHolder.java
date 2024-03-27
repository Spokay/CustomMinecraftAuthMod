package com.spokay.customloginmod.server;

import com.spokay.customloginmod.CustomLoginMod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import oshi.util.tuples.Pair;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.DEDICATED_SERVER)
public class ServerAuthenticationHolder {
    private static ServerAuthenticationHolder INSTANCE;

    private static final Path passwordFilePath = Paths.get(".", "/.authenticationstorage");

    private final List<Pair<String, String>> authenticationPairs = new ArrayList<>();

    private String fileContent = "";

    public static ServerAuthenticationHolder instance() {
        if (INSTANCE == null) {
            INSTANCE = new ServerAuthenticationHolder();
        }
        return INSTANCE;
    }

    private ServerAuthenticationHolder() {
        if (Files.exists(passwordFilePath)) {
            // Fill authentication pairs from file
            CustomLoginMod.LOGGER.info("Filling Pairs");
            fillAuthenticationPairs();
        }else{
            CustomLoginMod.LOGGER.info("File does not exist");
        }
    }

    public boolean isUserIdInStorage(String userId) {
        CustomLoginMod.LOGGER.info("Checking if user id is in storage");
        return authenticationPairs.stream().anyMatch(pair -> pair.getA().equals(userId));
    }

    public boolean isPasswordMatchingUserIdInStorage(String userId, String password) {
        return authenticationPairs.stream().anyMatch(pair -> pair.getA().equals(userId) && pair.getB().equals(password));
    }

    private void readAuthenticationStorage() {
        try {
            fileContent = Files.readString(passwordFilePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            CustomLoginMod.LOGGER.error("Failed to read authentication storage file", e);
        }
    }

    private void fillAuthenticationPairs() {
        readAuthenticationStorage();
        String[] lines = fileContent.split("\n");
        CustomLoginMod.LOGGER.info("Lines: " + lines.length);
        for (String line : lines) {
            String[] parts = line.split(";");
            authenticationPairs.add(new Pair<>(parts[0], parts[1]));
        }
    }

    private void saveFileWithNewAuthentication(Pair<String, String> newAuthentication) {
        String authLine = newAuthentication.getA() + ";" + newAuthentication.getB()+"\n";
        try {
            Files.writeString(passwordFilePath, authLine, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            CustomLoginMod.LOGGER.error("Failed to write authentication storage file", e);
        }
    }

    void registerUser(String userId, String password) {
        saveFileWithNewAuthentication(new Pair<>(userId, password));
    }
}

