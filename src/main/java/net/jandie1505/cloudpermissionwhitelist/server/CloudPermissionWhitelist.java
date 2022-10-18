package net.jandie1505.cloudpermissionwhitelist.server;

import de.dytanic.cloudnet.wrapper.Wrapper;
import net.jandie1505.cloudpermissionwhitelist.server.commands.*;
import net.jandie1505.cloudpermissionwhitelist.server.misc.Config;
import net.jandie1505.cloudpermissionwhitelist.server.events.Events;
import net.jandie1505.cloudpermissionwhitelist.server.misc.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class CloudPermissionWhitelist extends JavaPlugin {
    private static CloudPermissionWhitelist instance;
    private String taskName;
    private boolean protectionEnabled;
    private int protectionTime;
    private Map<UUID, Integer> tempAllowed;
    private Config config;
    private UpdateChecker updateChecker;
    int mainTask;
    int consoleUpdateNotificationTask;

    @Override
    public void onEnable() {
        this.config = new Config(this);
        this.updateChecker = new UpdateChecker(this);

        this.protectionEnabled = true;
        this.protectionTime = -1;
        this.tempAllowed = Collections.synchronizedMap(new HashMap<>());

        this.taskName = Wrapper.getInstance().getServiceId().getTaskName();

        this.getLogger().info("Task: " + taskName);
        this.getLogger().info("Join Permission: cloudpermissionwhitelist.join." + taskName);
        Bukkit.getServer().getPluginManager().registerEvents(new Events(this), this);
        this.getCommand("localkick").setExecutor(new CmdLocalKick(this));
        this.getCommand("localkick").setTabCompleter(new CmdLocalKick(this));
        this.getCommand("allowtempjoin").setExecutor(new CmdAllowTempJoin(this));
        this.getCommand("allowtempjoin").setTabCompleter(new CmdAllowTempJoin(this));
        this.getCommand("denytempjoin").setExecutor(new CmdDenyTempJoin(this));
        this.getCommand("denytempjoin").setTabCompleter(new CmdDenyTempJoin(this));
        this.getCommand("denyalltempjoin").setExecutor(new CmdDenyAllTempJoin(this));
        this.getCommand("denyalltempjoin").setTabCompleter(new CmdDenyAllTempJoin(this));
        this.getCommand("listtempjoin").setExecutor(new CmdListTempJoin(this));
        this.getCommand("listtempjoin").setTabCompleter(new CmdListTempJoin(this));
        this.getCommand("joinprotection").setExecutor(new CmdJoinProtection(this));
        this.getCommand("joinprotection").setTabCompleter(new CmdJoinProtection(this));

        this.mainTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            Map<UUID, Integer> copyMap = new HashMap<>(this.tempAllowed);
            for(UUID playerid : copyMap.keySet()) {
                if(this.tempAllowed.containsKey(playerid) && this.tempAllowed.get(playerid) > 0) {
                    int time = this.tempAllowed.get(playerid);
                    time = time - 1;
                    this.tempAllowed.put(playerid, time);
                } else {
                    this.tempAllowed.remove(playerid);
                    this.getLogger().info("Temporary join permission for " + playerid + " expired");
                }
            }

            if (this.protectionTime > 1) {
                this.protectionTime = this.protectionTime - 1;
            } else if (this.protectionTime == 0) {
                this.protectionTime = -1;
                this.protectionEnabled = !this.protectionEnabled;
            }
        }, 0, 20);

        if(this.config.getUpdateCheck()) {
            this.consoleUpdateNotificationTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
                if(this.config.getUpdateCheck()) {
                    this.updateChecker.refreshUpdateStatus();

                    if(this.config.getUpdateNotifyConsole() && this.updateChecker.isUpdateAvailable()) {
                        this.getLogger().info("An update of CloudPermissionWhitelist is available. Download it here: https://github.com/jandie1505/CloudPermissionWhitelist/releases");
                    }
                }
            }, 0, 144000);
        }

        if (this.config.getAutoDisableWhitelist()) {
            Bukkit.setWhitelist(false);
        }

        instance = this;
    }

    // CLOUDNET TASK

    public String getTaskName() {
        return this.taskName;
    }

    // PROTECTION STATUS

    public boolean isProtectionEnabled() {
        return this.protectionEnabled;
    }

    public int getProtectionTime() {
        return this.protectionTime;
    }

    public void setProtectionEnabled(boolean protectionEnabled) {
        this.protectionTime = -1;
        this.protectionEnabled = protectionEnabled;
    }

    public void setProtectionEnabled(boolean protectionEnabled, int time) {
        this.protectionTime = time;
        this.protectionEnabled = protectionEnabled;
    }

    // TEMP JOIN RELATED

    public boolean isPlayerTempAllowed(Player player) {
        return this.tempAllowed.containsKey(player.getUniqueId()) && tempAllowed.get(player.getUniqueId()) > 0;
    }

    public Map<UUID, Integer> getTempAllowed() {
        return Map.copyOf(this.tempAllowed);
    }

    public void addTempAllowed(UUID playerId, int time) {
        this.tempAllowed.put(playerId, time);
        this.getLogger().info(playerId + " can now join for " + time + " seconds");
    }

    public void removeTempAllowed(UUID playerId) {
        this.tempAllowed.remove(playerId);
        this.getLogger().info(playerId + " can't join anymore");
    }

    public void clearTempAllowed() {
        this.tempAllowed.clear();
        this.getLogger().info("List of temp join players was cleared");
    }

    // OBJECTS

    public Config getPluginConfig() {
        return this.config;
    }

    public UpdateChecker getUpdateChecker() {
        return this.updateChecker;
    }

    // STATIC

    public static CloudPermissionWhitelist getInstance() {
        return instance;
    }
}
