package net.jandie1505.CloudPermissionWhitelist;

import de.dytanic.cloudnet.wrapper.Wrapper;
import net.jandie1505.CloudPermissionWhitelist.commands.*;
import net.jandie1505.CloudPermissionWhitelist.misc.Config;
import net.jandie1505.CloudPermissionWhitelist.events.Events;
import net.jandie1505.CloudPermissionWhitelist.misc.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class CloudPermissionWhitelist extends JavaPlugin {
    private static CloudPermissionWhitelist instance;
    private String taskName;
    private Map<UUID, Integer> tempAllowed;
    private List<UUID> tempAllowedPlayerList;
    private Config config;
    int mainTask;
    int consoleUpdateNotificationTask;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        instance = this;

        this.tempAllowed = new HashMap<>();
        this.tempAllowedPlayerList = new ArrayList<>();

        taskName = Wrapper.getInstance().getServiceId().getTaskName();

        this.config = new Config(this);

        this.getLogger().info("Task: " + taskName + "\n" +
                "[CloudPermissionWhitelist] Join Permission: cloudpermissionwhitelist.join." + taskName);
        Bukkit.getServer().getPluginManager().registerEvents(new Events(this), this);
        getCommand("localkick").setExecutor(new CmdLocalKick(this));
        getCommand("localkick").setTabCompleter(new CmdLocalKick(this));
        getCommand("allowtempjoin").setExecutor(new CmdAllowTempJoin(this));
        getCommand("allowtempjoin").setTabCompleter(new CmdAllowTempJoin(this));
        getCommand("denytempjoin").setExecutor(new CmdDenyTempJoin(this));
        getCommand("denytempjoin").setTabCompleter(new CmdDenyTempJoin(this));
        getCommand("denyalltempjoin").setExecutor(new CmdDenyAllTempJoin(this));
        getCommand("denyalltempjoin").setTabCompleter(new CmdDenyAllTempJoin(this));
        getCommand("listtempjoin").setExecutor(new CmdListTempJoin(this));
        getCommand("listtempjoin").setTabCompleter(new CmdListTempJoin(this));

        mainTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            Set<UUID> keySet = tempAllowed.keySet();
            tempAllowedPlayerList = new ArrayList<>(keySet);
            for(UUID playerid : tempAllowedPlayerList) {
                if(tempAllowed.get(playerid) > 0 && tempAllowed.containsKey(playerid)) {
                    int time = tempAllowed.get(playerid);
                    time = time - 1;
                    tempAllowed.put(playerid, time);
                } else {
                    tempAllowed.remove(playerid);
                    this.getLogger().info("[CloudPermissionWhitelist] " + Bukkit.getPlayer(playerid).getName() + " can't join anymore");
                }
            }
        }, 0, 20);

        if(this.config.getUpdateCheck()) {
            consoleUpdateNotificationTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
                if(this.config.getUpdateCheck()) {
                    UpdateChecker.refreshUpdateStatus();

                    if(this.config.getUpdateNotifyConsole() && UpdateChecker.isUpdateAvailable()) {
                        Bukkit.getLogger().info("An update of CloudPermissionWhitelist is available. Download it here: https://github.com/jandie1505/CloudPermissionWhitelist/releases");
                    }
                }
            }, 0, 144000);
        }
    }

    public boolean canPlayerJoin(Player player){
        return this.tempAllowed.containsKey(player.getUniqueId()) && tempAllowed.get(player.getUniqueId()) > 0;
    }

    public Map<UUID, Integer> getTempAllowed(){
        return this.tempAllowed;
    }

    public List<UUID> getTempAllowedPlayerList(){
        return this.tempAllowedPlayerList;
    }

    public String getTaskName(){
        return this.taskName;
    }

    public Config getPluginConfig() {
        return this.config;
    }

    public static CloudPermissionWhitelist getInstance() {
        return instance;
    }
}
