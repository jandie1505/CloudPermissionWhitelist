package net.jandie1505.CloudPermissionWhitelist.commands;

import net.jandie1505.CloudPermissionWhitelist.CloudPermissionWhitelist;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CmdLocalKick implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player){
            this.playerCommandSender((Player) sender, args);
        } else if(sender instanceof ConsoleCommandSender){
            this.consoleCommandSender((ConsoleCommandSender) sender, args);
        }
        return true;
    }

    private void playerCommandSender(Player p, String[] args) {
        if(p.hasPermission("cloudpermissionwhitelist.localkick")) {
            if(args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if(target != null) {
                    target.kickPlayer("You have been kicked from the server");
                    p.sendMessage("§aKicked " + target.getName());
                    if(CloudPermissionWhitelist.getPlugin().getTempAllowed().containsKey(target.getUniqueId())) {
                        CloudPermissionWhitelist.getPlugin().getTempAllowed().remove(target.getUniqueId());
                    }
                }
            } else if(args.length == 2) {
                Player target = Bukkit.getPlayer(args[0]);
                if(target != null) {
                    target.kickPlayer(args[1]);
                    p.sendMessage("§aKicked " + target.getName() + "§a for " + args[1]);
                    if(CloudPermissionWhitelist.getPlugin().getTempAllowed().containsKey(target.getUniqueId())) {
                        CloudPermissionWhitelist.getPlugin().getTempAllowed().remove(target.getUniqueId());
                    }
                }
            } else {
                p.sendMessage("§cUse /localkick <Player> <Reason>");
            }
        } else {
            p.sendMessage("§cYou don't have the permission to use this command");
        }
    }

    private void consoleCommandSender(ConsoleCommandSender console, String[] args) {
        if(args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if(target != null) {
                target.kickPlayer("You have been kicked from the server");
                console.sendMessage("§aKicked " + target.getName());
                if(CloudPermissionWhitelist.getPlugin().getTempAllowed().containsKey(target.getUniqueId())) {
                    CloudPermissionWhitelist.getPlugin().getTempAllowed().remove(target.getUniqueId());
                }
            }
        } if(args.length == 2) {
            Player target = Bukkit.getPlayer(args[0]);
            if(target != null) {
                target.kickPlayer(args[1]);
                console.sendMessage("§aKicked " + target.getName() + "§a for " + args[1]);
                if(CloudPermissionWhitelist.getPlugin().getTempAllowed().containsKey(target.getUniqueId())) {
                    CloudPermissionWhitelist.getPlugin().getTempAllowed().remove(target.getUniqueId());

                }
            }
        } else {
            CloudPermissionWhitelist.getPlugin().getLogger().info("§cUse localkick <Player> <Reason>");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
        List<String>  tabComplete = new ArrayList<>();
        if(args.length == 1){
            for(Player p : Bukkit.getOnlinePlayers()){
                tabComplete.add(p.getName());
            }
        }
        return tabComplete;
    }
}
