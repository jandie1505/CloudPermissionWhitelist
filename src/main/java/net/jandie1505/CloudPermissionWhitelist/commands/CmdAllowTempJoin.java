package net.jandie1505.CloudPermissionWhitelist.commands;

import net.jandie1505.CloudPermissionWhitelist.CloudPermissionWhitelist;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CmdAllowTempJoin implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission("cloudpermissionwhitelist.allowtempjoin")){
                if(args.length == 1){
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                    CloudPermissionWhitelist.getTempAllowed().put(target.getUniqueId(), 300);
                    p.sendMessage("§a" + target.getName() + " can now join for 5 minutes");
                    CloudPermissionWhitelist.getPlugin().getLogger().info("[CloudPermissionWhitelist] " + target.getName() + " can now join for 5 minutes");
                } else {
                    p.sendMessage("§cUse /allowtempjoin <Player>");
                }
            } else {
                p.sendMessage("§cYou don't have the permission to use this command");
            }
        } else if(sender instanceof ConsoleCommandSender){
            if(args.length == 1){
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                CloudPermissionWhitelist.getTempAllowed().put(target.getUniqueId(), 300);
                CloudPermissionWhitelist.getPlugin().getLogger().info("§a" + target.getName() + " can now join for 5 minutes");
                CloudPermissionWhitelist.getPlugin().getLogger().info("[CloudPermissionWhitelist] " + target.getName() + " can now join for 5 minutes");
            } else {
                CloudPermissionWhitelist.getPlugin().getLogger().info("§cUse allowtempjoin <Player>");
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
