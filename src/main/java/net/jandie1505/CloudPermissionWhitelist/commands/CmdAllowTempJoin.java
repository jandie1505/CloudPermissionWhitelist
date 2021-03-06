package net.jandie1505.CloudPermissionWhitelist.commands;

import net.jandie1505.CloudPermissionWhitelist.CloudPermissionWhitelist;
import net.jandie1505.CloudPermissionWhitelist.misc.Config;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CmdAllowTempJoin implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(p.hasPermission("cloudpermissionwhitelist.allowtempjoin")) {
                if(args.length == 1) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                    CloudPermissionWhitelist.getPlugin().getTempAllowed().put(target.getUniqueId(), Config.getTempJoinTime());
                    p.sendMessage("§a" + target.getName() + " can now join for " + Config.getTempJoinTime() + " seconds");
                    CloudPermissionWhitelist.getPlugin().getLogger().info(target.getName() + " can now join for " + Config.getTempJoinTime() + " seconds");
                } else {
                    p.sendMessage("§cUse /allowtempjoin <Player>");
                }
            } else {
                p.sendMessage("§cYou don't have the permission to use this command");
            }
        } else if(sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;
            if(args.length == 1) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                CloudPermissionWhitelist.getPlugin().getTempAllowed().put(target.getUniqueId(), Config.getTempJoinTime());
                console.sendMessage("§a" + target.getName() + " can now join for " + Config.getTempJoinTime() + " seconds");
                CloudPermissionWhitelist.getPlugin().getLogger().info(target.getName() + " can now join for " + Config.getTempJoinTime() + " seconds");
            } else {
                console.sendMessage("§cUse allowtempjoin <Player>");
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
        List<String> tabComplete = new ArrayList<>();
        return tabComplete;
    }
}
