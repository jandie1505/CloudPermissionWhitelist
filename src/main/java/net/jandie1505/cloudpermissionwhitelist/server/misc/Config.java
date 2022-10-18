package net.jandie1505.cloudpermissionwhitelist.server.misc;

import net.jandie1505.cloudpermissionwhitelist.server.CloudPermissionWhitelist;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {
    private final CloudPermissionWhitelist cloudPermissionWhitelist;

    private File configFile;
    private FileConfiguration config;

    private boolean updateCheck;
    private boolean updateNotifyConsole;
    private boolean updateNotifyPlayer;
    private boolean statsTracking;
    private int tempJoinTime;
    private boolean autoDisableWhitelist;
    private boolean enforceWhitelist;

    public Config(CloudPermissionWhitelist cloudPermissionWhitelist) {
        this.cloudPermissionWhitelist = cloudPermissionWhitelist;

        this.updateCheck = false;
        this.updateNotifyConsole = false;
        this.updateNotifyPlayer = false;
        this.statsTracking = false;
        this.tempJoinTime = 300;
        this.autoDisableWhitelist = true;
        this.enforceWhitelist = false;

        this.load();
    }

    public void load() {
        try {
            this.createCustomConfig(false);

            this.updateCheck = this.config.getBoolean("updateCheck.check");
            this.updateNotifyConsole = this.config.getBoolean("updateCheck.notifyConsole");
            this.updateNotifyPlayer = this.config.getBoolean("updateCheck.notifyPlayer");
            this.statsTracking = this.config.getBoolean("statsTracking");
            this.autoDisableWhitelist = this.config.getBoolean("autoDisableWhitelist");
            this.enforceWhitelist = this.config.getBoolean("enforceWhitelist");

            int tempJoinTimeCheck = config.getInt("tempJoinTime");
            if(tempJoinTimeCheck > 0 && tempJoinTimeCheck < 1800) {
                this.tempJoinTime = tempJoinTimeCheck;
            }
        } catch(Exception e) {
            this.cloudPermissionWhitelist.getLogger().warning("Error with config, using internal default values");
            this.createCustomConfig(true);
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

    public boolean getAutoDisableWhitelist() {
        return this.autoDisableWhitelist;
    }

    public boolean getStatsTracking() {
        return this.statsTracking;
    }

    public boolean getEnforceWhitelist() {
        return this.enforceWhitelist;
    }

    // CONFIG FILE
    public FileConfiguration getCustomConfig() {
        return this.config;
    }

    private void createCustomConfig(boolean override) {
        this.configFile = new File(this.cloudPermissionWhitelist.getDataFolder(), "config.yml");
        if (!this.configFile.exists() || override) {
            this.configFile.getParentFile().mkdirs();
            this.cloudPermissionWhitelist.saveResource("config.yml", override);
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
