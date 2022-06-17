package net.jandie1505.CloudPermissionWhitelist.commands;

import net.jandie1505.CloudPermissionWhitelist.CloudPermissionWhitelist;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CmdListTempJoin implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(args.length == 0) {
                p.sendMessage("§aPlayers that can join:");
                for(UUID playerid : CloudPermissionWhitelist.getPlugin().getTempAllowedPlayerArray()){
                    p.sendMessage("§7" + Bukkit.getOfflinePlayer(playerid).getName() + " (" + CloudPermissionWhitelist.getPlugin().getTempAllowed().get(Bukkit.getOfflinePlayer(playerid).getUniqueId()) + ")");
                }
            } else {
                p.sendMessage("§cUse /listtempjoin");
            }
        } else if(sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;
            if(args.length == 0) {
                console.sendMessage("§aPlayers that can join:");
                for(UUID playerid : CloudPermissionWhitelist.getPlugin().getTempAllowedPlayerArray()){
                    console.sendMessage("§7" + Bukkit.getOfflinePlayer(playerid).getName() + " (" + CloudPermissionWhitelist.getPlugin().getTempAllowed().get(Bukkit.getOfflinePlayer(playerid).getUniqueId()) + ")");
                }
            } else {
                console.sendMessage("§cUse /listtempjoin");
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
