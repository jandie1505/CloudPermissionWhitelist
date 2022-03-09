package net.jandie1505.CloudPermissionWhitelist;

import de.dytanic.cloudnet.wrapper.Wrapper;
import net.jandie1505.CloudPermissionWhitelist.commands.*;
import net.jandie1505.CloudPermissionWhitelist.events.Events;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class CloudPermissionWhitelist extends JavaPlugin {
    private static CloudPermissionWhitelist plugin;
    private static String taskName;
    private static HashMap<UUID, Integer> tempAllowed = new HashMap<UUID, Integer>();
    private static ArrayList<UUID> tempAllowedPlayerArray = new ArrayList<UUID>();
    int mainTask;

    @Override
    public void onEnable(){
        plugin = this;
        taskName = Wrapper.getInstance().getServiceId().getTaskName();
        this.getLogger().info("[CloudPermissionWhitelist] Task: " + taskName + "" +
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
                for(UUID playerid : tempAllowedPlayerArray){
                    if(tempAllowed.get(playerid) > 0 && tempAllowed.containsKey(playerid)){
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
    }

    public static boolean canPlayerJoin(Player player){
        if(tempAllowed.containsKey(player.getUniqueId()) && tempAllowed.get(player.getUniqueId()) > 0){
            return true;
        } else {
            return false;
        }
    }

    public static CloudPermissionWhitelist getPlugin(){
        return plugin;
    }

    public static HashMap<UUID, Integer> getTempAllowed(){
        return tempAllowed;
    }

    public static ArrayList<UUID> getTempAllowedPlayerArray(){
        return tempAllowedPlayerArray;
    }

    public static String getTaskName(){
        return taskName;
    }
}
