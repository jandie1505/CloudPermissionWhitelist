package net.jandie1505.cloudpermissionwhitelist.server.commands;

import net.jandie1505.cloudpermissionwhitelist.server.CloudPermissionWhitelist;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CmdListTempJoin implements CommandExecutor, TabCompleter {
    private final CloudPermissionWhitelist cloudPermissionWhitelist;

    public CmdListTempJoin(CloudPermissionWhitelist cloudPermissionWhitelist) {
        this.cloudPermissionWhitelist = cloudPermissionWhitelist;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof ConsoleCommandSender || sender instanceof Player)) {
            return false;
        }

        if (sender instanceof Player && !sender.hasPermission("cloudpermissionwhitelist.listtempjoin")) {
            sender.sendMessage("§cYou don't have the permission to use this command");
            return true;
        }

        if (args.length != 0) {
            sender.sendMessage("§cUse /listtempjoin");
            return true;
        }

        String message = "§aPlayers that can join:§7\n";
        for (UUID playerId : this.cloudPermissionWhitelist.getTempAllowed().keySet()) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerId);
            message = message + "§7" + offlinePlayer.getName() + " (" + playerId + "), " + this.cloudPermissionWhitelist.getTempAllowed().get(playerId) + "\n";
        }
        message = message + "Allowed players: " + this.cloudPermissionWhitelist.getTempAllowed().size() + "\n";

        sender.sendMessage(message);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
        List<String> tabComplete = new ArrayList<>();
        return tabComplete;
    }
}
