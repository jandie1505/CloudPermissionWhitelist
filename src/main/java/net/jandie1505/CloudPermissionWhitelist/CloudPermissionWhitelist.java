package net.jandie1505.CloudPermissionWhitelist;

import de.dytanic.cloudnet.wrapper.Wrapper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class CloudPermissionWhitelist extends JavaPlugin implements Listener {
    private static String taskName;

    @Override
    public void onEnable(){
        taskName = Wrapper.getInstance().getServiceId().getTaskName();
        System.out.println("[CloudPermissionWhitelist] Task: " + taskName);
        System.out.println("[CloudPermissionWhitelist] Join Permission: cloudpermissionwhitelist." + taskName);
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLogin(PlayerLoginEvent event){
        Player target = event.getPlayer();
        if(target.hasPermission("cloudpermissionwhitelist." + taskName) || target.hasPermission("cloudpermissionwhitelist.*")){
            System.out.println("[CloudPermissionWhitelist] Login allowed for " + target.getName());
            event.allow();
        } else {
            System.out.println("[CloudPermissionWhitelist] Login denied for " + target.getName());
            event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, "§cYou don't have the permission to join this server");
        }
    }
}
