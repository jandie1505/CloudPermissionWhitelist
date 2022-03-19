package net.jandie1505.CloudPermissionWhitelist.misc;

import net.jandie1505.CloudPermissionWhitelist.CloudPermissionWhitelist;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {
    private static File configFile;
    private static FileConfiguration config;

    private static boolean updateCheck = false;
    private static boolean updateNotifyConsole = false;
    private static boolean updateNotifyPlayer = false;
    private static int tempJoinTime = 300;

    public static void load() {
        createCustomConfig();

        updateCheck = config.getBoolean("updateCheck.check");
        updateNotifyConsole = config.getBoolean("updateCheck.notifyConsole");
        updateNotifyPlayer = config.getBoolean("updateCheck.notifyPlayer");

        int tempJoinTimeCheck = tempJoinTime = config.getInt("tempJoinTime");
        if(tempJoinTimeCheck > 0 && tempJoinTimeCheck < 1800) {
            tempJoinTime = tempJoinTimeCheck;
        }
    }

    public static boolean getUpdateCheck() {
        return updateCheck;
    }

    public static boolean getUpdateNotifyConsole() {
        return updateNotifyConsole;
    }

    public static boolean getUpdateNotifyPlayer() {
        return updateNotifyPlayer;
    }

    public static int getTempJoinTime() {
        return tempJoinTime;
    }

    // CONFIG FILE
    public FileConfiguration getCustomConfig() {
        return config;
    }

    private static void createCustomConfig() {
        configFile = new File(CloudPermissionWhitelist.getPlugin().getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            CloudPermissionWhitelist.getPlugin().saveResource("config.yml", false);
        }

        config = new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            CloudPermissionWhitelist.getPlugin().getLogger().warning("Configuration Error, using default values");
        }
    }


}
