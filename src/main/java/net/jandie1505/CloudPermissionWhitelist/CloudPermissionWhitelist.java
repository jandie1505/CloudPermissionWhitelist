package net.jandie1505.CloudPermissionWhitelist;

import com.google.common.collect.ArrayListMultimap;
import de.dytanic.cloudnet.wrapper.Wrapper;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.GreedyStringArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class CloudPermissionWhitelist extends JavaPlugin implements Listener {
    private static String taskName;
    private static HashMap<UUID, Integer> tempAllowed = new HashMap<UUID, Integer>();
    private static ArrayList<UUID> tempAllowedPlayerArray = new ArrayList<UUID>();
    int mainTask;

    @Override
    public void onLoad(){
        CommandAPI.onLoad(false);
        CommandAPICommand localKick = new CommandAPICommand("localkick")
                .withPermission("cloudpermissionwhitelist.localkick")
                .withArguments(new PlayerArgument("Player"))
                .withArguments(new GreedyStringArgument("Reason"))
                .executesPlayer((sender, args) -> {
                    Player p = (Player) sender;
                    Player target = (Player) args[0];
                    p.sendMessage("§aKicked player " + target.getName());
                    target.kickPlayer((String) args[1]);
                    System.out.println("[CloudPermissionWhitelist] Kicked player" + target.getName());
                })
                .executesConsole((sender, args) -> {
                    Player target = (Player) args[0];
                    System.out.println("§aKicked player " + target.getName());
                    target.kickPlayer((String) args[1]);
                });
        CommandAPICommand allowTempJoin = new CommandAPICommand("allowtempjoin")
                .withPermission("cloudpermissionwhitelist.allowtempjoin")
                .withArguments(new GreedyStringArgument("Player"))
                .executesPlayer((sender, args) -> {
                    Player p = (Player) sender;
                    OfflinePlayer target = Bukkit.getOfflinePlayer((String) args[0]);
                    tempAllowed.put(target.getUniqueId(), 300);
                    p.sendMessage("§a" + target.getName() + " §acan now join for 5 minutes");
                    System.out.println("[CloudPermissionWhitelist] " + target.getName() + " can now join for 5 minutes");
                })
                .executesConsole((sender, args) -> {
                    OfflinePlayer target = Bukkit.getOfflinePlayer((String) args[0]);
                    tempAllowed.put(target.getUniqueId(), 300);
                    System.out.println("§a" + target.getName() + " §acan now join for 5 minutes");
                });
        CommandAPICommand denyTempJoin = new CommandAPICommand("denytempjoin")
                .withPermission("cloudpermissionwhitelist.denytempjoin")
                .withAliases("disallowtempjoin")
                .withArguments(new GreedyStringArgument("Player"))
                .executesPlayer((sender, args) -> {
                    Player p = (Player) sender;
                    OfflinePlayer target = Bukkit.getOfflinePlayer((String) args[0]);
                    if(tempAllowed.containsKey(target.getUniqueId())){
                        tempAllowed.remove(target.getUniqueId());
                        p.sendMessage("§a" + target.getName() + " §acan't join anymore");
                        System.out.println("[CloudPermissionWhitelist] " + target.getName() + " can't join anymore");
                    } else {
                        p.sendMessage("§a" + target.getName() + " §aalready can't join");
                    }
                })
                .executesConsole((sender, args) -> {
                    OfflinePlayer target = Bukkit.getOfflinePlayer((String) args[0]);
                    if(tempAllowed.containsKey(target.getUniqueId())){
                        tempAllowed.remove(target.getUniqueId());
                        System.out.println("§a" + target.getName() + " §acan't join anymore");
                    } else {
                        System.out.println("§a" + target.getName() + " §aalready can't join");
                    }
                });

        CommandAPICommand listTempJoin = new CommandAPICommand("listtempjoin")
                .withPermission("cloudpermissionwhitelist.listtempjoin")
                .executesPlayer((sender, args) -> {
                    Player p = (Player) sender;
                    p.sendMessage("§aPlayers that can join:");
                    for(UUID playerid : tempAllowedPlayerArray){
                        p.sendMessage("§7" + Bukkit.getOfflinePlayer(playerid).getName());
                    }
                });

        localKick.register();
        allowTempJoin.register();
        denyTempJoin.register();
        listTempJoin.register();
    }

    @Override
    public void onEnable(){
        CommandAPI.onEnable(this);
        taskName = Wrapper.getInstance().getServiceId().getTaskName();
        System.out.println("[CloudPermissionWhitelist] Task: " + taskName);
        System.out.println("[CloudPermissionWhitelist] Join Permission: cloudpermissionwhitelist.join." + taskName);
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        mainTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                Set<UUID> keySet = tempAllowed.keySet();
                tempAllowedPlayerArray = new ArrayList<>(keySet);
                for(UUID playerid : tempAllowedPlayerArray){
                    if(tempAllowed.get(playerid) > 0 && tempAllowed.containsKey(playerid)){
                        int time = tempAllowed.get(playerid);
                        time = time - 1;
                        tempAllowed.put(playerid, time);
                    } else {
                        tempAllowed.remove(playerid);
                        System.out.println("[CloudPermissionWhitelist] " + Bukkit.getPlayer(playerid).getName() + " can't join anymore");
                    }
                }
            }
        }, 0, 20);
    }

    public static boolean canPlayerJoin(Player player){
        if(tempAllowed.containsKey(player.getUniqueId()) && tempAllowed.get(player.getUniqueId()) > 0){
            return true;
        } else {
            return false;
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLogin(PlayerLoginEvent event){
        Player target = event.getPlayer();
        if(target.hasPermission("cloudpermissionwhitelist.join." + taskName) || target.hasPermission("cloudpermissionwhitelist.join.*") || target.hasPermission("cloudpermissionwhitelist.*")){
            System.out.println("[CloudPermissionWhitelist] Login allowed for " + target.getName());
            event.allow();
        } else if(canPlayerJoin(target)){
            System.out.println("[CloudPermissionWhitelist] Login temporary allowed for " + target.getName());
            event.allow();
        } else {
            System.out.println("[CloudPermissionWhitelist] Login denied for " + target.getName());
            event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, "§cYou don't have the permission to join this server");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player target = event.getPlayer();
        if(!(target.hasPermission("cloudpermissionwhitelist.join." + taskName) || target.hasPermission("cloudpermissionwhitelist.join.*") || target.hasPermission("cloudpermissionwhitelist.*")) && canPlayerJoin(target)){
            target.sendMessage("§aYou could join this server because an admin gave you temporary access\nIf you disconnect, you might can't join anymore");
        }
    }
}
