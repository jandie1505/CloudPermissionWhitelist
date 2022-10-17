package net.jandie1505.CloudPermissionWhitelist.commands;

import net.jandie1505.CloudPermissionWhitelist.CloudPermissionWhitelist;
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
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(args.length == 0) {
                this.cloudPermissionWhitelist.getTempAllowed().clear();
                p.sendMessage("§aList of temp join players was cleared");
                this.cloudPermissionWhitelist.getLogger().info("List of temp join players was cleared");
            } else {
                p.sendMessage("§cUse /denyalltempjoin");
            }
        } else if(sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;
            if(args.length == 0) {
                this.cloudPermissionWhitelist.getTempAllowed().clear();
                console.sendMessage("§aList of temp join players was cleared");
                this.cloudPermissionWhitelist.getLogger().info("List of temp join players was cleared");
            } else {
                console.sendMessage("§cUse denyalltempjoin");
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
