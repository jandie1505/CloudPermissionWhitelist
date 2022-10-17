package net.jandie1505.CloudPermissionWhitelist.commands;

import net.jandie1505.CloudPermissionWhitelist.CloudPermissionWhitelist;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CmdLocalKick implements CommandExecutor, TabCompleter {
    private final CloudPermissionWhitelist cloudPermissionWhitelist;

    public CmdLocalKick(CloudPermissionWhitelist cloudPermissionWhitelist) {
        this.cloudPermissionWhitelist = cloudPermissionWhitelist;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof ConsoleCommandSender || sender instanceof Player)) {
            return false;
        }

        if (sender instanceof Player && !sender.hasPermission("cloudpermissionwhitelist.listtempjoin")) {
            sender.sendMessage("§cYou don't have the permission to use this command");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("§cUse /localkick <Player> [Reason]");
            return true;
        }

        Player target;
        String reason = "You have been kicked from the server";

        try {
            target = Bukkit.getPlayer(UUID.fromString(args[0]));
        } catch (IllegalArgumentException e) {
            target = Bukkit.getPlayer(args[0]);
        }

        if (target == null) {
            sender.sendMessage("§cPlayer not online");
            return true;
        }

        if (args.length >= 2) {
            reason = args[1];
            for (int i = 2; i < args.length; i++) {
                reason = reason + " " + args[i];
            }
        }

        this.cloudPermissionWhitelist.removeTempAllowed(target.getUniqueId());
        target.kickPlayer(reason);
        sender.sendMessage("§aKicked " + target.getName() + " for " + reason);

        return true;
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
