package net.jandie1505.cloudpermissionwhitelist.server.commands;

import net.jandie1505.cloudpermissionwhitelist.server.CloudPermissionWhitelist;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CmdDenyTempJoin implements CommandExecutor, TabCompleter {
    private final CloudPermissionWhitelist cloudPermissionWhitelist;

    public CmdDenyTempJoin(CloudPermissionWhitelist cloudPermissionWhitelist) {
        this.cloudPermissionWhitelist = cloudPermissionWhitelist;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof ConsoleCommandSender || sender instanceof Player)) {
            return false;
        }

        if (sender instanceof Player && !sender.hasPermission("cloudpermissionwhitelist.denytempjoin")) {
            sender.sendMessage("§cYou don't have the permission to use this command");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage("§cUse /denytempjoin <Player>");
            return true;
        }

        UUID targetUUID;
        String targetName;

        try {
            targetUUID = UUID.fromString(args[0]);
            targetName = targetUUID.toString();
        } catch (IllegalArgumentException e) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            targetUUID = target.getUniqueId();
            targetName = target.getName();
        }

        this.cloudPermissionWhitelist.removeTempAllowed(targetUUID);
        sender.sendMessage("§a" + targetName + " can't join anymore");

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
        List<String> tabComplete = new ArrayList<>();
        return tabComplete;
    }
}
