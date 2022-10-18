package net.jandie1505.cloudpermissionwhitelist.server.events;

import net.jandie1505.cloudpermissionwhitelist.server.CloudPermissionWhitelist;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class Events implements Listener {
    private final CloudPermissionWhitelist cloudPermissionWhitelist;

    public Events(CloudPermissionWhitelist cloudPermissionWhitelist) {
        this.cloudPermissionWhitelist = cloudPermissionWhitelist;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLogin(PlayerLoginEvent event) {
        if (this.cloudPermissionWhitelist.isProtectionEnabled()) {
            Player target = event.getPlayer();
            if(target.hasPermission("cloudpermissionwhitelist.join." + this.cloudPermissionWhitelist.getTaskName()) || target.hasPermission("cloudpermissionwhitelist.join.*") || target.hasPermission("cloudpermissionwhitelist.*")) {
                this.cloudPermissionWhitelist.getLogger().info("Login allowed for " + target.getName());
                event.allow();
            } else if(this.cloudPermissionWhitelist.isPlayerTempAllowed(target)) {
                this.cloudPermissionWhitelist.getLogger().info("Login temporary allowed for " + target.getName());
                event.allow();
            } else {
                this.cloudPermissionWhitelist.getLogger().info("Login denied for " + target.getName());
                event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, "§cYou don't have the permission to join this server");
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player target = event.getPlayer();
        if(!(target.hasPermission("cloudpermissionwhitelist.join." + this.cloudPermissionWhitelist.getTaskName()) || target.hasPermission("cloudpermissionwhitelist.join.*") || target.hasPermission("cloudpermissionwhitelist.*")) && this.cloudPermissionWhitelist.isPlayerTempAllowed(target)) {
            target.sendMessage("§aYou could join this server because an admin gave you temporary access\nIf you disconnect, you might can't join anymore");
        }
        if(this.cloudPermissionWhitelist.getPluginConfig().getUpdateCheck() && this.cloudPermissionWhitelist.getPluginConfig().getUpdateNotifyPlayer() && this.cloudPermissionWhitelist.getUpdateChecker().isUpdateAvailable() && target.hasPermission("cloudpermissionwhitelist.updatenotify")) {
            target.sendRawMessage("§bAn update of CloudPermissionWhitelist is available. Download it here: https://github.com/jandie1505/CloudPermissionWhitelist/releases");
        }
    }
}
