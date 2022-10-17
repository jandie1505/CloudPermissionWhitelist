package net.jandie1505.CloudPermissionWhitelist.misc;

import net.jandie1505.CloudPermissionWhitelist.CloudPermissionWhitelist;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {
    private final CloudPermissionWhitelist cloudPermissionWhitelist;

    public Config(CloudPermissionWhitelist cloudPermissionWhitelist) {
        this.cloudPermissionWhitelist = cloudPermissionWhitelist;
        this.updateCheck = false;
        this.updateNotifyConsole = false;
        this.updateNotifyPlayer = false;
        this.tempJoinTime = 300;
        this.load();
    }

    private File configFile;
    private FileConfiguration config;

    private boolean updateCheck;
    private boolean updateNotifyConsole;
    private boolean updateNotifyPlayer;
    private int tempJoinTime;

    public void load() {
        try {
            createCustomConfig();

            this.updateCheck = this.config.getBoolean("updateCheck.check");
            this.updateNotifyConsole = this.config.getBoolean("updateCheck.notifyConsole");
            this.updateNotifyPlayer = this.config.getBoolean("updateCheck.notifyPlayer");

            int tempJoinTimeCheck = config.getInt("tempJoinTime");
            if(tempJoinTimeCheck > 0 && tempJoinTimeCheck < 1800) {
                this.tempJoinTime = tempJoinTimeCheck;
            }
        } catch(Exception e) {
            this.cloudPermissionWhitelist.getLogger().warning("Error with config, using internal default values");
        }
    }

    public boolean getUpdateCheck() {
        return this.updateCheck;
    }

    public boolean getUpdateNotifyConsole() {
        return this.updateNotifyConsole;
    }

    public boolean getUpdateNotifyPlayer() {
        return this.updateNotifyPlayer;
    }

    public int getTempJoinTime() {
        return this.tempJoinTime;
    }

    // CONFIG FILE
    public FileConfiguration getCustomConfig() {
        return this.config;
    }

    private void createCustomConfig() {
        this.configFile = new File(this.cloudPermissionWhitelist.getDataFolder(), "config.yml");
        if (!this.configFile.exists()) {
            this.configFile.getParentFile().mkdirs();
            this.cloudPermissionWhitelist.saveResource("config.yml", false);
        }

        this.config = new YamlConfiguration();
        try {
            this.config.load(this.configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            this.cloudPermissionWhitelist.getLogger().warning("Configuration Error, using default values");
        }
    }
}
