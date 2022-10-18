package net.jandie1505.cloudpermissionwhitelist.server.commands;

import net.jandie1505.cloudpermissionwhitelist.server.CloudPermissionWhitelist;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CmdDenyAllTempJoin implements CommandExecutor, TabCompleter {
    private final CloudPermissionWhitelist cloudPermissionWhitelist;

    public CmdDenyAllTempJoin(CloudPermissionWhitelist cloudPermissionWhitelist) {
        this.cloudPermissionWhitelist = cloudPermissionWhitelist;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof ConsoleCommandSender || sender instanceof Player)) {
            return false;
        }

        if (sender instanceof Player && !sender.hasPermission("cloudpermissionwhitelist.denyalltempjoin")) {
            sender.sendMessage("§cYou don't have the permission to use this command");
            return true;
        }

        if (args.length != 0) {
            sender.sendMessage("§cUse /denyalltempjoin");
            return true;
        }

        this.cloudPermissionWhitelist.clearTempAllowed();
        sender.sendMessage("§aList of temp join players was cleared");

        return true;
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
        List<String> tabComplete = new ArrayList<>();
        return tabComplete;
    }
}
