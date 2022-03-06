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
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission("cloudpermissionwhitelist.denytempjoin")){
                if(args.length == 1){
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                    if(CloudPermissionWhitelist.getTempAllowed().containsKey(target.getUniqueId())){
                        CloudPermissionWhitelist.getTempAllowed().remove(target.getUniqueId());
                        p.sendMessage("§a" + target.getName() + " can't join anymore");
                        System.out.println("[CloudPermissionWhitelist] " + target.getName() + " can't join anymore");
                    } else {
                        p.sendMessage("§cThis player already can't join");
                    }
                } else {
                    p.sendMessage("§cUse /denytempjoin <Player>");
                }
            } else {
                p.sendMessage("§cYou don't have the permission to use this command");
            }
        } else if(sender instanceof ConsoleCommandSender){
            if(args.length == 1){
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                if(CloudPermissionWhitelist.getTempAllowed().containsKey(target.getUniqueId())){
                    CloudPermissionWhitelist.getTempAllowed().remove(target.getUniqueId());
                    System.out.println("§a" + target.getName() + " can't join anymore");
                    System.out.println("[CloudPermissionWhitelist] " + target.getName() + " can't join anymore");
                } else {
                    System.out.println("§cThis player already can't join");
                }
            } else {
                System.out.println("§cUse denytempjoin <Player>");
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
