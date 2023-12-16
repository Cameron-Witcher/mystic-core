package net.mysticcloud.spigot.core.commands;

import net.mysticcloud.spigot.core.MysticCore;
import net.mysticcloud.spigot.core.commands.listeners.NpcTabCompleter;
import net.mysticcloud.spigot.core.utils.MessageUtils;
import net.mysticcloud.spigot.core.utils.gui.GuiManager;
import net.mysticcloud.spigot.core.utils.npc.Npc;
import net.mysticcloud.spigot.core.utils.npc.NpcManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

public class NpcCommands implements CommandExecutor {

    public NpcCommands(MysticCore plugin, String... cmd) {
        for (String s : cmd) {
            PluginCommand com = plugin.getCommand(s);
            com.setExecutor(this);
            com.setTabCompleter(new NpcTabCompleter());
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("npc")) {
            if (sender instanceof Player) {
                if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
                    //send help.
                    return true;
                }
                if (args[0].equalsIgnoreCase("spawn")) {
                    Npc npc = NpcManager.createPersistentNpc(((Player) sender).getLocation());
                }
            } else {
                sender.sendMessage(MessageUtils.prefixes("admin") + "Sorry, that is a player only command.");
            }

        }
        return true;
    }
}
