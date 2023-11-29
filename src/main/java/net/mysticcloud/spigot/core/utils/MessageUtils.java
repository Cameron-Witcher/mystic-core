package net.mysticcloud.spigot.core.utils;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class MessageUtils {

    private static Map<String, String> prefixes = new HashMap<>();

    public static void log(String message) {
        CoreUtils.getPlugin().getLogger().log(Level.ALL, colorize(message));
    }

    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String prefixes(String key) {
        if (prefixes.get(key) == null)
            prefixes.put(key, colorize("&e&l" + key.toUpperCase().substring(0, 1)
                    + key.toLowerCase().substring(1, key.length()) + " &7>&f "));
        return prefixes.get(key);
    }

    public static void prefixes(String key, String prefix) {
        prefixes.put(key, colorize(prefix));
    }


    public static void sendPluginMessage(Player player, String channel, String... arguments) {
        if (arguments == null | arguments.length == 0)
            return;
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        for (String s : arguments) {
            out.writeUTF(s);
        }
        player.sendPluginMessage(CoreUtils.getPlugin(), channel, out.toByteArray());
    }
}
