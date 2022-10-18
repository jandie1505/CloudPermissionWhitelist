package net.jandie1505.cloudpermissionwhitelist.server.commands;

import net.jandie1505.cloudpermissionwhitelist.server.CloudPermissionWhitelist;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CmdAllowTempJoin implements CommandExecutor, TabCompleter {
    private final CloudPermissionWhitelist cloudPermissionWhitelist;

    public CmdAllowTempJoin(CloudPermissionWhitelist cloudPermissionWhitelist) {
        this.cloudPermissionWhitelist = cloudPermissionWhitelist;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof ConsoleCommandSender || sender instanceof Player)) {
            return false;
        }

        if (sender instanceof Player && !sender.hasPermission("cloudpermissionwhitelist.allowtempjoin")) {
            sender.sendMessage("§cYou don't have the permission to use this command");
            return true;
        }

        if (args.length < 1 || args.length > 2) {
            sender.sendMessage("§cUse /allowtempjoin <Player> [Time]");
            return true;
        }

        UUID targetUUID;
        String targetName;
        int tempJoinTime = this.cloudPermissionWhitelist.getPluginConfig().getTempJoinTime();

        try {
            targetUUID = UUID.fromString(args[0]);
            targetName = targetUUID.toString();
        } catch (IllegalArgumentException e) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            targetUUID = target.getUniqueId();
            targetName = target.getName();
        }

        if (args.length == 2) {
            try {
                tempJoinTime = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                sender.sendMessage("§cPlease enter a valid time in seconds");
                return true;
            }
        }

        this.cloudPermissionWhitelist.addTempAllowed(targetUUID, tempJoinTime);
        sender.sendMessage("§a" + targetName + " can now join for " + tempJoinTime + " seconds");

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
        List<String> tabComplete = new ArrayList<>();
        return tabComplete;
    }
}
