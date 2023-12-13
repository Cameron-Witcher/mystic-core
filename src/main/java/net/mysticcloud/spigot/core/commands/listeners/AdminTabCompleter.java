package net.mysticcloud.spigot.core.commands.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.StringUtil;


public class AdminTabCompleter implements TabCompleter {

    Map<String, List<String>> cmds = new HashMap<>();

    public AdminTabCompleter() {


    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("about")) {
            return completions;
        }
        if (cmd.getName().equalsIgnoreCase("sudo")) {
            if (args.length == 1)
                StringUtil.copyPartialMatches(args[0], getOnlinePlayers(), completions);
        }
        if (cmd.getName().equalsIgnoreCase("sudo")) {
            if (args.length == 2) {
                List<String> allCmds = new ArrayList<>();
                for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
                    for (String s : plugin.getDescription().getCommands().keySet())
                        allCmds.add("/" + s);
                }
                allCmds.add("-walk");
                allCmds.add("-punch");
                StringUtil.copyPartialMatches(args[1], allCmds, completions);

            }
        }
        if (cmd.getName().equalsIgnoreCase("debug")) {
            if (args.length == 1) {

                StringUtil.copyPartialMatches(args[0], cmds.get("debug"), completions);
            }
            if (args.length >= 2) {
                if (args[0].equalsIgnoreCase("show") || args[0].equalsIgnoreCase("hide")) {
                    if (args.length <= 4)
                        StringUtil.copyPartialMatches(args[args.length - 1], getOnlinePlayers(), completions);
                }
            }
        }

        return completions;

    }

    public List<String> getOnlinePlayers() {
        List<String> players = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            players.add(player.getName());
        }
        return players;
    }

}
