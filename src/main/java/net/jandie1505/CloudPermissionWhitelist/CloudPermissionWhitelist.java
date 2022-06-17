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
    private static CloudPermissionWhitelist plugin;
    private String taskName;
    private Map<UUID, Integer> tempAllowed;
    private List<UUID> tempAllowedPlayerArray;
    int mainTask;
    int consoleUpdateNotificationTask;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        plugin = this;

        this.tempAllowed = new HashMap<>();
        this.tempAllowedPlayerArray = new ArrayList<>();

        taskName = Wrapper.getInstance().getServiceId().getTaskName();
        Config.load();
        this.getLogger().info("Task: " + taskName + "\n" +
                "[CloudPermissionWhitelist] Join Permission: cloudpermissionwhitelist.join." + taskName);
        Bukkit.getServer().getPluginManager().registerEvents(new Events(), this);
        getCommand("localkick").setExecutor(new CmdLocalKick());
        getCommand("localkick").setTabCompleter(new CmdLocalKick());
        getCommand("allowtempjoin").setExecutor(new CmdAllowTempJoin());
        getCommand("allowtempjoin").setTabCompleter(new CmdAllowTempJoin());
        getCommand("denytempjoin").setExecutor(new CmdDenyTempJoin());
        getCommand("denytempjoin").setTabCompleter(new CmdDenyTempJoin());
        getCommand("denyalltempjoin").setExecutor(new CmdDenyAllTempJoin());
        getCommand("denyalltempjoin").setTabCompleter(new CmdDenyAllTempJoin());
        getCommand("listtempjoin").setExecutor(new CmdListTempJoin());
        getCommand("listtempjoin").setTabCompleter(new CmdListTempJoin());

        mainTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                Set<UUID> keySet = tempAllowed.keySet();
                tempAllowedPlayerArray = new ArrayList<>(keySet);
                for(UUID playerid : tempAllowedPlayerArray) {
                    if(tempAllowed.get(playerid) > 0 && tempAllowed.containsKey(playerid)) {
                        int time = tempAllowed.get(playerid);
                        time = time - 1;
                        tempAllowed.put(playerid, time);
                    } else {
                        tempAllowed.remove(playerid);
                        CloudPermissionWhitelist.getPlugin().getLogger().info("[CloudPermissionWhitelist] " + Bukkit.getPlayer(playerid).getName() + " can't join anymore");
                    }
                }
            }
        }, 0, 20);

        if(Config.getUpdateCheck()) {
            consoleUpdateNotificationTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                @Override
                public void run() {
                    if(Config.getUpdateCheck()) {
                        UpdateChecker.refreshUpdateStatus();

                        if(Config.getUpdateNotifyConsole() && UpdateChecker.isUpdateAvailable()) {
                            Bukkit.getLogger().info("An update of CloudPermissionWhitelist is available. Download it here: https://github.com/jandie1505/CloudPermissionWhitelist/releases");
                        }
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

    public List<UUID> getTempAllowedPlayerArray(){
        return this.tempAllowedPlayerArray;
    }

    public String getTaskName(){
        return this.taskName;
    }

    public static CloudPermissionWhitelist getPlugin(){
        return plugin;
    }
}
