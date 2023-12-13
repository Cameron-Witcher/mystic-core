package net.mysticcloud.spigot.core.commands;

import net.mysticcloud.spigot.core.MysticCore;
import net.mysticcloud.spigot.core.commands.listeners.AdminCommandTabCompleter;
import net.mysticcloud.spigot.core.utils.MessageUtils;
import net.mysticcloud.spigot.core.utils.gui.GuiManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

public class PlayerCommands implements CommandExecutor {

    public PlayerCommands(MysticCore plugin, String... cmd) {
        for (String s : cmd) {
            PluginCommand com = plugin.getCommand(s);
            com.setExecutor(this);
            com.setTabCompleter(new AdminCommandTabCompleter());
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("inventory")) {
            if (sender instanceof Player) {
                if (args.length == 0) return false;
//                if (sender.hasPermission("" + "." + args[0])) {
                Player opener = args.length >= 2 ? Bukkit.getPlayer(args[1]) : (Player) sender;
                if (opener == null) {
                    sender.sendMessage(MessageUtils.prefixes("gui") + "Sorry, that player doesn't seem to be online.");
                    return true;
                }
                try {
                    GuiManager.openInventory(opener, GuiManager.getGuis().get(args[0]).getInventory(opener), args[0]);
                } catch (NullPointerException ex) {
                    sender.sendMessage(MessageUtils.prefixes("gui") + "There was an error opening that GUI. Does it exist?");
                    ex.printStackTrace();
                }

//                }
            }

        }
        return true;
    }
}
