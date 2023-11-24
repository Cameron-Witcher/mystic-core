package net.mysticcloud.spigot.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InfringementUtils {

    public static void kick(String player, String staff, String reason) {
        reason = reason == "" ? MessageUtils.colorize("&cYou've been kicked by &4" + staff + "&c.") : reason;
        CommandSender sender = staff.equalsIgnoreCase("CONSOLE") ? Bukkit.getConsoleSender() : Bukkit.getPlayer(staff);
        if (Bukkit.getPlayer(player) == null) {
            sender.sendMessage(MessageUtils.prefixes("admin") + "Player not online. Sending kick to proxy.");
            reason = (reason.contains("%kick%") ? "" : "%kick%") + reason;
            MessageUtils.sendPluginMessage((Player) Bukkit.getOnlinePlayers().toArray()[0], "mystic:mystic", "kick",
                    player + " " + reason);
        } else {
            Bukkit.getPlayer(player).kickPlayer(reason);
        }
    }
}
