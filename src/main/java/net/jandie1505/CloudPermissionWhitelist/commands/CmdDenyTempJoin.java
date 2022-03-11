package net.jandie1505.CloudPermissionWhitelist.commands;

import net.jandie1505.CloudPermissionWhitelist.CloudPermissionWhitelist;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CmdDenyTempJoin implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(p.hasPermission("cloudpermissionwhitelist.denytempjoin")) {
                if(args.length == 1) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                    if(CloudPermissionWhitelist.getTempAllowed().containsKey(target.getUniqueId())) {
                        CloudPermissionWhitelist.getTempAllowed().remove(target.getUniqueId());
                        p.sendMessage("§a" + target.getName() + " can't join anymore");
                        CloudPermissionWhitelist.getPlugin().getLogger().info(target.getName() + " can't join anymore");
                    } else {
                        p.sendMessage("§cThis player already can't join");
                    }
                } else {
                    p.sendMessage("§cUse /denytempjoin <Player>");
                }
            } else {
                p.sendMessage("§cYou don't have the permission to use this command");
            }
        } else if(sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;
            if(args.length == 1) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                if(CloudPermissionWhitelist.getTempAllowed().containsKey(target.getUniqueId())) {
                    CloudPermissionWhitelist.getTempAllowed().remove(target.getUniqueId());
                    console.sendMessage("§a" + target.getName() + " can't join anymore");
                    CloudPermissionWhitelist.getPlugin().getLogger().info(target.getName() + " can't join anymore");
                } else {
                    console.sendMessage("§cThis player already can't join");
                }
            } else {
                console.sendMessage("§cUse denytempjoin <Player>");
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
