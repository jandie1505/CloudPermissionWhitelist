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
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length == 0){
                p.sendMessage("§aPlayers that can join:");
                for(UUID playerid : CloudPermissionWhitelist.getTempAllowedPlayerArray()){
                    p.sendMessage("§7" + Bukkit.getOfflinePlayer(playerid).getName());
                }
            } else {
                p.sendMessage("§cUse /listtempjoin");
            }
        } else if(sender instanceof ConsoleCommandSender){
            if(args.length == 0){
                CloudPermissionWhitelist.getPlugin().getLogger().info("§aPlayers that can join:");
                for(UUID playerid : CloudPermissionWhitelist.getTempAllowedPlayerArray()){
                    CloudPermissionWhitelist.getPlugin().getLogger().info("§7" + Bukkit.getOfflinePlayer(playerid).getName());
                }
            } else {
                CloudPermissionWhitelist.getPlugin().getLogger().info("§cUse /listtempjoin");
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
