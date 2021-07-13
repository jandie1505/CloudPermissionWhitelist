package net.jandie1505.CloudPermissionWhitelist.commands;

import net.jandie1505.CloudPermissionWhitelist.CloudPermissionWhitelist;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CmdDenyAllTempJoin implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length == 0){
                CloudPermissionWhitelist.getTempAllowed().clear();
                p.sendMessage("§aList of temp join players was cleared");
                System.out.println("[CloudPermissionWhitelist] List of temp join players was cleared");
            } else {
                p.sendMessage("§cUse /denyalltempjoin");
            }
        } else if(sender instanceof ConsoleCommandSender){
            if(args.length == 0){
                CloudPermissionWhitelist.getTempAllowed().clear();
                System.out.println("§aList of temp join players was cleared");
                System.out.println("[CloudPermissionWhitelist] List of temp join players was cleared");
            } else {
                System.out.println("§cUse denyalltempjoin");
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
