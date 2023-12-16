package net.mysticcloud.spigot.core.commands.listeners;

import java.util.ArrayList;
import java.util.List;

import net.mysticcloud.spigot.core.utils.gui.GuiManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;


public class NpcTabCompleter implements TabCompleter {

    private List<String> invs = new ArrayList<>();
    private List<String> guiCmds = new ArrayList<>();

    public NpcTabCompleter() {
        for (String key : GuiManager.getGuis().keySet()) {
            invs.add(key);
        }
        guiCmds.add("reload");
        guiCmds.add("update");
        guiCmds.add("setkey");
        guiCmds.add("list");
        guiCmds.add("dump");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("guis")) {
            if (args.length == 1) {
                StringUtil.copyPartialMatches(args[0], guiCmds, completions);
            }
        }
        if (cmd.getName().equalsIgnoreCase("inventory")) {
            if (args.length == 1) {
                StringUtil.copyPartialMatches(args[0], invs, completions);
            }
            if (args.length == 2) {
                StringUtil.copyPartialMatches(args[1], getOnlinePlayers(), completions);
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