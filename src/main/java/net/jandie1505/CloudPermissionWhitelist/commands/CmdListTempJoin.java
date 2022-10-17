package net.jandie1505.CloudPermissionWhitelist.commands;

import net.jandie1505.CloudPermissionWhitelist.CloudPermissionWhitelist;
import org.bukkit.Bukkit;
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
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(args.length == 0) {
                p.sendMessage("§aPlayers that can join:");
                for(UUID playerid : this.cloudPermissionWhitelist.getTempAllowedPlayerList()){
                    p.sendMessage("§7" + Bukkit.getOfflinePlayer(playerid).getName() + " (" + this.cloudPermissionWhitelist.getTempAllowed().get(Bukkit.getOfflinePlayer(playerid).getUniqueId()) + ")");
                }
            } else {
                p.sendMessage("§cUse /listtempjoin");
            }
        } else if(sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;
            if(args.length == 0) {
                console.sendMessage("§aPlayers that can join:");
                for(UUID playerid : this.cloudPermissionWhitelist.getTempAllowedPlayerList()){
                    console.sendMessage("§7" + Bukkit.getOfflinePlayer(playerid).getName() + " (" + this.cloudPermissionWhitelist.getTempAllowed().get(Bukkit.getOfflinePlayer(playerid).getUniqueId()) + ")");
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
