package net.mysticcloud.spigot.core.commands;

import net.mysticcloud.spigot.core.utils.InfringementUtils;
import net.mysticcloud.spigot.core.utils.MessageUtils;
import net.mysticcloud.spigot.core.utils.regions.RegionUtils;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import net.mysticcloud.spigot.core.MysticCore;
import net.mysticcloud.spigot.core.commands.listeners.AdminCommandTabCompleter;
import net.mysticcloud.spigot.core.utils.CoreUtils;
import org.bukkit.util.Vector;
import org.json2.JSONArray;
import org.json2.JSONObject;

import java.util.Map;

public class AdminCommands implements CommandExecutor {

    public AdminCommands(MysticCore plugin, String... cmd) {
        for (String s : cmd) {
            PluginCommand com = plugin.getCommand(s);
            com.setExecutor(this);
            com.setTabCompleter(new AdminCommandTabCompleter());
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("sudo")) {
            if (sender.hasPermission("mysticcloud.admin.cmd.sudo")) {
                boolean allplayers = false;
                if (args.length > 1) {
                    if (Bukkit.getPlayer(args[0]) == null) {
                        if (!args[0].equalsIgnoreCase("@a")) {
                            sender.sendMessage(MessageUtils.prefixes("admin") + "Player not online.");
                            return true;
                        }
                        allplayers = true;

                    }
                    String command = "";
                    for (int s = 1; s != args.length; s++) {
                        command = command == "" ? args[s] : command + " " + args[s];
                    }
                    if (args[1].startsWith("/")) {
                        command = command.replaceFirst("/", "");
                        if (allplayers) for (Player player : Bukkit.getOnlinePlayers()) {
                            player.performCommand(command);
                        }
                        else Bukkit.getPlayer(args[0]).performCommand(command);
                        return true;
                    }
                    if (args[1].startsWith("-punch")) {
                        if (allplayers) {
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                for (Entity e : player.getNearbyEntities(5, 5, 5)) {
                                    if (e.equals(player)) continue;
                                    if (player.hasLineOfSight(e) && e instanceof LivingEntity) {
                                        ((LivingEntity) e).damage(0.1, player);
                                        break;
                                    }
                                }
                            }
                        } else for (Entity e : Bukkit.getPlayer(args[0]).getNearbyEntities(5, 5, 5)) {
                            if (e.equals(Bukkit.getPlayer(args[0]))) continue;
                            if (Bukkit.getPlayer(args[0]).hasLineOfSight(e) && e instanceof LivingEntity) {
                                ((LivingEntity) e).damage(0.1, Bukkit.getPlayer(args[0]));
                                break;
                            }
                        }
                        return true;

                    }
                    if (args[1].startsWith("-walk")) {
                        command = command.replaceFirst("-walk ", "");
                        String[] loc = command.split(" ");
                        if (allplayers) for (Player player : Bukkit.getOnlinePlayers()) {
                            player.teleport(Bukkit.getPlayer(args[0]).getLocation().add(Double.parseDouble(loc[0]), Double.parseDouble(loc[1]), Double.parseDouble(loc[2])));
                        }
                        else
                            Bukkit.getPlayer(args[0]).teleport(Bukkit.getPlayer(args[0]).getLocation().add(Double.parseDouble(loc[0]), Double.parseDouble(loc[1]), Double.parseDouble(loc[2])));
                        return true;
                    }
                    if (allplayers) for (Player player : Bukkit.getOnlinePlayers()) {
                        player.chat(command);
                    }
                    else Bukkit.getPlayer(args[0]).chat(command);

                } else {
                    sender.sendMessage(MessageUtils.prefixes("admin") + "/sudo <user> <command>");
                }
            }
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("region")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 0) {
                    sender.sendMessage(MessageUtils.prefixes("admin") + "Usage: /region help");
                    return true;
                }
                if (args[0].equalsIgnoreCase("help")) {
                    sender.sendMessage(MessageUtils.prefixes("region") + "save <name>");
                }
                if (args[0].equalsIgnoreCase("save")) {
                    if (args.length != 2) {
                        sender.sendMessage(MessageUtils.prefixes("region") + "Usage: /region save <name>");
                        return true;
                    }

                    RegionUtils.saveRegion(args[1], player);
                }

                if (args[0].equalsIgnoreCase("paste")) {
                    if (args.length != 2) {
                        sender.sendMessage(MessageUtils.prefixes("region") + "Usage: /region save <name>");
                        return true;
                    }

                    if (RegionUtils.saveExists(args[1])) {
                        player.sendMessage(MessageUtils.prefixes("region") + "Sorry that region doesn't exist.");
                        return true;
                    }

                    RegionUtils.pasteSave(args[1], player.getLocation());
                }

            } else {
                sender.sendMessage(MessageUtils.colorize(MessageUtils.prefixes("region") + "Sorry, you must be a player to run that command."));
            }
        }

        if (cmd.getName().equalsIgnoreCase("kick")) {
            if (sender.hasPermission("perm.kick.2")) {
                if (args.length > 0) {
                    String a = "";
                    if (args.length > 1) {
                        int b = 0;
                        for (String s : args) {
                            if (b != 0) a = a == "" ? s : a + " " + s;
                            b = b + 1;
                        }
                    }

                    InfringementUtils.kick(args[0], sender instanceof Player ? sender.getName() : "CONSOLE", a);
                } else {
                    sender.sendMessage(MessageUtils.prefixes("Usage: /kick <player> [reason]"));
                    return true;
                }
            } else {
                sender.sendMessage(MessageUtils.colorize(MessageUtils.prefixes("admin") + "You don't have permission to do that."));
            }
        }
        if (cmd.getName().equalsIgnoreCase("update")) {
            if (args.length == 1) {

                String plugin = args[0];
                String filename = plugin + ".jar";
                String url = "https://ci.vanillaflux.com/job/" + plugin + "/lastSuccessfulBuild/artifact/target/" + filename;
                sender.sendMessage(MessageUtils.prefixes("admin") + "Downloading " + filename + "...");
                if (CoreUtils.downloadFile(url, "plugins/" + filename, "quick", "CGtLLf9gckbh4xb@"))
                    sender.sendMessage(MessageUtils.prefixes("admin") + MessageUtils.colorize("Finished downloading " + filename));
                else {
                    sender.sendMessage(MessageUtils.prefixes("admin") + MessageUtils.colorize("There was an error downloading " + filename + ". Trying alt site..."));
                    if (CoreUtils.downloadFile("https://downloads.vanillaflux.com/" + filename, "plugins/" + filename, "admin", "v4pob8LW"))
                        sender.sendMessage(MessageUtils.prefixes("admin") + MessageUtils.colorize("Finished downloading " + filename));
                    else {
                        sender.sendMessage(MessageUtils.prefixes("admin") + MessageUtils.colorize("There was an error downloading " + filename + ". Trying alt site..."));
                    }
                }

            } else {
                sender.sendMessage(MessageUtils.prefixes("admin") + "Usage: /update <plugin>");
            }

        }


        return true;
    }
}
