package net.jandie1505.cloudpermissionwhitelist.server.commands;

import net.jandie1505.cloudpermissionwhitelist.server.CloudPermissionWhitelist;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CmdJoinProtection implements CommandExecutor, TabCompleter {
    private final CloudPermissionWhitelist cloudPermissionWhitelist;

    public CmdJoinProtection(CloudPermissionWhitelist cloudPermissionWhitelist) {
        this.cloudPermissionWhitelist = cloudPermissionWhitelist;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof ConsoleCommandSender || sender instanceof Player)) {
            return false;
        }

        if (sender instanceof Player && !sender.hasPermission("cloudpermissionwhitelist.joinprotection")) {
            sender.sendMessage("§cYou don't have the permission to use this command");
            return true;
        }

        if (args.length < 1 || args.length > 2) {
            sender.sendMessage("§cUse /joinprotection <status/on/off> [time]");
            return true;
        }

        if (args[0].equalsIgnoreCase("status")) {
            this.getStatusCommand(sender, cmd, s, args);
            return true;
        } else if (args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("true") || args[0].equalsIgnoreCase("enable")) {
            this.setStatusCommand(sender, cmd, s, args, true);
            return true;
        } else if (args[0].equalsIgnoreCase("off") || args[0].equalsIgnoreCase("false") || args[0].equalsIgnoreCase("disable")) {
            this.setStatusCommand(sender, cmd, s, args, false);
            return true;
        } else {
            sender.sendMessage("§cPlease enter status/on/off");
            return true;
        }
    }

    private void getStatusCommand(CommandSender sender, Command cmd, String s, String[] args) {
        String statusString;
        String time = "";

        if (this.cloudPermissionWhitelist.isProtectionEnabled()) {
            statusString = "§aenabled";
        } else {
            statusString = "§cdisabled";
        }

        if (this.cloudPermissionWhitelist.getProtectionTime() >= 0) {
            time = " (status change in " + this.cloudPermissionWhitelist.getProtectionTime() + " seconds)";
        }

        sender.sendMessage("§7Current join protection status: " + statusString + "§7" + time);
    }

    private void setStatusCommand(CommandSender sender, Command cmd, String s, String[] args, boolean status) {
        String timeString = "";

        if (args.length == 2) {
            try {
                if (Integer.parseInt(args[1]) < 0) {
                    sender.sendMessage("§cThe time cannot be negative");
                    return;
                }

                this.cloudPermissionWhitelist.setProtectionEnabled(status, Integer.parseInt(args[1]));
                timeString = " for " + args[1] + " seconds";
            } catch (NumberFormatException e) {
                sender.sendMessage("§cPlease enter a valid time in seconds");
                return;
            }
        } else {
            this.cloudPermissionWhitelist.setProtectionEnabled(status);
        }

        if (status) {
            sender.sendMessage("§aJoin protection was enabled" + timeString);
        } else {
            sender.sendMessage("§aJoin protection was disabled" + timeString);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
        List<String>  tabComplete = new ArrayList<>();

        if (args.length == 1) {
            tabComplete.add("status");
            tabComplete.add("on");
            tabComplete.add("off");
        }

        return tabComplete;
    }
}
