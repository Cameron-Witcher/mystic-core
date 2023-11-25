package net.mysticcloud.spigot.core.commands;

import net.mysticcloud.spigot.core.utils.InfringementUtils;
import net.mysticcloud.spigot.core.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import net.mysticcloud.spigot.core.MysticCore;
import net.mysticcloud.spigot.core.commands.listeners.AdminCommandTabCompleter;
import net.mysticcloud.spigot.core.utils.CoreUtils;

public class AdminCommands implements CommandExecutor {

    public AdminCommands(MysticCore plugin, String... cmd) {
        for (String s : cmd) {
            PluginCommand com = plugin.getCommand(s);
            com.setExecutor(this);
            com.setTabCompleter(new AdminCommandTabCompleter());
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("kick")) {
            if (sender.hasPermission("perm.kick")) {
                if (args.length > 0) {
                    String a = "";
                    if (args.length > 1) {
                        int b = 0;
                        for (String s : args) {
                            if (b != 0)
                                a = a == "" ? s : a + " " + s;
                            b = b + 1;
                        }
                    }

                    InfringementUtils.kick(args[0], sender instanceof Player ? sender.getName() : "CONSOLE", a);
                } else {
                    sender.sendMessage(MessageUtils.prefixes("Usage: /kick <player> [reason]"));
                    return true;
                }
            } else {
                sender.sendMessage(
                        MessageUtils.colorize(MessageUtils.prefixes("admin") + "You don't have permission to do that."));
            }
        }
        if (cmd.getName().equalsIgnoreCase("update")) {
            if (args.length == 1) {

                String plugin = args[0];
                String filename = plugin + ".jar";
                String url = "https://ci.vanillaflux.com/job/" + plugin
                        + "/lastSuccessfulBuild/artifact/target/" + filename;
                sender.sendMessage(MessageUtils.prefixes("admin") + "Downloading " + filename + "...");
                if (CoreUtils.downloadFile(url, "plugins/" + filename, "quick", "CGtLLf9gckbh4xb@"))
                    sender.sendMessage(
                            MessageUtils.prefixes("admin") + MessageUtils.colorize("Finished downloading " + filename));
                else {
                    sender.sendMessage(MessageUtils.prefixes("admin") + MessageUtils
                            .colorize("There was an error downloading " + filename + ". Trying alt site..."));
                    if (CoreUtils.downloadFile("https://downloads.vanillaflux.com/" + filename, "plugins/" + filename,
                            "admin", "v4pob8LW"))
                        sender.sendMessage(
                                MessageUtils.prefixes("admin") + MessageUtils.colorize("Finished downloading " + filename));
                    else {
                        sender.sendMessage(MessageUtils.prefixes("admin") + MessageUtils
                                .colorize("There was an error downloading " + filename + ". Trying alt site..."));
                    }
                }

            } else {
                sender.sendMessage(MessageUtils.prefixes("admin") + "Usage: /update <plugin>");
            }

        }


        return true;
    }
}
