package net.jandie1505.CloudPermissionWhitelist.events;

import net.jandie1505.CloudPermissionWhitelist.CloudPermissionWhitelist;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class Events implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLogin(PlayerLoginEvent event){
        Player target = event.getPlayer();
        if(target.hasPermission("cloudpermissionwhitelist.join." + CloudPermissionWhitelist.getTaskName()) || target.hasPermission("cloudpermissionwhitelist.join.*") || target.hasPermission("cloudpermissionwhitelist.*")){
            System.out.println("[CloudPermissionWhitelist] Login allowed for " + target.getName());
            event.allow();
        } else if(CloudPermissionWhitelist.canPlayerJoin(target)){
            System.out.println("[CloudPermissionWhitelist] Login temporary allowed for " + target.getName());
            event.allow();
        } else {
            System.out.println("[CloudPermissionWhitelist] Login denied for " + target.getName());
            event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, "§cYou don't have the permission to join this server");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player target = event.getPlayer();
        if(!(target.hasPermission("cloudpermissionwhitelist.join." + CloudPermissionWhitelist.getTaskName()) || target.hasPermission("cloudpermissionwhitelist.join.*") || target.hasPermission("cloudpermissionwhitelist.*")) && CloudPermissionWhitelist.canPlayerJoin(target)){
            target.sendMessage("§aYou could join this server because an admin gave you temporary access\nIf you disconnect, you might can't join anymore");
        }
    }
}
